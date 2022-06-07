/*
 * Copyright 2022 Szymon Micyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.simonix.dsl.jmeter

import org.apache.jmeter.JMeter
import org.apache.jmeter.engine.ClientJMeterEngine
import org.apache.jmeter.engine.DistributedRunner
import org.apache.jmeter.engine.JMeterEngine
import org.apache.jmeter.engine.RemoteJMeterEngineImpl
import org.apache.jmeter.rmi.RmiUtils
import org.apache.jmeter.samplers.Remoteable
import org.apache.jmeter.testelement.TestStateListener
import org.apache.jmeter.threads.RemoteThreadsListenerTestElement
import org.apache.jmeter.util.JMeterUtils
import org.apache.jmeter.util.ShutdownClient
import org.apache.jorphan.collections.HashTree
import org.apache.jorphan.util.HeapDumper
import org.apache.jorphan.util.ThreadDumper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * This code is modified version of code from {@link JMeter} and related classes.
 */
class TestScriptServerRunner {

    static final Logger logger = LoggerFactory.getLogger(TestScriptBase.class)

    static void run(String workerHostname, String workerPort) {
        System.setProperty('server.rmi.ssl.disable', 'true') // TODO: enable SSL support

        // Starting script as worker server (the test script will be provided by controller)
        try {
            System.setProperty('java.rmi.server.hostname', workerHostname)
            System.setProperty('server_port', workerPort)

            RemoteJMeterEngineImpl.startServer(RmiUtils.getRmiRegistryPort())

            logger.info('Worker node server started [hostname={}, port={}]', workerHostname, workerPort)
        } catch (Exception ex) {
            logger.error('Server can\'t be started', ex)
        }
    }

    static void run(HashTree testPlan, List<String> remoteWorkers) {
        System.setProperty('server.rmi.ssl.disable', 'true')  // TODO: enable SSL supports

        // add a system property so samplers can check to see if JMeter is running in NonGui mode
        System.setProperty(JMeter.JMETER_NON_GUI, 'true')

        try {
            List<JMeterEngine> engines = new ArrayList<>()

            // added to properly count number of threads
            testPlan.add(testPlan.getArray()[0], new RemoteThreadsListenerTestElement());

            // added to stop remote workers
            RemoteExecutionListener testListener = new RemoteExecutionListener(true)
            testPlan.add(testPlan.getArray()[0], testListener)

            DistributedRunner distributedRunner = new DistributedRunner()

            distributedRunner.init(remoteWorkers, testPlan)
            engines.addAll(distributedRunner.getEngines())
            testListener.setStartedRemoteEngines(engines)
            distributedRunner.start()
            startUdpDaemon(engines)
        } catch (Exception e) {
            logger.error('Server can\'t be started', e)
        }
    }

    private static void startUdpDaemon(final List<JMeterEngine> engines) {
        int port = JMeterUtils.getPropDefault('jmeterengine.nongui.port', ShutdownClient.UDP_PORT_DEFAULT)
        int maxPort = JMeterUtils.getPropDefault('jmeterengine.nongui.maxport', 4455)

        if (port > 1000) {
            final DatagramSocket socket = getSocket(port, maxPort)
            if (socket != null) {
                Thread.startDaemon 'UDP Listener', {
                    waitForSignals(engines, socket)
                }
            } else {
                logger.error('Failed to create UDP port [port={}]', port)
            }
        }
    }

    private static DatagramSocket getSocket(int udpPort, int udpPortMax) {
        DatagramSocket socket = null
        int i = udpPort
        while (i <= udpPortMax) {
            try {
                socket = new DatagramSocket(i)
                break
            } catch (SocketException e) {
                i++
            }
        }

        return socket
    }

    private static void waitForSignals(final List<JMeterEngine> engines, DatagramSocket socket) {
        logger.info('Waiting for possible Shutdown/StopTestNow/HeapDump/ThreadDump message [port={}]', socket.getLocalPort())

        byte[] buffer = new byte[80]

        DatagramPacket request = new DatagramPacket(buffer, buffer.length)
        try {
            while (true) {
                socket.receive(request)

                InetAddress address = request.getAddress()
                // Only accept commands from the local host
                if (address.isLoopbackAddress()){
                    String command = new String(request.getData(), request.getOffset(), request.getLength(), StandardCharsets.US_ASCII)
                    logger.info('Command received [command={}, address={}]', command, address)

                    switch (command) {
                        case 'StopTestNow':
                            for (JMeterEngine engine : engines) {
                                engine.stopTest(true)
                            }
                            break
                        case 'Shutdown':
                            for (JMeterEngine engine : engines) {
                                engine.stopTest(false)
                            }
                            break
                        case 'HeapDump':
                            HeapDumper.dumpHeap()
                            break
                        case 'ThreadDump':
                            ThreadDumper.threadDump()
                            break
                        default:
                            logger.warn('Command not recognised [command={}]', command)
                    }
                }
            }
        } catch (Exception e) {
            logger.error('Wait for signal exception', e)
        } finally {
            socket.close()
        }
    }

    private static class RemoteExecutionListener implements TestStateListener, Remoteable {
        private AtomicInteger startedRemoteEngines = new AtomicInteger(0)

        private ConcurrentLinkedQueue<JMeterEngine> remoteEngines = new ConcurrentLinkedQueue<>()

        private boolean remoteStop

        RemoteExecutionListener(boolean remoteStop) {
            this.remoteStop = remoteStop
        }

        void setStartedRemoteEngines(List<JMeterEngine> engines) {
            this.remoteEngines.clear()
            this.remoteEngines.addAll(engines)
            this.startedRemoteEngines = new AtomicInteger(remoteEngines.size())
        }

        @Override
        void testEnded(String host) {
            logger.info('Remote host finished [host={}, time={}]', host, System.currentTimeMillis())

            if (startedRemoteEngines.decrementAndGet() <= 0) {
                logger.info("All remote engines have ended test, starting RemoteTestStopper thread")

                Thread.start 'RemoteTestStopper', {
                    endTest(true)
                }
            }
        }

        @Override
        void testEnded() {
            // FIXME: is this needed if we execute test remotely
            endTest(false)
        }

        @Override
        void testStarted(String host) {
            logger.info('Remote host started [host={}, time={}]', host, System.currentTimeMillis())
        }

        @Override
        void testStarted() {
            if (logger.isInfoEnabled()) {
                logger.info('{} ({})', JMeterUtils.getResString("running_test"), System.currentTimeMillis())
            }
        }

        private void endTest(boolean isDistributed) {
            if (isDistributed) {
                logger.info('Clean up remote [time={}]', LocalDateTime.now())
            } else {
                logger.info('Clean up local [time={}]', LocalDateTime.now())
            }

            if (isDistributed) {
                if (remoteStop) {
                    logger.info('Shutting down remote servers [workers={}]', remoteEngines)
                    for (JMeterEngine engine : remoteEngines){
                        logger.info('Shutting down remote server [worker={}]', engine)
                        engine.exit()
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep(5) // Allow listeners to close files
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt()
                }

                ClientJMeterEngine.tidyRMI(logger)
            }

            checkForRemainingThreads()

            logger.info('Finished')
        }

        private static void checkForRemainingThreads() {
            final int pauseToCheckForRemainingThreads =
                    JMeterUtils.getPropDefault('jmeter.exit.check.pause', 2000)

            if (pauseToCheckForRemainingThreads > 0) {
                Thread.startDaemon {
                    try {
                        TimeUnit.MILLISECONDS.sleep(pauseToCheckForRemainingThreads) // Allow enough time for JVM to exit
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt()
                    }
                    // This is a daemon thread, which should only reach here if there are other
                    // non-daemon threads still active
                    logger.info('The JVM should have exited but did not.')
                }
            } else if (pauseToCheckForRemainingThreads <= 0) {
                logger.debug('jmeter.exit.check.pause is <= 0, JMeter won\'t check for unterminated non-daemon threads')
            }
        }
    }
}
