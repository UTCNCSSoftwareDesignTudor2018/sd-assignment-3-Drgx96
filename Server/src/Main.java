import com.mongodb.DB;
import com.mongodb.MongoClient;
import controller.ClientsManager;
import repos.AdminRepo;
import repos.ArticleRepo;
import services.AdminsService;
import services.ArticlesService;
import workers.SocketServer;

import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server starting...");
        DB mongoClient = null;
        try {
            mongoClient = new MongoClient().getDB("admin");
            SocketServer socketServer = new SocketServer(
                    new ClientsManager(
                            new ArticlesService(
                                    new ArticleRepo(
                                            mongoClient.getCollection("articles"))),
                            new AdminsService(
                                    new AdminRepo(
                                            mongoClient.getCollection("admins")))));
            socketServer.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
