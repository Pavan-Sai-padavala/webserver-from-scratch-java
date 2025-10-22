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

public class WebServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket socket=new ServerSocket(2025,50, InetAddress.getByName("127.0.0.1"));
        System.out.println("Server is listening on port: 2025");
        while(true)
        {
            Socket connection=socket.accept();
            BufferedReader req=new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));
            BufferedWriter res=new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(connection.getOutputStream())));

            String line;
            while ((line = req.readLine()) != null && !line.isEmpty()) {
                            System.out.println(line);
                        }
            String body = "<html><body><h1>Hello from Robust Java Server!</h1></body></html>";
            byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8); //Converting Character stream into Byte Stream

            //Appending HTTP Response Status Line
            res.write("HTTP/1.1 200 OK\r\n");

            //Appending HTTP header fields
            res.write("Content-Type: text/html; charset=utf-8\r\n");
            res.write("Content-Length: " + bodyBytes.length + "\r\n");
            res.write("Connection: close\r\n");

            //Appending CRLF to indicate end of the HTTP header fields section
            res.write("\r\n");
            res.write(body);
            res.flush();//pushing character stream into Byte Stream
        }
    }
}