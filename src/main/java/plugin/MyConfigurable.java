package plugin;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Transient;
import highlight.GrepRe;
import highlight.HighlightFilter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stuff.ExpressionItem;
import ui.MyForm;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@State(
        name="Highlighting console",
        storages = {
                @Storage("/HighlightingConsole.xml")}
)
public class MyConfigurable implements ApplicationComponent, Configurable, PersistentStateComponent<MyConfigurable> {
    private static final String MAX_PROCESSING_TIME_DEFAULT = "1000";
    public static final int maxLengthToMatch = 120;
    private List<ExpressionItem> expressionItems = new ArrayList<>();

    @Transient
    public MyForm form;
    @Transient
    private Project project;
    @Transient
    private ConsoleView console;
    @Transient
    private Operation operation = Operation.NONE;

    public MyConfigurable() {

    }

    public static MyConfigurable getInstance() {
        return ApplicationManager.getApplication().getComponent(MyConfigurable.class);
    }

    public HighlightFilter createHighlightFilter(Project project) {
        this.project = project;
        HighlightFilter highlightFilter = new HighlightFilter(project);
        return highlightFilter;
    }

    public List<ExpressionItem> getExpressionItems() {
        return expressionItems;
    }

    //TODO maybe here might be another better logic (i.e. in his 'Profile' he resets list)
    public boolean setExpressionItems(ExpressionItem item) {
        if (!expressionItems.contains(item)) {
            if (isTherePlain(item)) deletePlain(item);
            expressionItems.add(item);
            return true;
        }
        return false;
    }

    private void deletePlain(ExpressionItem delete) {
        for (Iterator<ExpressionItem> i = expressionItems.iterator(); i.hasNext();) {
            ExpressionItem item = i.next();
            if (item.getExpression().equals(delete.getExpression())) {
                i.remove();
                return;
            }
        }
    }

    private boolean isTherePlain(ExpressionItem item) {
        for (ExpressionItem i : expressionItems) {
            if (item.getExpression().equals(i.getExpression()))
                return true;
        }
        return false;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Highlighting console";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (form == null) {
            form = new MyForm();
        }
        return form.getRootComponent();
    }

    @Override
    public boolean isModified() {
        return form.isChanged();
    }

    @Override
    public void apply() throws ConfigurationException {
        if (form != null) {
            createHighlightFilterIfMissing(console);
            new GrepRe().resetHighlights(console);
//            if (operation == Operation.ADD) {
//                createHighlightFilterIfMissing(console);
//                new Rehighlighter().resetHighlights(console);
//            }
//            else if (operation == Operation.DELETE) {
////                new Rehighlighter().resetHighlights(console);
//                createHighlightFilterIfMissing(console);
//                for (int i = 0; i < 5; i++)
//                    new Rehighlighter().resetHighlights(console);
//            }
//            else {
//                createHighlightFilterIfMissing(console);
//                new Rehighlighter().resetHighlights(console);
//            }
//            operation = Operation.NONE;
        }
    }

    @NotNull
    public String limitInputLength_andCutNewLine(@NotNull String text) {
        int endIndex = text.length();
        if (text.endsWith("\n")) {
            --endIndex;
        }
        endIndex = Math.min(endIndex, maxLengthToMatch);
        return text.substring(0, endIndex);
    }

    @NotNull
    public CharSequence limitProcessingTime(String substring) {
        return StringUtil.newBombedCharSequence(substring, Integer.valueOf(MAX_PROCESSING_TIME_DEFAULT));
    }

    public void setConsole(ConsoleView consoleView) {
        this.console = consoleView;
    }

    public void createHighlightFilterIfMissing(@NotNull ConsoleView console) {
        if (console instanceof ConsoleViewImpl) {
            HighlightFilter highlightFilter = createHighlightFilter(((ConsoleViewImpl) console).getProject());
            console.addMessageFilter(highlightFilter);
        }
    }

    @Nullable
    @Override
    public MyConfigurable getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MyConfigurable state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public void deleteItem(ExpressionItem delete) {

        for (Iterator<ExpressionItem> i = expressionItems.iterator(); i.hasNext();) {
            ExpressionItem item = i.next();
            if (item.equals(delete)) {
                i.remove();
            }
        }

    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
