package model.Accounts;

public class Admin extends Account {


    public Admin(String firstName, String lastName, String email, String password, String phoneNumber, String domain, String accountType) {
        super(firstName, lastName, email, password, phoneNumber, domain, accountType);

    }

    @Override
    public String toString() {
        return "Admin{" +
                super.getString() +
                '}';
    }
}
