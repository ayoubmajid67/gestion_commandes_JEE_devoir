package controller.uiControllers.adminDashboard.Tabs;


import model.Compte;
import utils.SaveUtil;
import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IFormDialogEventHandler;
import utils.interfaces.objectConverter.CompteConverter;
import view.pages.AdminDashboard.AccountsManagementTab;
import view.pages.UserDashboard.ButtonEditor;
import view.pages.UserDashboard.FormDialog;

import javax.swing.*;
import java.sql.SQLException;

import static utils.ControllersGetter.accountsRepo;

public class AccountsManagementTabController {
    private AccountsManagementTab view;
    private FormDialog createAccountForm;
    private FormDialog editAccountForm;
    private final String[] columnNames = AccountsManagementTab.getColumnNamesCreateEdit();
    private final SaveUtil<Compte> saveUtil = new SaveUtil(new CompteConverter());


    public AccountsManagementTabController(AccountsManagementTab view) {
        this.view = view;
        initController();

    }    private final IButtonEditorEventsHandler iButtonEditorEventsHandler = new IButtonEditorEventsHandler() {


        @Override
        public void editObjectEventHandler(ButtonEditor view) {
            String[] columnNames = AccountsManagementTab.getColumnNamesCreateEdit();


            editAccountForm = new FormDialog(" Edit", columnNames, view.getRowData(), SaveEditAccountIFormEventHandler, view.getId());
        }

        @Override
        public void deleteObjectEventHandler(ButtonEditor ButtonEditorView) {
            // Show a confirmation dialog
            int response = JOptionPane.showConfirmDialog(
                    null, // No parent component
                    "Are you sure you want to delete this Account?", // Message
                    "Confirm Delete", // Dialog title
                    JOptionPane.YES_NO_OPTION // Option type (Yes/No)
            );

            // Check the user's response
            if (response == JOptionPane.YES_OPTION) {
                try {

                    accountsRepo.deleteAccount(ButtonEditorView.getId());
                    view.refreshTable();
                    System.out.println("Deleting account ");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error while deleting Account " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                // User clicked "No" or closed the dialog, do nothing
                System.out.println("Deleting operation canceled.");
            }
        }
    };

    public IButtonEditorEventsHandler getIButtonEditorEventsHandler() {
        return iButtonEditorEventsHandler;
    }    private final IFormDialogEventHandler SaveEditAccountIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {

                Compte account = saveUtil.saveFormData(formDialog.getFormData()); // Save form data

                System.out.println(account);
                accountsRepo.updateAccount(formDialog.getId(), account);


                // Show success message
                JOptionPane.showMessageDialog(
                        formDialog,
                        "The Account was updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Refresh the table to show the new data
                view.refreshTable();
                formDialog.dispose(); // Close the form dialog
            } else {
                JOptionPane.showMessageDialog(
                        formDialog,
                        "Please fill in all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    editAccountForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private void initController() {
        addCreatAccountButtonEvent();
    }

    private void addCreatAccountButtonEvent() {
        view.getCreateButton().addActionListener(ActionEvent -> {
            createAccountForm = new FormDialog("Create Account", columnNames, saveCreateAccountIFormEventHandler);

        });
    }

    private final IFormDialogEventHandler saveCreateAccountIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                Compte account = saveUtil.saveFormData(formDialog.getFormData());

                accountsRepo.addAccount(account);

                // Show success message
                JOptionPane.showMessageDialog(
                        createAccountForm,
                        "New Account added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                view.refreshTable();

                formDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        createAccountForm,
                        "Please fill in all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    createAccountForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };




}
