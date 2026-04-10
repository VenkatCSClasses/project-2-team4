package model;


public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String role; // "USER" or "LIBRARIAN"

    public User(int id, String username, String passwordHash, String role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getId() { 
        return id; 
    }
    public String getUsername() { 
        return username; 
    }
    public String getPasswordHash() { 
        return passwordHash; 
    }
    public String getRole() { 
        return role; 
    }
    public boolean isLibrarian() { 
        return role.equals("LIBRARIAN"); 
    }

    public void setPasswordHash(String passwordHash) { 
        this.passwordHash = passwordHash; 
    }

    public void setRole(String role) { 
        this.role = role; 
    }
    
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', role='" + role + "'}";
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}

