package models;

public class Article {
    private String Id;
    private String Title;
    private String Abstract;
    private String Author;
    private String Body;

    public Article(String id, String title, String abstr, String author, String body) {
        Id = id;
        Title = title;
        Abstract = abstr;
        Author = author;
        Body = body;
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
}
