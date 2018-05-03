package repos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import models.Article;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleRepo {
    private DBCollection articlesCollection;

    public ArticleRepo(DBCollection articlesCollection) {
        this.articlesCollection = articlesCollection;
    }

    public List<Article> getArticles() {
        DBCursor cursor = articlesCollection.find();
        LinkedList<DBObject> dbObjects = new LinkedList<>();
        while (cursor.hasNext()) {
            dbObjects.add(cursor.next());
        }
        return dbObjects.stream().map(x -> new Article(x)).collect(Collectors.toList());
    }

    public void insertArticle(Article article) {
        articlesCollection.insert(article.toDBObject());
    }

    public void deleteArticle(String articleId) {
        articlesCollection.findAndRemove(new BasicDBObject("_id", new ObjectId(articleId)));
    }

    public void updateArticle(Article article) {
        DBObject dbObj = article.toDBObject();
        dbObj.removeField("_id");
        articlesCollection.update(new BasicDBObject("_id", new ObjectId(article.getId())), dbObj);
    }
}
