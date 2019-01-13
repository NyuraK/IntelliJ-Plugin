package actions;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.util.IconLoader;
import highlight.Rehighlighter;

import javax.swing.*;

public class ClearFromHighlights extends DumbAwareAction {
    public static final Icon ICON = IconLoader.getIcon("/rubbish-bin.png");
    private ConsoleView consoleView;

    public ClearFromHighlights(ConsoleView consoleView) {
        super("Clear from highlights", null, ICON);
        this.consoleView = consoleView;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        new Rehighlighter().removeAllHighlighters(consoleView);
//        MyConfigurable.getInstance().setConsole(consoleView);
    }
}
