package messages;

public class DeleteArticleRequest extends Message {
    public String id;

    public DeleteArticleRequest(String id) {
        this.id = id;
    }
}
