package controller;

import messages.*;
import models.Article;
import services.AdminsService;
import services.ArticlesService;
import workers.InputWorker;
import workers.OutputWorker;

import java.net.Socket;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ClientController implements Observer {
    private final Socket socket;
    private final ArticlesService articlesService;
    private final AdminsService adminsService;
    private Boolean authenticated;
    private InputWorker inputWorker;
    private OutputWorker outputWorker;

    public ClientController(Socket clientSocket, ArticlesService articlesService, AdminsService adminsService) {
        this.socket = clientSocket;
        this.authenticated = false;
        this.articlesService = articlesService;
        this.adminsService = adminsService;
        articlesService.addObserver(this);
    }

    public void run() {
        inputWorker = new InputWorker(this);
        outputWorker = new OutputWorker(this);
        inputWorker.start();
        outputWorker.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        List<Article> articles = articlesService.getArticles();
        ListArticlesResponse listArticlesResponse = new ListArticlesResponse();
        listArticlesResponse.articles = articles;
        outputWorker.addMessageToBeSent(listArticlesResponse);
    }

    public Socket getSocket() {
        return socket;
    }

    public void handleMessage(Message receivedMessage) {
        if (receivedMessage instanceof ListArticlesRequest) {
            handleMessage((ListArticlesRequest) receivedMessage);
            return;
        }
        if (receivedMessage instanceof UpdateArticleRequest) {
            if (authenticated)
                handleMessage((UpdateArticleRequest) receivedMessage);
            else
                outputWorker.addMessageToBeSent(new NotAuthenticatedResponse());
            return;
        }
        if (receivedMessage instanceof DeleteArticleRequest) {
            if (authenticated)
                handleMessage((DeleteArticleRequest) receivedMessage);
            else
                outputWorker.addMessageToBeSent(new NotAuthenticatedResponse());
            return;
        }
        if (receivedMessage instanceof InsertArticleRequest) {
            if (authenticated)
                handleMessage((InsertArticleRequest) receivedMessage);
            else
                outputWorker.addMessageToBeSent(new NotAuthenticatedResponse());
            return;
        }
        if (receivedMessage instanceof AuthenticationRequest) {
            handleMessage((AuthenticationRequest) receivedMessage);
            return;
        }
    }

    public void handleMessage(ListArticlesRequest receivedMessage) {
        List<Article> articles = articlesService.getArticles();
        outputWorker.addMessageToBeSent(new ListArticlesResponse(articles));
    }

    public void handleMessage(DeleteArticleRequest receivedMessage) {
        articlesService.deleteArticle(receivedMessage.id);
    }

    public void handleMessage(InsertArticleRequest receivedMessage) {
        articlesService.addArticle(receivedMessage.article);
    }

    public void handleMessage(AuthenticationRequest receivedMessage) {
        authenticated = adminsService.validateCridentials(receivedMessage.user, receivedMessage.password);
        if (authenticated)
            outputWorker.addMessageToBeSent(new AuthenticatedResponse());
    }

    public void handleMessage(UpdateArticleRequest receivedMessage) {
        articlesService.updateArticle(receivedMessage.article);
    }

    public void stopIt() {
        inputWorker.interrupt();
        outputWorker.interrupt();
        this.interrupt();
    }
}
