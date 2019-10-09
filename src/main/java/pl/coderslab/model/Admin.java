package pl.coderslab.model;

import pl.coderslab.utils.PasswordUtil;

public class Admin {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private int superadmin;
    private int enable;

    public Admin() {
    }

    public Admin(String first_name, String last_name, String email, String password, int superadmin, int enable) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = PasswordUtil.createHash(password);
        this.superadmin = superadmin;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getSuperadmin() {
        return superadmin;
    }

    public int getEnable() {
        return enable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSuperadmin(int superadmin) {
        this.superadmin = superadmin;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
