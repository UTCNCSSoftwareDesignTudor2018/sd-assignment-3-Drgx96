package workers;

import builders.MessageBuilder;
import com.google.gson.Gson;
import messages.Message;
import presenter.Presenter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OutputWorker extends Thread {
    private final Socket socket;
    private ConcurrentLinkedQueue<Message> messagesToBeSent;
    private PrintWriter out;

    public OutputWorker(Presenter clientController) {
        messagesToBeSent = new ConcurrentLinkedQueue<>();
        this.socket = clientController.getSocket();
    }

    @Override
    public void run() {
        super.run();
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            while (socket.isConnected()) {
                if (messagesToBeSent.size() != 0) {
                    sendMessage(messagesToBeSent.poll());
                } else {
                    yield();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Message message) {
        Gson gson = new Gson();
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setMessageName(message.getClass().getSimpleName());
        messageBuilder.setBody(gson.toJson(message));
        out.print(messageBuilder.build());
        out.flush();
    }

    public void addMessageToBeSent(Message message) {
        messagesToBeSent.add(message);
    }
}
