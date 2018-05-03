package messages;

import models.Article;

import java.util.List;

public class ListArticlesResponse extends Message {
    public List<Article> articles;

    public ListArticlesResponse() {
    }

    public ListArticlesResponse(List<Article> articles) {
        this.articles = articles;
    }
}
