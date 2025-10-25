package com.http.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import com.http.Model.HttpRequestWrapper;
import com.http.Model.HttpResponseWrapper;

public class HomeController
{
    // GET /helloworld End-Point
    public static void helloWorldController(HttpRequestWrapper req,HttpResponseWrapper res, BufferedReader in,BufferedWriter out) throws IOException
    {
        // Sending 405 status code for request methods not advertised for this resource
        if(! Set.of("GET", "HEAD").contains(req.getRequestLine(0)))
        {
            res.setResponseLine("HTTP/1.1 405 Method Not Allowed");
            res.setResponseHeader("Allow", new String[]{"GET"});
            res.setResponseHeader("Content-Length", new String[]{String.valueOf(0)});
            out.write(res.toString());
            out.flush();
        }
        res.setResponseLine("HTTP/1.1 200 OK");
        res.setResponseHeader("Content-Type", new String[]{"text/plain; charset=utf-8"});
        
        // HEAD /helloworld end-point, should send only headers with same response line as of GET
        if(req.getRequestLine(0).equals("HEAD"))
        {
            res.setResponseHeader("Content-Length",new String[]{"0"});
            out.write(res.toString());
            out.flush();
        }
        String body = "Hello World";
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8); // Converting Character stream into Byte Stream
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(bodyBytes.length)});
        res.setResponseHeader("Connection", new String[]{"close"});

        res.setBody(body);

        out.write(res.toString());
        out.flush();
    }
    
    // GET /ServerStatus End-Point
    public static void ServerStatus(HttpRequestWrapper req,HttpResponseWrapper res, BufferedReader in,BufferedWriter out) throws IOException
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
        res.setResponseLine("HTTP/1.1 200 OK");
        String body="";
        // Accept Field parsing for content-negotiation
        String[] accepts = req.getRequestHeader("accept");
        if(accepts != null && accepts.length > 0 && accepts[0] != null)
        {
            if (accepts[0].equals("text/plain"))
            {
                res.setResponseHeader("Content-Type", new String[]{"text/plain; charset=utf-8"});
                body = "Server is running on port 2025, you can make connection requests right now !!!";
            } 
            else
            {
                res.setResponseHeader("Content-Type",new String[]{"text/html; charset=utf-8"});
                body="<html><body><h1>Server is Running on port 2025</h1><p>You can make connection requests right now !!!</p></body></html>";
            }
        }
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8); // Converting Character stream into Byte Stream
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(bodyBytes.length)});
        res.setResponseHeader("Connection", new String[]{"close"});

        res.setBody(body);

        out.write(res.toString());
        out.flush();
    }

    
}
