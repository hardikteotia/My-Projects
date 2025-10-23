// package MultiThreaded;
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.net.ServerSocket;
// import java.net.Socket;
// import java.util.function.Consumer;

// public class Server {
    
//     public Consumer<Socket> getConsumer(){
//         return (clientSocket)->{
//             try{
//                 PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
//                 toClient.println("Hello from the server");
//                 toClient.close();
//                 clientSocket.close();
//             }
//             catch(Exception e){
//                 e.printStackTrace();
//             }
//         };
//     }


//     public static void main(String[] args) {
//         int port = 8010;
//         Server server = new Server();
//         try{
//             ServerSocket serverSocket = new ServerSocket(port);
//             // serverSocket.setSoTimeout(10000);
//             System.out.println("Server is Listening on port " + port);
//             while (true) {
//                 Socket acceptedSocket = serverSocket.accept();
//                 Thread thread = new Thread(()->{server.getConsumer().accept(acceptedSocket);});
//                 thread.start();
//             }
//         }
//         catch(IOException e){
//             e.printStackTrace();
//         }
//     }
// }


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
