package model.Accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountToken {

    private final String idAccountToken;
    private final String accountType;
    private final String token;

    AccountToken() {
        idAccountToken = "unknown";
        accountType = "unknown";
        token = "unknown";
    }

    public AccountToken(String idAccountToken, String accountType, String Token) {
        this.idAccountToken = idAccountToken;
        this.accountType = accountType;
        this.token = Token;
    }

    public String getIdAccountToken() {
        return idAccountToken;
    }

    public String getToken() {
        return token;
    }


    public String getAccountType() {
        return accountType;
    }

    @JsonIgnore
    public boolean isAdmin() {

        return accountType.equals("admin");
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