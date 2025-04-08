// AdminCommandsManagementTabController.java
package controller.uiControllers.adminDashboard.Tabs;

import model.Commande;
import utils.SaveUtil;
import utils.interfaces.objectConverter.CommandeConverter;
import view.pages.AdminDashboard.AdminCommandsManagementTab;

public class AdminCommandsManagementTabController {
    private final AdminCommandsManagementTab view;
    private final String[] columnNames = AdminCommandsManagementTab.getColumnNamesViewOnly();
    private final SaveUtil<Commande> saveUtil = new SaveUtil(new CommandeConverter());

    public AdminCommandsManagementTabController(AdminCommandsManagementTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // No action listeners needed for view-only mode
    }
}