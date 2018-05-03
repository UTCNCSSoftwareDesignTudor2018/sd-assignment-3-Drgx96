package messages;

import models.Admin;

public class AuthenticationRequest extends Message {
    public String user;
    public String password;

    public AuthenticationRequest(Admin admin) {
        user = admin.user;
        password = admin.password;
    }
}
