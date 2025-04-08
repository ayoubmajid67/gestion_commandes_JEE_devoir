package model;


import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Compte {
    private final String id;
    private String nom;
    private String email;
    private String password;
    private String compteType;

    // Default Constructor
    public Compte() {
        this.id = UUID.randomUUID().toString();
        this.nom = "unknown";
        this.email = "unknown";
        this.password = "unknown";
        this.compteType = "unknown";
    }

    // Parameterized Constructor
    public Compte(String nom, String email, String password, String compteType) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.compteType = compteType;
    }

    // Constructor with ID
    public Compte(String id, String nom, String email, String password, String compteType) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.compteType = compteType;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompteType() {
        return compteType;
    }

    public void setCompteType(String compteType) {
        this.compteType = compteType;
    }

    @JsonIgnore
    public boolean isAdmin() {
        return "admin".equals(compteType);
    }

    @JsonIgnore
    public boolean isUser() {
        return "user".equals(compteType);
    }

    // Edit Account Method
    public void editAccount(Compte updatedAccount) {
        this.nom = updatedAccount.nom;
        this.email = updatedAccount.email;
        this.password = updatedAccount.password;
        this.compteType = updatedAccount.compteType;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", compteType='" + compteType + '\'' +
                '}';
    }
}