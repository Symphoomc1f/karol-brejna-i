package org.lantern;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerSocketTest
 * <p>
 * <p></p>
 *
 * @author Vigor Yuan
 */
public class ServerSocketTest {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8000);
            Socket socket = serverSocket.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
