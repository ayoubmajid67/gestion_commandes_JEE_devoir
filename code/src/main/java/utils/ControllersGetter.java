package utils;




import controller.businessControllers.account.AccountSessionHandler;
import model.Accounts.AccountToken;
import repo.AccountsRepo;
import repo.CommandeRepo;
import repo.DbConnexion;

public class ControllersGetter {
     public static DbConnexion dbConnexion = new DbConnexion();
     public static AccountToken currentAccountSession=null;
     public static AccountSessionHandler accountSessionHandler = new AccountSessionHandler();
     public static AccountsRepo accountsRepo = new AccountsRepo();
     public static CommandeRepo CommandeRepo = new CommandeRepo();
}
