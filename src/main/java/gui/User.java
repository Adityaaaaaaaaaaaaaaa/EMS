package gui;

class User {
     String email;
     String password;
     String name;
     String phone;
     String address;

    public User(String email, String password, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public User() {}
}
