package com.dombi.servlet;

import java.lang.reflect.Method;
import java.util.HashMap;

public class PathDiscoverer {
    private static PathDiscoverer ourInstance = new PathDiscoverer();

    public static PathDiscoverer getInstance() {
        return ourInstance;
    }

    private PathDiscoverer() {
    }
    private HashMap<String, Method> hashMap = new HashMap();
    public void addRoute(String path, Method method){
        hashMap.put(path, method);
    }

    public Method getMethodBy(String path) {
        return hashMap.get(path);
    }
}
