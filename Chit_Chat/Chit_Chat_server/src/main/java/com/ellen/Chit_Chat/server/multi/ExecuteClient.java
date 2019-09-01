package com.ellen.Chit_Chat.server.multi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ExecuteClient implements Runnable{

    private static final Map<String,Socket> ONLINE_USER_MAP = new ConcurrentHashMap<String, Socket>();

    private final Socket client;

    public ExecuteClient(Socket client) {
        this.client = client;
    }

    public void run() {
        /*
         * 注册： userName: ***
         * 私聊： private: name:  message
         * 群聊： group: message
         * 退出： bye
         * */
        try {
            InputStream clientInput = this.client.getInputStream();
            Scanner scanner = new Scanner(clientInput);

            while(true){
                String line = scanner.nextLine();
                //要注册,注册：userName: ***
                if(line.startsWith("userName")){
                    String userName = line.split("\\:")[1];
                    this.register(userName,client);
                    continue;
                }
                //要私聊,私聊： private: name:  message
                if(line.startsWith("private")){
                    String[] segments = line.split("\\:");
                    String userName = segments[1];
                    String message = segments[2];
                    this.privateChat(userName,message);
                    continue;
                }
                //要群聊，群聊： group: message
                if(line.startsWith("group")){
                    String message = line.split("\\:")[1];
                    this.groupChat(message);
                    continue;

                }
                if(line.startsWith("bye")){
                    this.quit();
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //注册
    private void register(String userName, Socket client){
        System.out.println(userName+"来聊天室划水了"+client.getRemoteSocketAddress());
        ONLINE_USER_MAP.put(userName,client);
        printOnlineUser();
        sendMessage(this.client, userName+"加入聊天室");
        System.out.println();
    }
    //私聊
    private void privateChat(String userName, String message){
        String currentUserName = this.getCurrentUserName();
        Socket target = ONLINE_USER_MAP.get(userName);
        if(target!= null){
            this.sendMessage(target,currentUserName+"给你私聊的消息是："+message);
        }
    }
    //群聊
    private void groupChat(String message){
        for(Socket socket : ONLINE_USER_MAP.values()){
            if(socket.equals(this.client)){
                continue;
            }
            this.sendMessage(socket,this.getCurrentUserName()+"群聊的消息是："+message);
        }

    }
    //退出
    private void quit(){
        String currentUserName = this.getCurrentUserName();
        System.out.println("用户："+currentUserName+"下线了");
        Socket socket = ONLINE_USER_MAP.get(currentUserName);
        this.sendMessage(socket,"bye");
        ONLINE_USER_MAP.remove(currentUserName);
        printOnlineUser();
    }
    //当前在线的人
    private static void printOnlineUser(){
        System.out.println("当前在线人数："+ONLINE_USER_MAP.size());
        if(ONLINE_USER_MAP.size() != 0) {
            System.out.println("用户如下：");
            for (Map.Entry<String, Socket> entry : ONLINE_USER_MAP.entrySet()) {
                System.out.println(entry.getKey());
            }
        }
    }
    //发送到客户端的信息
    private void sendMessage(Socket socket, String message){
        try {
            OutputStream clientOutput = socket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            writer.write(message+"\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //获取当前用户名
    private String getCurrentUserName(){
        String currentUserName = " ";
        for(Map.Entry<String,Socket> entry: ONLINE_USER_MAP.entrySet()){
            if(this.client.equals(entry.getValue())){
                currentUserName = entry.getKey();
                break;
            }
        }
        return currentUserName;
    }
}