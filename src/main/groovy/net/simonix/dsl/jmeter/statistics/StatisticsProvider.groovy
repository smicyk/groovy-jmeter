/*
 * Copyright 2021 Szymon Micyk
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
package net.simonix.dsl.jmeter.statistics

import org.apache.jmeter.util.Calculator

/**
 * Provides basic statistics about test execution
 */
interface StatisticsProvider {

    /**
     * @return sample mean time
     */
    default double getMean() {
        return 0
    }

    /**
     * @return sample standard deviation time
     */
    default double getStandardDeviation() {
        return 0
    }

    /**
     * @return sample lowest time
     */
    default double getMin() {
        return 0
    }

    /**
     * @return sample highest time
     */
    default double getMax() {
        return 0
    }

    /**
     * @return sample count
     */
    default long getCount() {
        return 0
    }

    /**
     * @return sample error percentage
     */
    default double getError() {
        return 0
    }

    /**
     * @return sample throughput as number of request per second
     */
    default double getThroughput() {
        return 0
    }

    /**
     * @return sample received bytes per second
     */
    default double getReceivedBytes() {
        return 0
    }

    /**
     * @return sample sent bytes per second
     */
    default double getSentBytes() {
        return 0
    }

    /**
     * @return sample received bytes divided by number of samples
     */
    default double getAveragePageBytes() {
        return 0
    }

    /**
     * @return sample total bytes received
     */
    default long getTotalBytes() {
        return 0
    }

    default long getElapsedTime() {
        return 0
    }

    static class EmptyStatisticsProvider implements StatisticsProvider {

    }

    static class CalculatorStatisticsProvider implements StatisticsProvider {
        private final Calculator calculator

        CalculatorStatisticsProvider(Calculator calculator) {
            this.calculator = calculator
        }

        @Override
        double getMean() {
            return calculator.mean
        }

        @Override
        double getStandardDeviation() {
            return calculator.standardDeviation
        }

        @Override
        double getMin() {
            return calculator.min
        }

        @Override
        double getMax() {
            return calculator.max
        }

        @Override
        long getCount() {
            return calculator.count
        }

        @Override
        double getError() {
            return calculator.errorPercentage
        }

        @Override
        double getThroughput() {
            return calculator.rate
        }

        @Override
        double getReceivedBytes() {
            return calculator.bytesPerSecond
        }

        @Override
        double getSentBytes() {
            return calculator.sentBytesPerSecond
        }

        @Override
        double getAveragePageBytes() {
            return calculator.avgPageBytes
        }

        @Override
        long getTotalBytes() {
            return calculator.totalBytes
        }
    }
}