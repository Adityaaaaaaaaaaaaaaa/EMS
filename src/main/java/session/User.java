package session;

public class User {
    private String id;
    private String role;
    private String name;
    private String email;
    private String phone;

    public User(String id, String role, String name, String email, String phone) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
