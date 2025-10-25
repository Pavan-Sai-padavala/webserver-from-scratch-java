package com.http.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.http.Model.HttpRequestWrapper;
import com.http.Model.HttpResponseWrapper;

public class HomeController
{
    // GET /helloworld End-Point
    public static void helloWorldController(HttpRequestWrapper req,HttpResponseWrapper res, BufferedReader in,BufferedWriter out) throws IOException
    {
        // Status line (no CRLF here — wrapper will format)
        res.setResponseLine("HTTP/1.1 200 OK");

        // Appending HTTP header fields
        res.setResponseHeader("Content-Type", new String[]{"text/plain; charset=utf-8"});
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
        res.setResponseLine("HTTP/1.1 200 OK");

        res.setResponseHeader("Content-Type", new String[]{"text/plain; charset=utf-8"});
        String body = "Server is running on port 2025, you can make connection requests right now !!!";
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8); // Converting Character stream into Byte Stream
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(bodyBytes.length)});
        res.setResponseHeader("Connection", new String[]{"close"});

        res.setBody(body);

        out.write(res.toString());
        out.flush();
    }

}
