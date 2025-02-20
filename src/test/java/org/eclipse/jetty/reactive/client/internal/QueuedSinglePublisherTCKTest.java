/*
 * Copyright (c) 2017-2022 the original author or authors.
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
package org.eclipse.jetty.reactive.client.internal;

import java.lang.reflect.Method;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.annotations.BeforeMethod;

public class QueuedSinglePublisherTCKTest extends PublisherVerification<String> {
    public QueuedSinglePublisherTCKTest() {
        super(new TestEnvironment());
    }

    @BeforeMethod
    public void printTestName(Method method) {
        System.err.printf("Running %s.%s()%n", getClass().getName(), method.getName());
    }

    @Override
    public Publisher<String> createPublisher(long elements) {
        QueuedSinglePublisher<String> publisher = new QueuedSinglePublisher<>();
        for (int i = 0; i < elements; ++i) {
            publisher.offer("element_" + i);
        }
        publisher.complete();
        return publisher;
    }

    @Override
    public Publisher<String> createFailedPublisher() {
        QueuedSinglePublisher<String> publisher = new QueuedSinglePublisher<>();
        publisher.cancel();
        return publisher;
    }

    @Override
    public long maxElementsFromPublisher() {
        return 1024;
    }
}
