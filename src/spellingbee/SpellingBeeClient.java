package spellingbee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;;

public class SpellingBeeClient {
    private int PORT = 5555;
    private String IP = "127.0.0.1";
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner myScanner = new Scanner(System.in);
    private ArrayList<String> answers = new ArrayList<>();

    public void startConnection(String ip, int port) {
        
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
        } catch (Exception e) {
            System.out.println("Error connecting: " + e);
        }
    }

    public String sendMessage(String msg)  {
        try {
            out.println(msg);
            String resp = in.readLine();
            return resp;
            
        } catch (Exception e) {
            System.out.println("Error sending message: " + e);
        }

        return null;
    }

    public void stopConnection() {
        try {
            
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Could not close streams and or socket. Might leak");
        }

        
    }

    public String getUserInput() {
        System.out.print("Enter word: ");
        String guess = myScanner.nextLine();
        return guess;
    }

    public void runner() {
        
        
        try {

            this.startConnection(IP, PORT);
            String user_input = "hello";

            while (user_input != "end") {
                System.out.println(answers);
                user_input = this.getUserInput();
                String resp = this.sendMessage((user_input));
                if (!resp.equals("yes")) {
                    continue;
                }
                answers.add(user_input);

            }
            System.out.println("CLosing streams");


        } catch (Exception e) {
            System.out.println("Error in runner: " + e);
            this.stopConnection();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        SpellingBeeClient client = new SpellingBeeClient();
        client.runner();
    }
}
