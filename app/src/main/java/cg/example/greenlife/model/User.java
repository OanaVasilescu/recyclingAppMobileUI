package cg.example.greenlife.model;

public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public User(String username, String password, String email, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
