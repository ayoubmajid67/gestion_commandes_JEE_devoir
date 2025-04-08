package controller.uiControllers;

import controller.businessControllers.account.AccountSessionHandler;
import model.Accounts.Account;
import model.Compte;
import utils.PageSwitcher;
import utils.PagesGetter;
import view.pages.LoginPage;


import javax.swing.*;
import utils.ControllersGetter;

import java.sql.SQLException;
import java.util.Optional;

public class LoginPageController {
    private final LoginPage view;

    public LoginPageController(LoginPage loginPage) {
        this.view = loginPage;
        initLoginPageEvents();
    }

    private void initLoginPageEvents() {
        view.getLoginButton().addActionListener(e -> handleLoginEvent(
                view.getEmailField().getText(),
                new String(view.getPasswordField().getPassword())
        ));
    }

    public void handleLoginEvent(String email, String password) {

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Email and Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


  try {
      Optional<Compte>  accountOpt = ControllersGetter.accountsRepo.getCompte(email,password);


      Compte account = null;
      if (accountOpt.isPresent()) {
          account = accountOpt.get();
      }

      if (account != null) {
          AccountSessionHandler.UpdateCurrentAccountSession(account.getId(),account.getCompteType());
          if(account.isAdmin())
              PageSwitcher.switchPage("adminDashboard");
          PagesGetter.AdminDashBoardPage.reloadTaps();

          if(account.isUser()){
              PageSwitcher.switchPage("userDashboard");
              PagesGetter.userDashboardPage.reloadTabs();




          }

      } else {
          JOptionPane.showMessageDialog(view, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
      }
  }catch (SQLException e){
      JOptionPane.showMessageDialog(view, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }


    }
}

