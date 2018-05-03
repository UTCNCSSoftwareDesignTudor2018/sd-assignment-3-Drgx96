package workers;

import controller.ClientsManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private final ClientsManager clientThreadsManager;

    public SocketServer(ClientsManager clientThreadsManager) {
        this.clientThreadsManager = clientThreadsManager;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(5005);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientThreadsManager.createThreadForClient(clientSocket);
                System.out.println("Connected to " + clientSocket.getRemoteSocketAddress().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
