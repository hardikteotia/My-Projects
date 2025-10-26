
package MultiThreaded;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return clientSocket -> {
            try (
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                toClient.println("Hello from the server!");
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(10000); // 10 seconds timeout
            System.out.println("Server is listening on port " + port);
            while (true) {
                try {
                    Socket acceptedSocket = serverSocket.accept();
                    new Thread(() -> server.getConsumer().accept(acceptedSocket)).start();
                } catch (SocketTimeoutException e) {
                    System.out.println("Socket timed out!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
