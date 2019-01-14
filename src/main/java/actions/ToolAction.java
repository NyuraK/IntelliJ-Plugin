package actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import plugin.MyConfiguration;

public class ToolAction extends DumbAwareAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = getEventProject(e);
//        MyConfiguration.getInstance().prepareForm();
        ShowSettingsUtil.getInstance().editConfigurable(project, "ExpressionAddPanel", MyConfiguration.getInstance(), true);
    }
}
