package com.dombi.servlet;

public class Routes {
    @WebRoute(path = "/test1")
    public String test1(){
        return "test1";
    }
    @WebRoute(path = "/test2")
    public String test2(){
        return "test2";
    }
    @WebRoute(path = "/user/<username>")
    public String usernameHandler(String username){
        return "The given username" + username;
    }

}
