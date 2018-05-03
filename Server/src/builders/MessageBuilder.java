package builders;

public class MessageBuilder {
    private static String startingPhrase = "start";
    private static String typePhrase = "type:";
    private static String sizePhrase = "length:";
    private String body;
    private String messageName;

    public void setBody(String jsonBody) {
        body = jsonBody;
    }

    public void setMessageName(String simpleName) {
        messageName = simpleName;
    }

    public String build() {
        return startingPhrase + '\n' +
                typePhrase + messageName + '\n' +
                sizePhrase + body.length() + '\n' +
                body;
    }
}
