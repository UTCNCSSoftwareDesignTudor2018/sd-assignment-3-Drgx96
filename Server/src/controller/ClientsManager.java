package controller;

import services.AdminsService;
import services.ArticlesService;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientsManager {
    private List<ClientController> clientThreads;
    private ArticlesService articlesService;
    private AdminsService adminsService;

    public ClientsManager(ArticlesService articlesService, AdminsService adminsService) {
        this.articlesService = articlesService;
        this.adminsService = adminsService;
        clientThreads = new ArrayList<>();
    }


    public void createThreadForClient(Socket clientSocket) {
        ClientController clientThread = new ClientController(clientSocket, articlesService, adminsService);
        clientThread.start();
    }
}
