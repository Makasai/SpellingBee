package spellingbee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoMultiServer {
    private ServerSocket serverSocket;

    public void start() throws Exception {
        serverSocket = new ServerSocket(5555);
        while (true) {
            Socket s = null;
            s = serverSocket.accept();
            System.out.println("A new client connection: " + s);
            new EchoClientHandler(s).start();
        }
    }

    public void stop() throws Exception {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run(){
            String inputLine;

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while ((inputLine = in.readLine()) != null) {
                    System.out.println("now restart");
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    out.println(inputLine);
                    System.out.println("incoming: " + inputLine);
                }

                System.out.println("end?");
                in.close();
                out.close();
                clientSocket.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        EchoMultiServer server = new EchoMultiServer();
        server.start();
    }
}
