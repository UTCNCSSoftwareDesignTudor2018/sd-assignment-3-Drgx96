package workers;

import com.google.gson.Gson;
import controller.ClientController;
import messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputWorker extends Thread {
    private final ClientController clientController;
    private final Socket socket;
    private BufferedReader in;

    public InputWorker(ClientController clientController) {
        this.socket = clientController.getSocket();
        this.clientController = clientController;
    }

    @Override
    public void run() {
        super.run();
        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            while (socket.isConnected()) {
                try {
                    Message receivedMessage = receiveMessage();
                    if (receivedMessage == null)
                        System.out.println("Wrong message type received!");
                    else
                        clientController.handleMessage((receivedMessage.getClass().cast(receivedMessage)));
                } catch (IOException e) {
                    clientController.stopIt();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Message receiveMessage() throws IOException {
        try {
            String lastStringRead = null;
            while (!"start".equals(lastStringRead))
                lastStringRead = in.readLine();
            lastStringRead = in.readLine();
            if (!lastStringRead.startsWith("type:"))
                return null;
            String className = lastStringRead.substring("type:".length());
            Class classOfMessage = Class.forName("messages." + className);
            lastStringRead = in.readLine();
            if (!lastStringRead.startsWith("length:"))
                return null;
            long length = Long.parseLong(lastStringRead.substring("length:".length()));
            StringBuilder jsonString = new StringBuilder();
            while (length > 0) {
                jsonString.append((char) in.read());
                length--;
            }
            return (Message) new Gson().fromJson(jsonString.toString(), classOfMessage);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
