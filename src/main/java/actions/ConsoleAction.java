package actions;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import plugin.MyConfiguration;

import javax.swing.*;

public class ConsoleAction extends DumbAwareAction {
    public static final Icon ICON = IconLoader.getIcon("/paint-brush.png");
    private ConsoleView consoleView;

    public ConsoleAction(ConsoleView console) {
        super("Open 'Expression Add' Console", null, ICON);
        this.consoleView = console;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = getEventProject(e);
        MyConfiguration.getInstance().setConsole(consoleView);
        ShowSettingsUtil.getInstance().editConfigurable(project, "ExpressionAddPanel", MyConfiguration.getInstance(), true);
    }

}
