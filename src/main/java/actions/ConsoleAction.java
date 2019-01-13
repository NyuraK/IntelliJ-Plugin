package actions;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import plugin.MyConfigurable;
import stuff.Utils;

import javax.swing.*;

public class ConsoleAction extends DumbAwareAction {
    public static final Icon ICON = IconLoader.getIcon("/network.png");
    private ConsoleView consoleView;

    public ConsoleAction() {
    }

    public ConsoleAction(ConsoleView console) {
        super("Open 'Expression Add' Console", null, ICON);
        this.consoleView = console;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = getEventProject(e);
//        MyConfigurable.getInstance().prepareForm();
        MyConfigurable.getInstance().setConsole(consoleView);
        ShowSettingsUtil.getInstance().editConfigurable(project, "ExpressionAddPanel", MyConfigurable.getInstance(), true);
    }

    private String getExpression(AnActionEvent e) {
        String s = Utils.getSelectedString(e);
        if (s == null)
            s = "";
        if (s.endsWith("\n")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
