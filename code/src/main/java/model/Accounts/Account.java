package model.Accounts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import model.Commande;

import java.util.UUID;
public class Account {
    private final String idAccount;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String domain;
    private String accountType;

    // Constructor
    public  Account() {
        this.idAccount = UUID.randomUUID().toString();
        this.firstName = "unknown";
        this.lastName = "unknown";
        this.email = "unknown";
        this.password = "unknown";
        this.phoneNumber = "unknown";
        this.domain = "unknown";
        this.accountType = "unknown";

    }
    public Account(String firstName, String lastName, String email,
                   String password, String phoneNumber, String domain, String accountType) {
        this.idAccount=UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.domain = domain;
        this.accountType = accountType;
    }

    public  Account(String idAccount, String email , String password,String accountType) {
        this.idAccount = idAccount;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public Account(String idAccount, String firstName, String lastName, String email,
                   String password, String phoneNumber, String domain, String accountType) {
        this.idAccount= idAccount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.domain = domain;
        this.accountType = accountType;
    }

    public void editAccount(Account updatedAccount) {
        this.setFirstName(updatedAccount.firstName);
        this.setLastName(updatedAccount.lastName);
        this.setEmail(updatedAccount.email);
        this.setPassword(updatedAccount.password);
        this.setPhoneNumber(updatedAccount.phoneNumber);
        this.setDomain(updatedAccount.domain);
        this.setAccountType(updatedAccount.accountType);

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccountType() {
        return accountType;
    }
    @JsonIgnore
    public boolean isAdmin(){

        return  accountType.equals("admin");
    }
    @JsonIgnore
    public boolean isAuditor(){
        return  accountType.equals("auditor");
    }


    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }



    @Override
    public String toString() {
        return "Compte{" +
                "idAccount='" + idAccount + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", domain='" + domain + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }

    @JsonIgnore
    public String getString() {
        return
                ", idAccount='" + idAccount + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", domain='" + domain + '\'' +
                ", accountType='" + accountType + '\''
                ;
    }
}
