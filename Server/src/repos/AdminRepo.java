package repos;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import models.Admin;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminRepo {
    private final DBCollection adminsCollection;

    public AdminRepo(DBCollection adminsCollection) {
        this.adminsCollection = adminsCollection;
    }

    public List<Admin> getAdmins() {
        DBCursor cursor = adminsCollection.find();
        LinkedList<DBObject> dbObjects = new LinkedList<>();
        while (cursor.hasNext()) {
            dbObjects.add(cursor.next());
        }
        return dbObjects.stream().map(x -> new Admin(x)).collect(Collectors.toList());
    }

    public void insertAdmin(Admin admin) {
        adminsCollection.insert(admin.toDBObject());
    }

    public void deleteAdmin(Admin admin) {
        adminsCollection.remove(admin.toDBObject());
    }
}
