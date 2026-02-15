import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.io.IOException;

import Model.Task;

public class App
{
    public static void main(String[] args) throws IOException
    {
        ExecutorService threadPool=Executors.newFixedThreadPool(100);
        ServerSocket socket=new ServerSocket(2025,50, InetAddress.getByName("127.0.0.1"));
        System.out.println("Server is listening on port: 2025");
        while(true)
        {
            Socket connection=socket.accept();
            Task taskobj=new Task(connection);
            threadPool.execute(taskobj);
        }
    }
}
