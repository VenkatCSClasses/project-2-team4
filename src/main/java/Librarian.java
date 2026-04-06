public class Librarian {
    private String username;
    private String password;

    public Librarian(String user, String pass){
        this.username = user;
        this.password = pass;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }
}
