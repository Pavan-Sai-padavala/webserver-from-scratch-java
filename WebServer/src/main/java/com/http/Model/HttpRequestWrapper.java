package com.http.Model;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestWrapper
{
    String[] requestLine;
    HashMap<String,String[]> requestHeaders;
    String body;

    public HttpRequestWrapper()
    {
        this.requestLine=new String[3];
        this.requestHeaders=new HashMap<>();
    }

    public void setRequestLine(String requestLine)
    {
        String[] tokens = requestLine.trim().split(" ", 3);
        for (int i = 0; i < tokens.length && i < 3; i++) {
            this.requestLine[i] = tokens[i];
        }
    }
    public String getRequestLine(int ind)
    {
        return this.requestLine[ind];
    }

    public void setRequestHeader(String key,String[] values)
    {
        this.requestHeaders.put(key,values);
    }
    public String[] getRequestHeader(String key)
    {
        return this.requestHeaders.get(key);
    }
    public void setBody(String body)
    {
        this.body=body; 
    }
    public String getBody()
    {
        return this.body;
    }
    @Override
    public String toString()
    {        
        StringBuilder sb = new StringBuilder();
        String method = this.requestLine[0];
        String URI = this.requestLine[1];
        String version = this.requestLine[2];
        // Request-Line
        sb.append(method).append(" ").append(URI).append(" ").append(version).append("\r\n");

        // Headers
        for (Map.Entry<String, String[]> e : this.requestHeaders.entrySet()) {
            String key = e.getKey();
            String[] vals = e.getValue();
            if (vals == null || vals.length == 0) {
                sb.append(key).append(":").append("\r\n");
            } else {
                sb.append(key).append(": ").append(String.join(", ", vals)).append("\r\n");
            }
        }

        // CRLF to separate headers and body
        sb.append("\r\n");

        // Body (if any)
        if (this.body != null) {
            sb.append(this.body);
        }

        return sb.toString();
    }
}