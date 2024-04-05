import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            String serverResponse;

            while (true) {
                System.out.print("Client: ");
                userInput = inputFromUser.readLine();
                outputToServer.println(userInput);

                if ((serverResponse = inputFromServer.readLine()) != null) {
                    System.out.println("Server: " + serverResponse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
