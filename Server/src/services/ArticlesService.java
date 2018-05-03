package services;

import models.Article;
import repos.ArticleRepo;

import java.util.List;
import java.util.Observable;

public class ArticlesService extends Observable {

    private ArticleRepo articleRepo;

    public ArticlesService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public List<Article> getArticles() {
        return articleRepo.getArticles();
    }

    public void deleteArticle(String id) {
        articleRepo.deleteArticle(id);
        notifyClients();
    }

    private synchronized void notifyClients() {
        setChanged();
        notifyObservers();
    }

    public void addArticle(Article article) {
        articleRepo.insertArticle(article);
        notifyClients();
    }

    public void updateArticle(Article article) {
        articleRepo.updateArticle(article);
        notifyClients();
    }
}
