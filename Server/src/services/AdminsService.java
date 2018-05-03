package services;

import repos.AdminRepo;

public class AdminsService {
    private final AdminRepo adminRepo;

    public AdminsService(AdminRepo admins) {
        this.adminRepo = admins;
    }

    public boolean validateCridentials(String user, String password) {
        return adminRepo.getAdmins().stream().filter(x -> x.user.equals(user) && x.password.equals(password)).count() > 0;
    }
}
