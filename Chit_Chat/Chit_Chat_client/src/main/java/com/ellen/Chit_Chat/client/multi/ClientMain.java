package com.ellen.Chit_Chat.client.multi;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        try {
            int port = 5678;
            if (args.length > 0) {
                try {
                    port = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("端口参数不对，采用默认端口" + port);
                }
            }
            String host = "127.0.0.1";
            if (args.length > 1) {
                host = args[1];
            }

            final Socket socket = new Socket(host, port);
            //往服务器发送数据
            new WriteDataToServerThread(socket).start();
            //从服务器读数据
            new ReadDataFromServerThread(socket).start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
