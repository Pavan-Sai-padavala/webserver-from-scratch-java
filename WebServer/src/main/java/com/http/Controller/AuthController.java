package com.http.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import com.http.Model.HttpRequestWrapper;
import com.http.Model.HttpResponseWrapper;

public class AuthController
{
    public static void loginController(HttpRequestWrapper req, HttpResponseWrapper res, BufferedReader in, BufferedWriter out) throws IOException
    {
        // Sending 405 status code for request methods not advertised for this resource
        if(! req.getRequestLine(0).equals("GET"))
        {
            res.setResponseLine("HTTP/1.1 405 Method Not Allowed");
            res.setResponseHeader("Allow", new String[]{"GET"});
            res.setResponseHeader("Content-Length", new String[]{String.valueOf(0)});
            out.write(res.toString());
            out.flush();
        }
        // Handling Authentication Header Field, if not present WWW-Authenticate Header field is placed in response
        String[] auth = req.getRequestHeader("authorization");
        if (auth == null ) {
            res.setResponseLine("HTTP/1.1 401 Unauthorized");
            res.setResponseHeader("WWW-Authenticate", new String[]{"Basic realm=\"simple\""});
            out.write(res.toString());
            out.flush();
        }
        res.setResponseLine("HTTP/1.1 200 OK");
        res.setResponseHeader("Content-Type", new String[]{"text/plain"});
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(0)});
        out.write(res.toString());
        out.flush();

    }    
}
