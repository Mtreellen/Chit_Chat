package com.ellen.Chit_Chat.server.multi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    public static void main(String[] args) {
        int port = 5678;
        if(args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                System.out.println("端口参数不对，采用默认端口" + port);
            }
        }

        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("等待客户端连接中...");

            while(true){
                Socket client = serverSocket.accept();
                executorService.submit(new ExecuteClient(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
