package model.Accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountToken {

    private final String idAccountToken;
    private final String accountType;
    private final String token;

    AccountToken(){
       idAccountToken ="unknown";
       accountType="unknown";
        token="unknown";
    }
    public String getIdAccountToken() {
        return idAccountToken;
    }

    public AccountToken(String idAccountToken, String accountType, String Token) {
        this.idAccountToken = idAccountToken;
        this.accountType = accountType;
        this.token = Token;
    }

    public String getToken() {
        return token;
    }


    public String getAccountType() {
        return accountType;
    }
    @JsonIgnore
    public boolean isAdmin(){

        return  accountType.equals("admin");
    }
    @JsonIgnore // Exclude from JSON serialization
    public boolean isAuditor(){
        return  accountType.equals("auditor");
    }

    @Override
    public String toString() {
        return "AccountToken{" +
                "idAccount='" + idAccountToken + '\'' +
                ", accountType='" + accountType + '\'' +
                ", token='" + token + '\'' +
                '}';
    }




}