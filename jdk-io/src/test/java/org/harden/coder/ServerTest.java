package org.harden.coder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",80));
        try(OutputStream outputStream = socket.getOutputStream()){
            String msg="hello socket";
            outputStream.write(msg.getBytes());
            outputStream.flush();
        }
        socket.close();


    }
}
