package com.http.Model;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.http.Controller.AuthController;
import com.http.Controller.HomeController;
import com.http.Controller.MediaController;

public class Task implements Runnable 
{
    Socket connection;
    public Task(Socket connection)
    {
        this.connection=connection;
    }
    @Override
    public void run()
    {
        System.out.printf("Thread %s is handling the connection\n",Thread.currentThread().getName());
        try
        {
            BufferedReader in=new BufferedReader(new InputStreamReader(new BufferedInputStream(this.connection.getInputStream())));
            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(this.connection.getOutputStream())));

            // Creating HTTP Request Wrapper
            HttpRequestWrapper req = new HttpRequestWrapper();
            // reading Request Line(first line)
            String line = in.readLine();
            req.setRequestLine(line);
            // Parsing ":" separated key-value header parameters till a CRLF is found
            while ((line = in.readLine()) != null && !line.isEmpty())
            {
                int idx = line.indexOf(":");
                if (idx > 0) {
                    String key = line.substring(0, idx).trim().toLowerCase();
                    String[] values = line.substring(idx + 1).trim().split(";");
                    req.setRequestHeader(key, values);
                }
            }

            // Checking content-length, to read body if present
            int contentLength = 0;
            String[] cl = req.getRequestHeader("content-length");
            if (cl != null && cl.length !=0) {
                try { contentLength = Integer.parseInt(cl[0]); } catch (NumberFormatException ex) { contentLength = 0; }
            }
            if (contentLength > 0) {
                // The client now sends the body as a Base64-encoded string for binary
                // payloads. Always read the body as characters and store it as a String.
                char[] buf = new char[contentLength];
                int read = 0;
                while (read < contentLength) {
                    int r = in.read(buf, read, contentLength - read);
                    if (r == -1) break;
                    read += r;
                }
                req.setBody(new String(buf, 0, Math.max(0, read)));
            }

            HttpResponseWrapper res=new HttpResponseWrapper();

            // Mapping URIs to the respective controllers, for customized response handling

            if("/helloworld".equals(req.getRequestLine(1)))
                HomeController.helloWorldController(req, res, in, out);
            else if("/serverstatus".equals(req.getRequestLine(1)))
                HomeController.ServerStatus(req, res, in, out);
            else if("/signin".equals(req.getRequestLine(1)))
                AuthController.loginController(req, res, in, out);
            else if("/videos".equals(req.getRequestLine(1)))
            {
                String method=req.getRequestLine(0);
                if("POST".equals(method))
                    MediaController.setVideo(req,res,this.connection.getInputStream(),this.connection.getOutputStream());
                else if("GET".equals(method))
                    MediaController.getVideo(req,res,this.connection.getInputStream(),this.connection.getOutputStream());
                else
                {
                    res.setResponseLine("HTTP/1.1 405 Method Not Allowed");
                    res.setResponseHeader("Allow", new String[]{"GET","POST"});
                    res.setResponseHeader("Content-Length", new String[]{String.valueOf(0)});
                    out.write(res.toString());
                    out.flush();                    
                }
            }
            else if("/images".equals(req.getRequestLine(1)))
            {
                String method = req.getRequestLine(0);
                if ("POST".equalsIgnoreCase(method))
                    MediaController.setImage(req, res,this.connection.getInputStream(),this.connection.getOutputStream());
                else if("GET".equals(method))
                    MediaController.getImage(req, res,this.connection.getInputStream(),this.connection.getOutputStream());

                else // Sending 405 status code for request methods not advertised for this resource
                {
                    res.setResponseLine("HTTP/1.1 405 Method Not Allowed");
                    res.setResponseHeader("Allow", new String[]{"GET","POST"});
                    res.setResponseHeader("Content-Length", new String[]{String.valueOf(0)});
                    out.write(res.toString());
                    out.flush();
                }
            }
            else if("/audio".equals(req.getRequestLine(1)))
            {
                String method = req.getRequestLine(0);
                if ("POST".equalsIgnoreCase(method))
                    MediaController.setAudio(req, res,this.connection.getInputStream(),this.connection.getOutputStream());
                else if("GET".equals(method))
                    MediaController.getAudio(req, res,this.connection.getInputStream(),this.connection.getOutputStream());
                else
                {
                    res.setResponseLine("HTTP/1.1 405 Method Not Allowed");
                    res.setResponseHeader("Allow", new String[]{"GET","POST"});
                    res.setResponseHeader("Content-Length", new String[]{String.valueOf(0)});
                    out.write(res.toString());
                    out.flush();                    
                }
            }
            else
            {
                // invalid URI found, send response with 400 Bad Resuest
                String bad = "Bad Request!!!";
                byte[] badBytes = bad.getBytes(StandardCharsets.UTF_8);
                out.write("HTTP/1.1 400 Bad Request\r\n");
                out.write("Content-Type: text/plain; charset=utf-8\r\n");
                out.write("Content-Length: " + badBytes.length + "\r\n");
                out.write("Connection: close\r\n");
                out.write("\r\n"); // End of headers
                out.write(bad);
                out.flush();
            }
        }
        catch(Exception e)
        {
            System.out.println("An Exception was raised");
        }
    }
}