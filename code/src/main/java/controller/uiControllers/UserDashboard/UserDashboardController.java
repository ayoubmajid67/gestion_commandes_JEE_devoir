package controller.uiControllers.UserDashboard;

import controller.businessControllers.account.AccountSessionHandler;
import view.pages.UserDashboard.UserDashboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDashboardController {

    UserDashboard view;
    public UserDashboardController(UserDashboard view) {
        this.view = view;
        addAuditorDashboardEvents();
    }
    public void addAuditorDashboardEvents(){

        addLogoutEvent();

    }
    public  void addLogoutEvent(){
        this.view.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle

                AccountSessionHandler.ClearCurrentAccountSession();

            }
        });
    }




}
