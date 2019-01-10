package plugin;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import ui.ExpressionWindow;

import javax.swing.*;

public class MyConfigurable implements Configurable {

    private ExpressionWindow form;
    private Project project;
    @Nullable
    private ConsoleView console;

    public MyConfigurable(Project project, ConsoleView consoleView) {
        this.project = project;
        this.console = consoleView;
    }

    public void prepareForm() {
        form = new ExpressionWindow(this);
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
            form = new ExpressionWindow(this);
        }
        return form.getRootComponent();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}
