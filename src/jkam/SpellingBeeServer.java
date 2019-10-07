package jkam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SpellingBeeServer {

    private int PORT = 5555;
    private ServerSocket serverSocket;
    public static ArrayList<String> word_bank = CreateWordBank();

    public static ArrayList<String> CreateWordBank() {
        ArrayList<String> word_bank = new ArrayList<String>();
        word_bank.add("cat".toUpperCase());
        word_bank.add("fat".toUpperCase());
        word_bank.add("sat".toUpperCase());
        word_bank.add("bat".toUpperCase());
        word_bank.add("hat".toUpperCase());

        return word_bank;
    }

    public void start() {

        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket s = serverSocket.accept();
                System.out.println("Connection established: " + s);
                new SpellingBeeClientHandler(s).start();
            }
        } catch (Exception e) {
            System.out.println("Error starting up server" + e);
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    private static class SpellingBeeClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public SpellingBeeClientHandler(Socket socket) {
            this.clientSocket = socket;
        }


        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;


                while ((inputLine = in.readLine()) != null ) {

                    if (".".equals(inputLine)) {
                        out.println("Closing connection");
                        break;
                    }
                    System.out.println("incoming word: " + inputLine);


                    String guess = inputLine.toUpperCase();
                    if (word_bank.contains(guess)) {
                        out.println("yes");
                        word_bank.remove(guess);
                    } else {
                        out.println(word_bank.contains(inputLine));
                    }
                }

            } catch (Exception e) {
                System.out.println("Error getting i/o streams" + e);
            }
        }



    }

    public static void main(String[] args) {
        SpellingBeeServer server = new SpellingBeeServer();
        server.start();
    }


}