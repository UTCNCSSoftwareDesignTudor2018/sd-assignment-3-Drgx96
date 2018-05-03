package models;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class Article {
    private String Id;
    private String Title;
    private String Abstract;
    private String Author;
    private String Body;

    public Article() {

    }

    public Article(String title, String anAbstract, String author, String body) {
        Title = title;
        Abstract = anAbstract;
        Author = author;
        Body = body;
    }

    public Article(DBObject x) {
        Id = ((ObjectId) x.get("_id")).toHexString();
        Title = (String) x.get("title");
        Abstract = (String) x.get("abstract");
        Author = (String) x.get("author");
        Body = (String) x.get("body");
    }

    public String getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public DBObject toDBObject() {
        if (getId().equals(""))
            return new BasicDBObject("title", this.getTitle())
                    .append("abstract", this.getAbstract())
                    .append("author", this.getAuthor())
                    .append("body", this.getBody());
        return new BasicDBObject("_id", this.getId())
                .append("title", this.getTitle())
                .append("abstract", this.getAbstract())
                .append("author", this.getAuthor())
                .append("body", this.getBody());
    }
}
