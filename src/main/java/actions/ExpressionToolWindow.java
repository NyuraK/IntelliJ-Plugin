package actions;

import com.intellij.execution.ExecutionHelper;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.testframework.sm.runner.ui.SMTestRunnerResultsForm;
import com.intellij.execution.testframework.ui.BaseTestsOutputConsoleView;
import com.intellij.execution.testframework.ui.TestResultsPanel;
import com.intellij.execution.ui.*;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.docking.DockManager;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerManager;
import org.jetbrains.annotations.NotNull;
import ui.ExpressionWindow;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Collection;

public class ExpressionToolWindow implements ToolWindowFactory, DumbAware {
    public static final String TOOL_WINDOW_ID = "Nice Viewer";
    public static final Key<ExpressionWindow> NICE_VIEW_KEY = Key.create("NICE_VIEWER_KEY");

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        SimpleToolWindowPanel panel = new SimpleToolWindowPanel(false, true);
        ConsoleViewImpl consoleView = new ConsoleViewImpl(project, true);
        RunContentManager manager = new RunContentManagerImpl(project, DockManager.getInstance(project));
        RunContentDescriptor runContentDescriptor = manager.getSelectedContent();
        Content content = runContentDescriptor.getAttachedContent();

        panel.setContent(consoleView.getComponent());

        RunnerLayoutUi runnerLayoutUi = getRunnerLayoutUi(project, runContentDescriptor, consoleView);
        ConsoleView console = new ConsoleViewImpl(project, true);
        Content my_content = ContentFactory.SERVICE.getInstance().createContent(panel, "", false);
//        Content my_content = toolWindow.getContentManager().getFactory().createContent(content.getComponent(), "My plugin output", false);
        toolWindow.getContentManager().addContent(content);
    }
    

    public static boolean isSameConsole(RunContentDescriptor dom, ExecutionConsole consoleView, boolean orChild) {
        ExecutionConsole executionConsole = dom.getExecutionConsole();
        if (executionConsole instanceof BaseTestsOutputConsoleView) {
            executionConsole = ((BaseTestsOutputConsoleView) executionConsole).getConsole();
        }
//        if (consoleView instanceof MyConsoleViewImpl && orChild) {
//            ConsoleViewImpl parentConsoleView = ((MyConsoleViewImpl) consoleView).getParentConsoleView();
//            return isSameConsole(dom, parentConsoleView, orChild);
//        }
        return executionConsole == consoleView;
    }

    public static boolean isSameConsole(Content dom, ExecutionConsole consoleView) {
        JComponent actionsContextComponent = dom.getActionsContextComponent();
        if (actionsContextComponent == consoleView) {
            return true;
        } else if (actionsContextComponent instanceof SMTestRunnerResultsForm) {
            SMTestRunnerResultsForm resultsForm = (SMTestRunnerResultsForm) actionsContextComponent;
            try {
                Field myConsole = TestResultsPanel.class.getDeclaredField("myConsole");
                myConsole.setAccessible(true);
                ConsoleView data = DataManager.getInstance().getDataContext((Component) myConsole.get(resultsForm)).getData(LangDataKeys.CONSOLE_VIEW);
                return data == consoleView;
            } catch (Throwable e) {

            }
        }
        return false;
    }

    private RunContentDescriptor getRunContentDescriptor(Project project, ConsoleViewImpl consoleView) {
        Collection<RunContentDescriptor> descriptors = ExecutionHelper.findRunningConsole(project,
                dom -> {
                    if (isSameConsole(dom, consoleView, true)) {
                        return true;
                    }
                    RunnerLayoutUi runnerLayoutUi = dom.getRunnerLayoutUi();
                    if (runnerLayoutUi != null) {
                        Content[] contents = runnerLayoutUi.getContents();
                        for (Content content : contents) {
                            if (isSameConsole(content, consoleView)) {
                                return true;
                            }
                        }
                    }
                    return false;
                });
        if (!descriptors.isEmpty()) {
            if (descriptors.size() == 1) {
                RunContentDescriptor runContentDescriptor = (RunContentDescriptor) descriptors.toArray()[0];
                if (runContentDescriptor != null) {
                    return runContentDescriptor;
                }
            }
        }

        return null;
    }

    public static RunnerLayoutUi getRunnerLayoutUi(Project eventProject, RunContentDescriptor runContentDescriptor, ConsoleViewImpl parentConsoleView) {
        RunnerLayoutUi runnerLayoutUi = null;

        if (runContentDescriptor != null) {
            runnerLayoutUi = runContentDescriptor.getRunnerLayoutUi();
        }

        if (runnerLayoutUi == null) {
            XDebugSession debugSession = XDebuggerManager.getInstance(eventProject).getDebugSession(
                    parentConsoleView);
            if (debugSession != null) {
                runnerLayoutUi = debugSession.getUI();
            }
            if (debugSession == null) {
                XDebugSession currentSession = XDebuggerManager.getInstance(eventProject).getCurrentSession();
                if (currentSession != null) {
                    runnerLayoutUi = currentSession.getUI();
                }
            }
        }

//        if (runnerLayoutUi == null) {
//            Container parent = parentConsoleView.getParent();
//            if (parent instanceof ConsoleAction.MyJPanel) {
//                runnerLayoutUi = ((ConsoleAction.MyJPanel) parent).runnerLayoutUi;
//            }
//        }
        return runnerLayoutUi;
    }

}
