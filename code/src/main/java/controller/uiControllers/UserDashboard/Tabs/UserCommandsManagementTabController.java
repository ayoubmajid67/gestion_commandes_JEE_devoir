package controller.uiControllers.UserDashboard.Tabs;

import model.Commande;
import repo.CommandeRepo;
import utils.ControllersGetter;
import utils.SaveUtil;
import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IFormDialogEventHandler;
import utils.interfaces.objectConverter.CommandeConverter;
import view.pages.UserDashboard.ButtonEditor;
import view.pages.UserDashboard.FormDialog;
import view.pages.UserDashboard.UserCommandsManagementTab;

import javax.swing.*;

import java.sql.SQLException;



public class UserCommandsManagementTabController {
    private UserCommandsManagementTab view;
    private FormDialog createCommandeForm;
    private FormDialog editCommandeForm;
    private String[] columnNames = UserCommandsManagementTab.getColumnNamesCreateEdit();
    private SaveUtil<Commande> saveUtil = new SaveUtil(new CommandeConverter());
    private String currentUserId; // Store current user ID from AccountToken

    public UserCommandsManagementTabController(UserCommandsManagementTab view) {
        this.view = view;
        this.currentUserId = ControllersGetter.currentAccountSession.getIdAccountToken();
        initController();
    }

    private void initController() {
        addCreatCommandeButtonEvent();
    }

    private boolean belongsToCurrentUser(String commandeId) {
        try {

            Commande commande = ControllersGetter.CommandeRepo.getCommandeById(commandeId).get();

            return commande != null && commande.getidCompte().equals(currentUserId);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void addCreatCommandeButtonEvent() {
        view.getCreateButton().addActionListener(ActionEvent -> {
            createCommandeForm = new FormDialog("Ajouter Commande", columnNames, saveCreateCommandeIFormEventHandler);
        });
    }

    private IFormDialogEventHandler saveEditCommandeIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {

                Commande commande = saveUtil.saveFormData(formDialog.getFormData());
                commande.setIdCompte(currentUserId);




                ControllersGetter.CommandeRepo.editCommande(formDialog.getId(), commande);
                view.refreshTable();

                JOptionPane.showMessageDialog(
                        editCommandeForm,
                        "Order updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                formDialog.dispose();
            } else if (!belongsToCurrentUser(formDialog.getId())) {
                JOptionPane.showMessageDialog(
                        formDialog,
                        "You can only edit your own orders",
                        "Access Denied",
                        JOptionPane.WARNING_MESSAGE
                );
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
                    editCommandeForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IFormDialogEventHandler saveCreateCommandeIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                System.out.println("data" + formDialog.getFormData());
                Commande commande = saveUtil.saveFormData(formDialog.getFormData());
                commande.setIdCompte(currentUserId); // Set to current user's idAccountToken
                ControllersGetter.CommandeRepo.createCommande(commande);

                JOptionPane.showMessageDialog(
                        formDialog,
                        "New order added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                view.refreshTable();
                formDialog.dispose();
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
                    formDialog,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IButtonEditorEventsHandler iButtonEditorEventsHandler = new IButtonEditorEventsHandler() {
        @Override
        public void editObjectEventHandler(ButtonEditor view) {
            if (belongsToCurrentUser(view.getId())) {
                editCommandeForm = new FormDialog(
                        "Edit",
                        columnNames,
                        view.getRowData(),
                        saveEditCommandeIFormEventHandler,
                        view.getId()
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "You can only edit your own orders",
                        "Access Denied",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }

        @Override
        public void deleteObjectEventHandler(ButtonEditor buttonEditorView) {
            if (belongsToCurrentUser(buttonEditorView.getId())) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this Order?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    try {
                        ControllersGetter.CommandeRepo.deleteCommande(buttonEditorView.getId());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    view.refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "You can only delete your own orders",
                        "Access Denied",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    };

    public IButtonEditorEventsHandler getIButtonEditorEventsHandler() {
        return iButtonEditorEventsHandler;
    }

    public IFormDialogEventHandler getSaveCreateCommandeIFormEventHandler() {
        return saveCreateCommandeIFormEventHandler;
    }
}