package com.ellen.Chit_Chat.client.single;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SingleThreadClient {
    public static void main(String[] args) throws IOException {
        Socket client = null;
        Scanner readFromServer = null;
        PrintStream writeMSgToServer = null;
        try {
            //1.尝试与服务器建立连接
            client = new Socket("127.0.0.1",9999);

            //2.获取此连接的输入输出流
            readFromServer = new Scanner(client.getInputStream());
            writeMSgToServer = new PrintStream(client.getOutputStream());

            //3.进行通信
            writeMSgToServer.println("你好，我是客户端！");
            if(readFromServer.hasNext()){
                System.out.println("服务器说："+readFromServer.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            client.close();
            readFromServer.close();
            writeMSgToServer.close();
        }
    }
}