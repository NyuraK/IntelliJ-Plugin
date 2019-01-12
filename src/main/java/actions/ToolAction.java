package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import plugin.MyConfigurable;

import javax.swing.*;

public class ToolAction extends DumbAwareAction {
    public static final Icon ICON = IconLoader.getIcon("/network.png");

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = getEventProject(e);
        MyConfigurable.getInstance().prepareForm();
        ShowSettingsUtil.getInstance().editConfigurable(project, "ExpressionAddPanel", MyConfigurable.getInstance(), true);
    }
}
