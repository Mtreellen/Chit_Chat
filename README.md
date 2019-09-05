# 聊天室

## 1 Socket API

### 1.1 Socket 编程

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;套接字使⽤TCP提供了两台计算机之间的通信机制。 客户端程序创建⼀个套接字，并尝试连接服务器的
套接字。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当连接建⽴时，服务器会创建⼀个 Socket 对象。客户端和服务器现在可以通过对 Socket 对象的写⼊和
读取来进⾏通信。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**java.net.Socket 类代表⼀个套接字，并且 java.net.ServerSocket 类为服务器程序提供了⼀种来监听客户端，并与他们建⽴连接的机制。**

> 以下步骤在两台计算机之间使⽤套接字建⽴TCP连接时会出现：
>
> - 服务器实例化⼀个 ServerSocket 对象，表示通过服务器上的端⼝通信。
> - 服务器调⽤ ServerSocket 类的 accept() ⽅法，该⽅法将⼀直等待，直到客户端连接到服务器上给定的端⼝。
> - 服务器正在等待时，⼀个客户端实例化⼀个 Socket 对象，指定服务器名称和端⼝号来请求连接。
> - Socket 类的构造函数试图将客户端连接到指定的服务器和端⼝号。如果通信被建⽴，则在客户端创建⼀个 Socket 对象能够与服务器进⾏通信。
> - 在服务器端，accept() ⽅法返回服务器上⼀个新的 socket 引⽤，该 socket 连接到客户端的socket。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;连接建⽴后，通过使⽤ I/O 流在进⾏通信，每⼀个socket都有⼀个输出流和⼀个输⼊流，客户端的输出
流连接到服务器端的输⼊流，⽽客户端的输⼊流连接到服务器端的输出流。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TCP 是⼀个双向的通信协议，因此数据可以通过两个数据流在同⼀时间发送.以下是⼀些类提供的⼀套完整的有⽤的⽅法来实现 socket。

### 1.2 ServerSocket

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务器应⽤程序通过使⽤ java.net.ServerSocket 类以获取⼀个端⼝,并且侦听客户端请求。ServerSocket 类有四个构造⽅法：

```java 
public ServerSocket(int port)throws IOException //创建绑定到指定端口的服务器套接字。
    
public ServerSocket(int port, inr backlog)throws IOException //利用指定的 backlog创建服务器套接字并将其绑定到指定的本地端口号。

public ServerSocket(int port, inr backlog， InetAddress address)throws IOException //使用指定的端口、侦听 backlog 和要绑定到的本地的 IP 地址创建服务器。

public ServerSocket()throws IOException //创建非绑定服务器套接字
```

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建⾮绑定服务器套接字。 如果 ServerSocket 构造⽅法没有抛出异常，就意味着你的应⽤程序已经成功绑定到指定的端⼝，并且侦听客户端请求。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务端socket处理客户端socket连接是需要⼀定时间的。ServerSocket有⼀个队列，存放还没有来得及处理的客户端Socket，这个队列的容量就是backlog的含义。如果队列已经被客户端socket占满了，如果还有新的连接过来，那么ServerSocket会拒绝新的连接。也就是说backlog提供了容量限制功能，避免太多的客户端socket占⽤太多服务器资源。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;⼀些 ServerSocket 类的常⽤⽅法：

```java
public int getLocalPort() //返回此套接字在其上侦听的端口

public Socket accept()throws IOException //侦听并接受到此套接字的连接

public void setSoTimeout(int timeout) //通过指定超时值启用/禁止SO_TIMEOUT，以毫秒为单位。timeout指的是InputStream的读取超时时间。

public void bind(SocketAddress host, int backlog) //将 SeverSocket 绑定到特定地址（IP地址和端口号）
```

### 1.3 Socket

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;java.net.Socket 类代表客户端和服务器都⽤来互相沟通的套接字。客户端要获取⼀个 Socket 对象通过实例化 ，⽽ 服务器获得⼀个 Socket 对象则通过 accept() ⽅法的返回值。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Socket 类有五个构造⽅法.

```java
//创建一个流套接字并将其连接到指定主机上的指定端口号
public Socket(String host, int port)throws UnknownHostException, IOException

//创建一个流套接字并将其连接到指定 IP 地址的指定端口号
piblic Socket(InetAddress host, int port)throws IOException

//创建一个套接字并将其连接到指定的远程主机上的指定远程端口
public Socket(String host, int port, InetAddress localAddress, int localPort)throws IOException

//创建一个套接字并将其连接到指定远程地址上的指定远程端口
public Socket(InetAddress host, int port, InetAddress localAddress, int localPort)throws IOException

//通过系统默认类型的 SocketImpl 创建未连接套接字
public Socket()
