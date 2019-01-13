package highlight;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.testframework.ui.BaseTestsOutputConsoleView;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.Nullable;

public class Rehighlighter {
    public static final Filter FILTER = new Filter() {
        @Nullable
        @Override
        public Result applyFilter(String s, int i) {
            return null;
        }
    };

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
        ((ConsoleViewImpl) console).revalidate();
    }

    private void reset(ConsoleViewImpl consoleViewImpl) {
        Editor editor = consoleViewImpl.getEditor();
        if (editor != null) {
            editor.getMarkupModel().removeAllHighlighters();
            consoleViewImpl.rehighlightHyperlinksAndFoldings();
            consoleViewImpl.revalidate();
        }
    }
}
