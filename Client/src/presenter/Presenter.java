package presenter;

import messages.*;
import models.Admin;
import models.Article;
import view.FeedView;
import workers.InputWorker;
import workers.OutputWorker;

import java.net.Socket;
import java.util.stream.Collectors;

public class Presenter extends Thread {
    private FeedView feedView;
    private Socket socket;
    private InputWorker inputWorker;
    private OutputWorker outputWorker;

    public Presenter(Socket clientSocket, FeedView feedView) {
        this.feedView = feedView;
        this.socket = clientSocket;
        inputWorker = new InputWorker(this);
        outputWorker = new OutputWorker(this);
    }

    public void run() {
        inputWorker.start();
        outputWorker.start();
        feedView.setPresenter(this);
        feedView.setVisible(true);
        outputWorker.addMessageToBeSent(new ListArticlesRequest());
    }

    public Socket getSocket() {
        return socket;
    }

    public void handleMessage(Message receivedMessage) {
        if (receivedMessage instanceof ListArticlesResponse) {
            handleMessage((ListArticlesResponse) receivedMessage);
            return;
        }
        if (receivedMessage instanceof AuthenticatedResponse) {
            handleMessage((AuthenticatedResponse) receivedMessage);
            return;
        }
        if (receivedMessage instanceof NotAuthenticatedResponse) {
            handleMessage((NotAuthenticatedResponse) receivedMessage);
            return;
        }
    }

    private void handleMessage(ListArticlesResponse receivedMessage) {
        feedView.setArticles(receivedMessage.articles.stream()
                .map(x -> new String[]{x.getId(), x.getTitle(), x.getAbstract(), x.getAuthor(), x.getBody()})
                .collect(Collectors.toList()));
    }

    private void handleMessage(AuthenticatedResponse receivedMessage) {
        feedView.setAuthenticated(true);
    }

    private void handleMessage(NotAuthenticatedResponse receivedMessage) {
        feedView.setAuthenticated(false);
    }

    public void addArticle(String title, String abstr, String author, String body) {
        Article article = new Article("", title, abstr, author, body);
        outputWorker.addMessageToBeSent(new InsertArticleRequest(article));
    }

    public void updateArticle(String id, String title, String abstr, String author, String body) {
        Article article = new Article(id, title, abstr, author, body);
        outputWorker.addMessageToBeSent(new UpdateArticleRequest(article));
    }

    public void deleteArticle(String id) {
        outputWorker.addMessageToBeSent(new DeleteArticleRequest(id));
    }

    public void authenticate(String user, String password) {
        outputWorker.addMessageToBeSent(new AuthenticationRequest(new Admin(user, password)));
    }

    public void logOut() {
        feedView.setAuthenticated(false);
    }
}
