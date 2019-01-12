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
        MyConfigurable.getInstance().prepareForm();
        MyConfigurable.getInstance().setConsole(consoleView);
        ShowSettingsUtil.getInstance().editConfigurable(project, "ExpressionAddPanel", MyConfigurable.getInstance(), true);
    }


//    @Override
//    public void update(AnActionEvent e) {
//        Presentation presentation = e.getPresentation();
//        boolean enabled = false;
//
//        Project eventProject = getEventProject(e);
//        ConsoleViewImpl parentConsoleView = (ConsoleViewImpl) getConsoleView(e);
//        if (parentConsoleView != null) {
//            GrepCopyingFilter copyingFilter = ServiceManager.getInstance().getCopyingFilter(parentConsoleView);
//            if (eventProject != null && copyingFilter != null) {
//                RunContentDescriptor runContentDescriptor = OpenGrepConsoleAction.getRunContentDescriptor(eventProject, parentConsoleView);
//                if (runContentDescriptor != null) {
//                    RunnerLayoutUi runnerLayoutUi = getRunnerLayoutUi(eventProject, runContentDescriptor, parentConsoleView);
//                    enabled = runnerLayoutUi != null;
//                }
//            }
//        }
//
//        presentation.setEnabled(enabled);
//
//    }

//    private ConsoleView createConsole(@NotNull Project project, ConsoleViewImpl consoleView, MyProcessHandler processHandler) {
//        ConsoleView console = new ConsoleViewImpl(project, false);
//        console.attachToProcess(processHandler);
//        return console;
//    }

//    private static MyJPanel createConsolePanel(RunnerLayoutUi runnerLayoutUi, ConsoleView view, ActionGroup actions,
//                                               ExpressionWindow comp) {
//        MyJPanel panel = new MyJPanel(runnerLayoutUi);
//        panel.setLayout(new BorderLayout());
//        panel.add(comp.getRootComponent(), BorderLayout.NORTH);
//        panel.add(view.getComponent(), BorderLayout.CENTER);
//        ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.UNKNOWN, actions,
//                false);
//        panel.add(actionToolbar.getComponent(), BorderLayout.WEST);
//        return panel;
//    }
//
//    static class MyJPanel extends JPanel implements Disposable {
//        private RunnerLayoutUi runnerLayoutUi;
//
//        public MyJPanel(RunnerLayoutUi runnerLayoutUi) {
//            this.runnerLayoutUi = runnerLayoutUi;
//        }
//
//        @Override
//        public void dispose() {
//
//            removeAll();
//        }
//    }
//
//    private class MyProcessHandler extends ProcessHandler {
//        @Override
//        protected void destroyProcessImpl() {
//
//        }
//
//        @Override
//        protected void detachProcessImpl() {
//
//        }
//
//        @Override
//        public boolean detachIsDefault() {
//            return false;
//        }
//
//        @Nullable
//        @Override
//        public OutputStream getProcessInput() {
//            return null;
//        }
//    }

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
