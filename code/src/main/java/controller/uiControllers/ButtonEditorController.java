package controller.uiControllers;

import utils.interfaces.IButtonEditorEventsHandler;
import view.pages.AdminDashboard.AccountsManagementTab;
import view.pages.UserDashboard.ButtonEditor;

public class ButtonEditorController {
    private final ButtonEditor view;

    private final IButtonEditorEventsHandler iButtonEditorEditEventHandler;

    public ButtonEditorController(ButtonEditor view, IButtonEditorEventsHandler iButtonEditorEventsHandler) {
        this.view = view;
        this.iButtonEditorEditEventHandler = iButtonEditorEventsHandler;

        controllers();
    }

    public void controllers() {
        addEditUserButtonEvent();
        addDeleteUserButtonEvent();
    }

    private void addEditUserButtonEvent() {

        view.getEditButton().addActionListener(ActionEvent -> {
            String[] columnNames = AccountsManagementTab.getColumnNamesCreateEdit();


            iButtonEditorEditEventHandler.editObjectEventHandler(view);

        });
    }

    private void addDeleteUserButtonEvent() {


        view.getDeleteButton().addActionListener(ActionEvent -> {
            String[] columnNames = AccountsManagementTab.getColumnNamesCreateEdit();

            iButtonEditorEditEventHandler.deleteObjectEventHandler(view);

        });
    }
}
