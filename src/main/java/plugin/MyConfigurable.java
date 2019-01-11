package plugin;

import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import ui.ExpressionWindow;
import ui.MyForm;

import javax.swing.*;

public class MyConfigurable implements Configurable {

//    private ExpressionWindow form;
    private MyForm form;
    private RunConfigurationBase runConfigurationBase;
    private Project project;
    @Nullable
    private ConsoleView console;

    public MyConfigurable(Project project, ConsoleView consoleView) {
        this.project = project;
        this.console = consoleView;
    }

    public MyConfigurable(RunConfigurationBase runConfigurationBase) {
        this.runConfigurationBase = runConfigurationBase;
        project = runConfigurationBase.getProject();
    }

    public void prepareForm() {
//        form = new ExpressionWindow(this);
        form = new MyForm();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Highlghited console";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (form == null) {
//            form = new ExpressionWindow(this);
            form = new MyForm();
        }
        return form.getRootComponent();
    }

    @Override
    public boolean isModified() {
        return form != null;
    }

    @Override
    public void apply() throws ConfigurationException {
        System.out.println("Apply changes =>");
//        Profile selectedProfile = form.getSelectedProfile();
//        if (selectedProfile != null) {
//            PluginState formSettings = form.getSettings();
//            applicationComponent.loadState(getClone(formSettings));
//
//
//            long selectedProfileId = selectedProfile.getId();
//            RunConfigurationBase runConfigurationBase = this.runConfigurationBase;
//            if (runConfigurationBase == null && console != null) {
//                runConfigurationBase = ServiceManager.getInstance().getRunConfigurationBase(console);
//            }
//            if (runConfigurationBase != null) {
//                GrepConsoleData.getGrepConsoleData(runConfigurationBase).setSelectedProfileId(selectedProfileId);
//            }
//            if (selectedProfileId != originalSelectedProfileId) {
//                Profile profile = applicationComponent.getState().getProfile(selectedProfileId);
//                if (console != null) {
//                    serviceManager.profileChanged(console, profile);
//                }
//            }
//            form.setOriginallySelectedProfileId(selectedProfileId);
//
//            refreshServices(currentAction);
    }
}
