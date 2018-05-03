package messages;

import models.Article;

public class InsertArticleRequest extends Message {
    public Article article;

    public InsertArticleRequest(Article article) {
        this.article = article;
    }
}
