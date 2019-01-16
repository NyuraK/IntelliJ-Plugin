package actions;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.ColorPicker;
import filters.Rehighlighter;
import plugin.MyConfiguration;
import stuff.Operation;
import stuff.Utils;

import javax.swing.*;
import java.awt.*;

public class AddHighlightAction extends DumbAwareAction {
    public static final Icon ICON = IconLoader.getIcon("/paint-brush.png");

    public AddHighlightAction() {
        super("Highlight", null, ICON);

    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        final ConsoleView consoleView = e.getData(LangDataKeys.CONSOLE_VIEW);
        if (consoleView != null) {
            try {
                String string = Utils.getString(e);
                if (string == null)
                    return;

                Color color = ColorPicker.showDialog(rootComponent(getEventProject(e)), "Background color", Color.white, true, null, true);
                if (color == null) {
                    return;
                }
                addToConsole(consoleView, string, color);

            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }

    }

    private void addToConsole(ConsoleView console, String expression, Color color) {
        MyConfiguration.getInstance().addToPanel(expression, color, Operation.ADD);
        new Rehighlighter().rehighlight(console);
    }

    @Override
    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        final boolean enabled = e.getData(LangDataKeys.CONSOLE_VIEW) != null;
        boolean selectedText = Utils.isSelectedText(e);
        presentation.setEnabled(selectedText && enabled);
        presentation.setVisible(selectedText && enabled);
    }

    private static JComponent rootComponent(Project project) {
        if (project != null) {
            IdeFrame frame = WindowManager.getInstance().getIdeFrame(project);
            if (frame != null)
                return frame.getComponent();
        }

        JFrame frame = WindowManager.getInstance().findVisibleFrame();
        return frame != null ? frame.getRootPane() : null;
    }
}
