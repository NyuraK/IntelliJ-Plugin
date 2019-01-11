package actions;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.RunnerLayoutUi;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugin.MyConfigurable;
import stuff.Kit;
import ui.ExpressionWindow;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;

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
//        ConsoleViewImpl consoleView = (ConsoleViewImpl) e.getData(LangDataKeys.CONSOLE_VIEW);
//        String expression = getExpression(e);
//
//        ConsoleViewImpl newConsole = (ConsoleViewImpl) createConsole(project, consoleView, new MyProcessHandler());
//        ExecutionResult
//        final ExpressionItem quickFilterPanel = new ExpressionItem(consoleView, newConsole, expression, runnerLayoutUi);
//        DefaultActionGroup actions = new DefaultActionGroup();
//        final MyJPanel consolePanel = createConsolePanel(runnerLayoutUi, newConsole, actions, );
        MyConfigurable instance = new MyConfigurable(project, consoleView);
        instance.prepareForm();
        ShowSettingsUtil.getInstance().editConfigurable(project, "GrepConsoleSettings", instance, true);
    }

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
        String s = Kit.getSelectedString(e);
        if (s == null)
            s = "";
        if (s.endsWith("\n")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
