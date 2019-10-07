package jkam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        System.out.println(clientSocket);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) { // does it read from the line during runtime? You can do this in java? Seems...too dynamic for it haha
            System.out.println("yo restart");
            if (".".equals(inputLine)) {
                out.println("Good bye");
                break;
            }
            out.println(inputLine + 'z');

        }
    }

    public static void main(String[] args) throws Exception {
        EchoServer server = new EchoServer();
        server.start(5555);
    }
}
