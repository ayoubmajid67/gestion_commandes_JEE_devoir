package controller.uiControllers;

import utils.interfaces.IFormDialogEventHandler;
import view.pages.UserDashboard.FormDialog;

public class FormDialogController {
    FormDialog view;
    IFormDialogEventHandler formDialogEventHandler;

    public FormDialogController(FormDialog view, IFormDialogEventHandler formDialogEventHandler) {
        this.view = view;
        this.formDialogEventHandler = formDialogEventHandler;
        this.initController();
    }


    private void initController() {
        addSaveCreateButtonEvent();
        addCancelCreateButtonEvent();
    }

    private void addSaveCreateButtonEvent() {
        view.getSaveButton().addActionListener(ActionEvent -> {
            formDialogEventHandler.save(view);
        });
    }

    private void addCancelCreateButtonEvent() {
        view.getCancelButton().addActionListener(ActionEvent -> {
            formDialogEventHandler.cancel(view);
        });
    }


}
