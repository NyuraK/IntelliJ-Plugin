package highlight;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.impl.EditorHyperlinkSupport;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;
import plugin.MyConfigurable;
import stuff.Utils;

public class GrepRe {

    public void resetHighlights(ConsoleView console) {
        if (console instanceof ConsoleViewImpl) {
            reset((ConsoleViewImpl) console);
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
            consoleViewImpl.revalidate();
        }
    }

    private void highlightAll(ConsoleViewImpl consoleViewImpl, Editor editor) {
        try {
            Filter myCustomFilter = (Filter) Utils.getPropertyValue(consoleViewImpl, "myFilters");

            int lineCount = editor.getDocument().getLineCount();
            if (lineCount > 0) {
                consoleViewImpl.getHyperlinks().highlightHyperlinks(myCustomFilter, getPredefinedMessageFilter(), 0, lineCount - 1);
            }
        } catch (NoSuchFieldException e1) {
            throw new RuntimeException("IJ API was probably changed, update the plugin or report it", e1);
        }
    }


    public void highlight(Editor editor, Project project) {
        EditorHyperlinkSupport myHyperlinks = new EditorHyperlinkSupport(editor, project);
        int lineCount = editor.getDocument().getLineCount();
        if (lineCount > 0) {
            myHyperlinks.highlightHyperlinks(MyConfigurable.getInstance().createHighlightFilter(project), getPredefinedMessageFilter(), 0, lineCount - 1);
        }
    }


    private Filter getPredefinedMessageFilter() {
        return new Filter() {
            @Nullable
            @Override
            public Result applyFilter(String line, int entireLength) {
                return null;
            }
        };
    }
}
