package filters;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.impl.EditorHyperlinkSupport;
import com.intellij.execution.testframework.ui.BaseTestsOutputConsoleView;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import plugin.MyConfiguration;

public class Rehighlighter {

    public void resetHighlights(ConsoleView console) {
        if (console instanceof ConsoleViewImpl) {
            reset((ConsoleViewImpl) console);
        } else if (console instanceof BaseTestsOutputConsoleView) {
            BaseTestsOutputConsoleView view = (BaseTestsOutputConsoleView) console;
            resetHighlights(view.getConsole());
        }
    }

    public void removeAllHighlighters(ConsoleView console) {
        Editor editor = ((ConsoleViewImpl) console).getEditor();
        if (editor != null) {
            editor.getMarkupModel().removeAllHighlighters();
        }
    }

    private void reset(ConsoleViewImpl consoleViewImpl) {
        removeAllHighlighters(consoleViewImpl);
        consoleViewImpl.rehighlightHyperlinksAndFoldings();
        consoleViewImpl.revalidate();
    }

    public void doJobOnDelete(ConsoleView console) {
        Project project = ((ConsoleViewImpl) console).getProject();
        Editor editor = ((ConsoleViewImpl) console).getEditor();
        removeAllHighlighters(console);
        EditorHyperlinkSupport myHyperlinks = new EditorHyperlinkSupport(editor, project);
        HighlightFilter filter = MyConfiguration.getInstance().createHighlightFilter(project);

        int lineCount = editor.getDocument().getLineCount();
        if (lineCount > 0)
            myHyperlinks.highlightHyperlinks(filter, 0, lineCount-1);
    }
}
