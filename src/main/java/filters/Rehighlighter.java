package filters;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.impl.EditorHyperlinkSupport;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import plugin.MyConfiguration;

public class Rehighlighter {

    public void removeAllHighlighters(ConsoleView console) {
        Editor editor = ((ConsoleViewImpl) console).getEditor();
        if (editor != null) {
            editor.getMarkupModel().removeAllHighlighters();
        }
    }

    public void rehighlight(ConsoleView console) {
        Project project = ((ConsoleViewImpl) console).getProject();
        Editor editor = ((ConsoleViewImpl) console).getEditor();
        removeAllHighlighters(console);
        EditorHyperlinkSupport myHyperlinks = new EditorHyperlinkSupport(editor, project);
        HighlightFilter filter = MyConfiguration.getInstance().createHighlightFilter();

        int lineCount = editor.getDocument().getLineCount();
        if (lineCount > 0)
            myHyperlinks.highlightHyperlinks(filter, 0, lineCount-1);
    }
}
