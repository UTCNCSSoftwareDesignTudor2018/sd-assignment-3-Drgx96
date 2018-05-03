package models;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Admin {
    public String user;
    public String password;

    public Admin(DBObject x) {
        this.user = (String) x.get("user");
        this.password = (String) x.get("password");
    }

    public DBObject toDBObject() {
        return new BasicDBObject("user", this.user)
                .append("password", this.password);
    }
}
