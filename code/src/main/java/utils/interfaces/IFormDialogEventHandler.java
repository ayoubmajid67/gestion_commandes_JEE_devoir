package utils.interfaces;


import view.pages.UserDashboard.FormDialog;

import java.awt.*;

@FunctionalInterface
public interface IFormDialogEventHandler {
    void save(FormDialog formDialog);


    default void cancel(Dialog dialog){
        System.out.println("The Form dialog closed with success");
         dialog.dispose();
    }

}

