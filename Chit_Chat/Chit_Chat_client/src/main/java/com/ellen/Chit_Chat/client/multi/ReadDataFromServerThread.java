package com.ellen.Chit_Chat.client.multi;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class ReadDataFromServerThread extends Thread{
    private final Socket client;

    public ReadDataFromServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //获取客户端的输入流
            InputStream clientInput = client.getInputStream();
            Scanner scanner = new Scanner(clientInput);

            while(true){
                String message = scanner.nextLine();
                System.out.println("来自服务器的消息："+message);
                if(message.equals("bye")){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
