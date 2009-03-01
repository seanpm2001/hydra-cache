/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hydracache.server.httpd;

import static org.junit.Assert.assertNotNull;

import org.apache.http.nio.NHttpServiceHandler;
import org.apache.http.nio.protocol.EventListener;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpRequestHandler;
import org.jmock.Mockery;
import org.junit.Test;

/**
 * @author nzhu
 * 
 */
public class HttpServiceHandlerFactoryTest {
    private Mockery context = new Mockery();

    /**
     * Test method for
     * {@link org.hydracache.server.httpd.HttpServiceHandlerFactory#create()}.
     * 
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {

        HttpParams httpParams = context.mock(HttpParams.class);

        HttpRequestHandler requestHandler = context
                .mock(HttpRequestHandler.class);

        EventListener eventListener = context.mock(EventListener.class);

        HttpServiceHandlerFactory factory = new HttpServiceHandlerFactory(
                httpParams, requestHandler, eventListener);

        NHttpServiceHandler handler = factory.create();

        assertNotNull(handler);
    }

}
