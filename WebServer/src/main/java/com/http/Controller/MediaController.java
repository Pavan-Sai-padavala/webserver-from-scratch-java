package com.http.Controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import com.http.Model.HttpRequestWrapper;
import com.http.Model.HttpResponseWrapper;

public class MediaController
{
    // GET Request 
    public static void getImage(HttpRequestWrapper req, HttpResponseWrapper res, InputStream in, OutputStream out) throws IOException
    {       
        File fileobj=new File("E:\\pavan sai\\users\\Pictures\\photo.jpg");
        int filelen=(int) fileobj.length();

        BufferedInputStream filestream=new BufferedInputStream(new FileInputStream(fileobj));
        byte[] buffer=new byte[filelen];
        filestream.read(buffer,0,filelen);

        res.setResponseLine("HTTP/1.1 200 OK");
        res.setResponseHeader("Content-Type", new String[]{"image/jpeg"});
        res.setResponseHeader("Connection", new String[]{"close"});
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(buffer.length)});
        out.write(res.toString().getBytes());
        
        out.write(buffer);
        out.flush();

    }  

    //POST Request
    public static void setImage(HttpRequestWrapper req, HttpResponseWrapper res, InputStream in, OutputStream out) throws IOException
    {
        String body = req.getBody();
        if (body == null || body.isEmpty())
        {
            String msg = "No image data received";
            res.setResponseLine("HTTP/1.1 400 Bad Request");
            res.setResponseHeader("Content-Type", new String[]{"text/plain; charset=utf-8"});
            res.setResponseHeader("Connection", new String[]{"close"});
            res.setResponseHeader("Content-Length", new String[]{String.valueOf(msg.getBytes().length)});
            out.write(res.toString().getBytes());
            out.write(msg.getBytes());
            out.flush();
            return;
        }

        // Decode Base64 body to bytes
        byte[] ByteBody;
        ByteBody = Base64.getDecoder().decode(body);

        String outPath = "E:\\pavan sai\\users\\Pictures\\uploaded_" + System.currentTimeMillis() + ".jpg";
        File fileObj = new File(outPath);
        FileOutputStream fileStream = new FileOutputStream(fileObj);
        fileStream.write(ByteBody);

        String msg = "Saved image to: " + outPath;
        res.setResponseLine("HTTP/1.1 201 Created");
        res.setResponseHeader("Content-Type", new String[]{"text/plain; charset=utf-8"});
        res.setResponseHeader("Connection", new String[]{"close"});
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(msg.getBytes().length)});
        out.write(res.toString().getBytes());
        out.write(msg.getBytes());
        out.flush();
    } 

    // GET Request
    public static void getAudio(HttpRequestWrapper req, HttpResponseWrapper res, InputStream in , OutputStream out) throws IOException
    {
        File fileobj=new File("E:\\pavan sai\\users\\Pictures\\[iSongs.info] 04 - Dosti.mp3");
        int filelen=(int) fileobj.length();

        BufferedInputStream filestream=new BufferedInputStream(new FileInputStream(fileobj));
        byte[] buffer=new byte[filelen];
        filestream.read(buffer,0,filelen);
        
        res.setResponseLine("HTTP/1.1 200 OK");
        res.setResponseHeader("Content-Type", new String[]{"audio/mpeg"});
        res.setResponseHeader("Connection", new String[]{"close"});
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(buffer.length)});
        out.write(res.toString().getBytes());
        out.write(buffer);
        out.flush();
    }



    // POST Request
    public static void setAudio(HttpRequestWrapper req, HttpResponseWrapper res, InputStream in, OutputStream out) throws IOException
    {
        byte[] body = Base64.getDecoder().decode(req.getBody());

        String outPath = "E:\\pavan sai\\users\\Pictures\\uploaded_audio_" + System.currentTimeMillis() + ".mp3";
        File fileObj = new File(outPath);
        FileOutputStream fileStream = new FileOutputStream(fileObj);
        fileStream.write(body);

        String msg = "Saved audio to: " + outPath;
        res.setResponseLine("HTTP/1.1 201 Created");
        res.setResponseHeader("Content-Type", new String[]{"text/plain; charset=utf-8"});
        res.setResponseHeader("Connection", new String[]{"close"});
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(msg.getBytes().length)});
        out.write(res.toString().getBytes());
        out.write(msg.getBytes());
        out.flush();
    }

    // GET Request
    public static void getVideo(HttpRequestWrapper req, HttpResponseWrapper res, InputStream rawIn, OutputStream rawOut) throws IOException
    {
        File fileobj = new File("E:\\pavan sai\\users\\Pictures\\clip.mp4");
        int filelen = (int) fileobj.length();
        BufferedInputStream filestream = new BufferedInputStream(new FileInputStream(fileobj));
        
        byte[] buffer = new byte[filelen];
        filestream.read(buffer, 0, filelen);

        res.setResponseLine("HTTP/1.1 200 OK");
        res.setResponseHeader("Content-Type", new String[]{"video/mp4"});
        res.setResponseHeader("Connection", new String[]{"close"});
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(buffer.length)});
        rawOut.write(res.toString().getBytes());
        rawOut.write(buffer);
        rawOut.flush();
    }

    // POST Request
    public static void setVideo(HttpRequestWrapper req, HttpResponseWrapper res, InputStream rawIn, OutputStream rawOut) throws IOException
    {
        byte[] body = Base64.getDecoder().decode(req.getBody());
        String outPath = "E:\\pavan sai\\users\\Pictures\\uploaded_video_" + System.currentTimeMillis() + ".mp4";
        File fileObj = new File(outPath);
        FileOutputStream fileStream = new FileOutputStream(fileObj);
        fileStream.write(body);

        String msg = "Saved video to: " + outPath;
        res.setResponseLine("HTTP/1.1 201 Created");
        res.setResponseHeader("Content-Type", new String[]{"text/plain; charset=utf-8"});
        res.setResponseHeader("Connection", new String[]{"close"});
        res.setResponseHeader("Content-Length", new String[]{String.valueOf(msg.getBytes().length)});
        rawOut.write(res.toString().getBytes());
        rawOut.write(msg.getBytes());
        rawOut.flush();
    }
}
