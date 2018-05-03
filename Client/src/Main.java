import presenter.Presenter;
import view.FeedView;

import java.io.IOException;
import java.net.Socket;

public class Main {
    private static String hostName = "localhost";
    private static int portNumber = 5005;

    public static void main(String[] args) {
        try {
            Presenter presenter = new Presenter(new Socket(hostName, portNumber), new FeedView());
            presenter.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
