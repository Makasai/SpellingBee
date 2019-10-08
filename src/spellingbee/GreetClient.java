package spellingbee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GreetClient {
    private int PORT = 5555;
    private String IP = "127.0.0.1";
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner myScanner = new Scanner(System.in);


    public void startConnection(String ip, int port) throws Exception {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws Exception  {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
    }

    public String getUserInput() {
        System.out.println("Enter word: ");
        String guess = myScanner.nextLine();
        return guess;
    }

    public void runner() throws Exception {

        this.startConnection(IP, PORT);
        while (true) {
            String user_input = getUserInput();

            String resp = this.sendMessage(user_input);
            System.out.println(resp);
        }

    }

    public static void main(String[] args) throws Exception {
        GreetClient client = new GreetClient();
        client.runner();
    }
}
