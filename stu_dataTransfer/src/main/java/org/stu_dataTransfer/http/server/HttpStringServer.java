/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.stu_dataTransfer.http.server;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.RequestLine;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;

/**
 * Embedded HTTP/1.1 file server based on a classic (blocking) I/O model.
 */
public class HttpStringServer {

    public static void main(String[] args) throws Exception {

        int port = 8080;
        

        
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(15000)
                .setTcpNoDelay(true)
                .build();

        final HttpServer server = ServerBootstrap.bootstrap()
                .setListenerPort(port)
                .setServerInfo("Test/1.1")
                .setSocketConfig(socketConfig)
//                .setSslContext(sslcontext)
                .setExceptionLogger(new StdErrorExceptionLogger())
                .registerHandler("*", new HttpFileHandler())
                .create();

        server.start();
        server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.shutdown(5, TimeUnit.SECONDS);
            }
        });
    }

    static class StdErrorExceptionLogger implements ExceptionLogger {

        
        public void log(final Exception ex) {
            if (ex instanceof SocketTimeoutException) {
                System.err.println("Connection timed out");
            } else if (ex instanceof ConnectionClosedException) {
                System.err.println(ex.getMessage());
            } else {
                ex.printStackTrace();
            }
        }

    }

    static class HttpFileHandler implements HttpRequestHandler  {


        public HttpFileHandler() {
            super();
        }

        public void handle(
                final HttpRequest request,
                final HttpResponse response,
                final HttpContext context) throws HttpException, IOException {

            String method = request.getRequestLine().getMethod().toUpperCase(Locale.ROOT);
            if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
                throw new MethodNotSupportedException(method + " method not supported");
            }
            String target = request.getRequestLine().getUri();

            if (request instanceof HttpEntityEnclosingRequest) {
                HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
                byte[] entityContent = EntityUtils.toByteArray(entity);
                System.out.println("Incoming entity content (bytes): " + entityContent.length);
            }
            
            StringBuffer sb = new StringBuffer();
            if(target.contains("category")){//类别
            	sb.append("[{\"id\":\"idValue1\",\"name\":\"类别1\"},{\"id\":\"idValue2\",\"name\":\"类别2\"}]");
            }else if(target.contains("articleInCategory")){//类别中文章
            	sb.append("{\"catigory\":{\"id\":\"idvalue\",\"name\":\"类别名称\"},\"article\":[{\"id\":\"idValue\",\"title\":\"文章标题\",\"category\":\"文章类别\",\"publishDate\":\"publishDateValue\",\"content\":\"文章内容<a href>连接</a>\"},{\"id\":\"idValue\",\"title\":\"文章标题\",\"category\":\"文章类别\",\"publishDate\":\"publishDateValue\",\"content\":\"文章内容<a href>连接</a>\"}]}");
            }else if(target.contains("article")){//文章详细
            	sb.append("{\"id\":\"idValue\",\"title\":\"文章标题\",\"category\":\"文章类别\",\"publishDate\":\"publishDateValue\",\"content\":\"文章内容<a href>连接</a>\"}");
            }
            
            
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            StringEntity entity = new StringEntity(
            		sb.toString(),
                    ContentType.create("text/html", "UTF-8"));
            response.setEntity(entity);
        }

    }

}
