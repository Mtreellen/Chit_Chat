package com.ellen.Chit_Chat.server.single;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SingleThreadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Scanner readFromClient = null;
        PrintStream sendMsgToClient =null;
        try {
            //1.建立服务器
            serverSocket = new ServerSocket(9999);
            System.out.println("等待客户端连接...");

            //2.等待客户端连接
            Socket client = serverSocket.accept();
            System.out.println("有客户端连接,端口号为："+client.getPort()+
                    "   "+"localPort:"+client.getLocalPort());

            //3.获取此连接的输入输出流
            //获取客户端的输入流（即输入开关）
            readFromClient = new Scanner(client.getInputStream());
            //获取客户端的输出流（即输出管道）
            sendMsgToClient = new PrintStream(client.getOutputStream(),
                    true,"UTF-8");

            //4.进行通信
            if(readFromClient.hasNext()){
                System.out.println("客户端说："+readFromClient.nextLine());
            }

            sendMsgToClient.println("你好，我是服务器！");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            serverSocket.close();
            readFromClient.close();
            sendMsgToClient.close();
        }

    }
}
