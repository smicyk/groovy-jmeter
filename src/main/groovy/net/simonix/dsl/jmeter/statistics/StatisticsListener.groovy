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

import org.apache.jmeter.samplers.SampleResult
import org.apache.jmeter.testelement.TestStateListener
import org.apache.jmeter.visualizers.Visualizer

/**
 * Simple implementation of {@link Visualizer} used by {@link org.apache.jmeter.reporters.ResultCollector}
 */
class StatisticsListener implements Visualizer, TestStateListener {
    final Statistics statistics = new Statistics()

    @Override
    void add(SampleResult sample) {
        statistics.addSample(sample)
    }

    @Override
    boolean isStats() {
        return true
    }

    @Override
    void testStarted() {
        statistics.startTime = System.currentTimeMillis()
    }

    @Override
    void testStarted(String host) {
        statistics.startTime = System.currentTimeMillis()
    }

    @Override
    void testEnded() {
        statistics.endTime = System.currentTimeMillis()
    }

    @Override
    void testEnded(String host) {
        statistics.endTime = System.currentTimeMillis()
    }
}
