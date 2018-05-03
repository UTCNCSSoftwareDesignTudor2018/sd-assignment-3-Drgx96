package messages;

import models.Article;

public class UpdateArticleRequest extends Message {
    public Article article;

    public UpdateArticleRequest(Article article) {
        this.article = article;
    }
}
