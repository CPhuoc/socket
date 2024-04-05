import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for clients...");

            List<PrintWriter> clients = new ArrayList<>();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.add(outputToClient);

                Thread clientHandler = new Thread(new ClientHandler(clientSocket, clients));
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<PrintWriter> clients;

    public ClientHandler(Socket clientSocket, List<PrintWriter> clients) {
        this.clientSocket = clientSocket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String clientInput;

            while ((clientInput = inputFromClient.readLine()) != null) {
                System.out.println("Received from client: " + clientInput);

                // Broadcast message to all clients
                for (PrintWriter client : clients) {
                    client.println(clientInput);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
