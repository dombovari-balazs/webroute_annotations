package com.dombi.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

    // dinamikus rooting, user alapján
    // 1m hívásnál hány object jön létre

    public static void main(String[] args) throws Exception {
        System.out.println("Server started...");
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        Class<Routes> routesClass = Routes.class;


        for(Method method: routesClass.getDeclaredMethods()){
            if(method.isAnnotationPresent(WebRoute.class)){
                WebRoute annotation = method.getAnnotation(WebRoute.class);
                PathDiscoverer.getInstance().addRoute(annotation.path(),method);
            }
        }
        server.createContext("/", new MyHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Method methodBy = PathDiscoverer.getInstance().getMethodBy(t.getRequestURI().getPath());
            try {
                String response;
                if(methodBy == null){
                    response = "Content not found.";
                    t.sendResponseHeaders(404, response.length());
                }
                else {
                    response = methodBy.invoke(new Routes()).toString();
                    t.sendResponseHeaders(200, response.length());

                }
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


}