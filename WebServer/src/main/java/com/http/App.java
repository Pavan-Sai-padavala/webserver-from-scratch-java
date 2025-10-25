package com.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.http.Controller.HomeController;
import com.http.Model.HttpRequestWrapper;
import com.http.Model.HttpResponseWrapper;

public class App 
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket socket=new ServerSocket(2025,50, InetAddress.getByName("127.0.0.1"));
        System.out.println("Server is listening on port: 2025");
        while(true)
        {
            Socket connection=socket.accept();
            BufferedReader in=new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));
            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(connection.getOutputStream())));

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

            // Checking content-length, to ready body if present
            int contentLength = 0;
            String[] cl = req.getRequestHeader("content-length");
            if (cl != null && cl.length !=0) {
                try { contentLength = Integer.parseInt(cl[0]); } catch (NumberFormatException ex) { contentLength = 0; }
            }
            if (contentLength > 0) {
                char[] buf = new char[contentLength];
                int read = 0;
                while (read < contentLength) {
                    int r = in.read(buf, read, contentLength - read);
                    if (r == -1) break;
                    read += r;
                }
                req.setBody(new String(buf, 0, read));
            }

            HttpResponseWrapper res=new HttpResponseWrapper();

            // Mapping URIs to the respective controllers, for customized response handling

            if("/helloworld".equals(req.getRequestLine(1)))
                HomeController.helloWorldController(req, res, in, out);
            else if("/serverstatus".equals(req.getRequestLine(1)))
                HomeController.ServerStatus(req, res, in, out);
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
    }
}
