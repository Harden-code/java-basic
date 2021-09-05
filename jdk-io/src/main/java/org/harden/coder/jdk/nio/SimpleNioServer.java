package org.harden.coder.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class SimpleNioServer {
    private volatile boolean isOpen =true;

    private int port;

    public SimpleNioServer(int port){
        this.port =port;
    }



    public void start() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector selector = Selector.open();

        ssc.configureBlocking(false);
        ssc.register(selector,SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(port));
        while (isOpen){
            if(selector.select()==0){
                continue;
            }
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()){
                SelectionKey selectionKey = selectionKeys.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel= (ServerSocketChannel)selectionKey.channel();
                    SocketChannel accept = serverSocketChannel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ);
                }
                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel=(SocketChannel)selectionKey.channel();

                    ByteBuffer allocate = ByteBuffer.allocate(64);
                    int read = socketChannel.read(allocate);

                    if(read<64){
                        allocate.flip();
                        String msg =new String(allocate.array(),0,allocate.limit(),"UTF-8");
                        System.out.println(msg);
                        //如果客户端关闭后,成为Fin状态,如果不调用close服务端无法关闭连接
                        //就会造成连接浪费
//                        socketChannel.close();
                    }else{
                        System.out.println("over full");
                    }
                }
                selectionKeys.remove();

            }
        }
    }

    public static void main(String[] args) throws IOException {
        SimpleNioServer simpleNioServer = new SimpleNioServer(80);
        simpleNioServer.start();
    }
}
