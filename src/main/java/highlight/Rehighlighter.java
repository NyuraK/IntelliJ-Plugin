package highlight;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.testframework.ui.BaseTestsOutputConsoleView;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.Nullable;
import stuff.Kit;

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


    public void removeAllHighlighters(Editor editor) {
        if (editor != null) {
            editor.getMarkupModel().removeAllHighlighters();
        }
    }

    private void reset(ConsoleViewImpl consoleViewImpl) {
        Editor editor = consoleViewImpl.getEditor();
        if (editor != null) {//disposed are null - may be bug
            removeAllHighlighters(editor);
            highlightAll(consoleViewImpl, editor);
        }
    }

    private void highlightAll(ConsoleViewImpl consoleViewImpl, Editor editor) {
        try {
            Filter myCustomFilter = (Filter) Kit.getPropertyValue(consoleViewImpl, "myFilters");

            int lineCount = editor.getDocument().getLineCount();
            if (lineCount > 0) {
                consoleViewImpl.getHyperlinks().highlightHyperlinks(myCustomFilter, FILTER, 0, lineCount - 1);
            }
        } catch (NoSuchFieldException e1) {
            throw new RuntimeException("IJ API was probably changed, update the plugin or report it", e1);
        }
    }

}
