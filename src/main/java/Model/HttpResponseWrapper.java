package Model;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseWrapper
{

    String[] responseLine;
    HashMap<String,String[]> responseHeaders;
    String body;

    public HttpResponseWrapper()
    {
        this.responseLine=new String[3];
        this.responseHeaders=new HashMap<>();
    }

    public void setResponseLine(String responseLine)
    {
        String[] tokens = responseLine.trim().split(" ", 3);
        for (int i = 0; i < tokens.length && i < 3; i++) {
            this.responseLine[i] = tokens[i];
        }
    }
    public String getResponseLine(int ind)
    {
        return this.responseLine[ind];
    }

    public void setResponseHeader(String key,String[] values)
    {
        this.responseHeaders.put(key,values);
    }
    public String[] getResponseHeader(String key)
    {
        return this.responseHeaders.get(key);
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
        String version = this.responseLine[0];
        String status = this.responseLine[1];
        String code = this.responseLine[2];
        // Request-Line
        sb.append(version).append(" ").append(status).append(" ").append(code).append("\r\n");

        // Headers
        for (Map.Entry<String, String[]> e : this.responseHeaders.entrySet()) {
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

