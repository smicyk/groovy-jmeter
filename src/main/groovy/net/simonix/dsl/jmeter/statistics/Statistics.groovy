/*
 * Copyright 2023 Szymon Micyk
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

import groovy.transform.CompileDynamic
import org.apache.jmeter.samplers.SampleResult
import org.apache.jmeter.util.Calculator

import java.util.concurrent.ConcurrentHashMap

/**
 * Gathers statistics during test execution. Works similar to {@link org.apache.jmeter.visualizers.SummaryReport} class but with storing data to file.
 */
@CompileDynamic
class Statistics implements StatisticsProvider {

    private static final String LABEL_TOTAL_ROW = '__total__'

    private final Map<String, Calculator> rows

    long startTime
    long endTime

    static StatisticsProvider empty() {
        return new EmptyStatisticsProvider()
    }

    Statistics() {
        this.rows = new ConcurrentHashMap<>()
        this.rows.put(LABEL_TOTAL_ROW, new Calculator(LABEL_TOTAL_ROW))
    }

    void addSample(SampleResult sample) {
        String sampleLabel = sample.getSampleLabel(false)

        Calculator row = rows.get(sampleLabel)
        if (row == null) {
            row = rows.computeIfAbsent(sampleLabel, { label ->
                return new Calculator(label)
            })
        }
        // Calculator is thread-safe
        row.addSample(sample)
        Calculator total = rows.get(LABEL_TOTAL_ROW)
        total.addSample(sample)
    }

    Object getProperty(String name) {
        MetaProperty metaProperty = metaClass.getMetaProperty(name)

        if (metaProperty) {
            return metaClass.getProperty(this, name)
        }

        Calculator calculator = rows[name]

        if (calculator) {
            return new CalculatorStatisticsProvider(calculator)
        }

        return empty()
    }

    /**
     * @return all samples' names
     */
    List<String> getNames() {
        return rows.keySet().findAll {
            it != LABEL_TOTAL_ROW
        }.collect { it }
    }

    List<StatisticsProvider> getValues() {
        return rows.keySet().findAll {
            it != LABEL_TOTAL_ROW
        }.collect { String name -> new CalculatorStatisticsProvider(rows.get(name) as Calculator) }
    }

    /**
     * @return all sample mean time
     */
    double getMean() {
        return getProperty(LABEL_TOTAL_ROW).mean
    }

    /**
     * @return all sample standard deviation time
     */
    double getStandardDeviation() {
        return getProperty(LABEL_TOTAL_ROW).standardDeviation
    }

    /**
     * @return all sample lowest time
     */
    double getMin() {
        return getProperty(LABEL_TOTAL_ROW).min
    }

    /**
     * @return all sample highest time
     */
    double getMax() {
        return getProperty(LABEL_TOTAL_ROW).max
    }

    /**
     * @return all sample count
     */
    long getCount() {
        return getProperty(LABEL_TOTAL_ROW).count
    }

    /**
     * @return all sample error percentage
     */
    double getError() {
        return getProperty(LABEL_TOTAL_ROW).error
    }

    /**
     * @return all sample throughput as number of request per second
     */
    double getThroughput() {
        return getProperty(LABEL_TOTAL_ROW).throughput
    }

    /**
     * @return all sample received bytes per second
     */
    double getReceivedBytes() {
        return getProperty(LABEL_TOTAL_ROW).receivedBytes
    }

    /**
     * @return all sample sent bytes per second
     */
    double getSentBytes() {
        return getProperty(LABEL_TOTAL_ROW).sentBytes
    }

    /**
     * @return all sample received bytes divided by number of samples
     */
    double getAveragePageBytes() {
        return getProperty(LABEL_TOTAL_ROW).averagePageBytes
    }

    /**
     * @return all sample total bytes received
     */
    long getTotalBytes() {
        return getProperty(LABEL_TOTAL_ROW).totalBytes
    }

    long getElapsedTime() {
        return endTime - startTime
    }
}
