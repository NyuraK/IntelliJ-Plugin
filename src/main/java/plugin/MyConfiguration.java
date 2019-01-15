package plugin;

import com.intellij.execution.filters.InputFilter;
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
import filters.ExpressionInputFilter;
import filters.HighlightFilter;
import filters.Rehighlighter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stuff.ExpressionItem;
import stuff.Operation;
import ui.MyForm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@State(
        name="Highlighting console",
        storages = {
                @Storage("/HighlightingConsole.xml")}
)
public class MyConfiguration implements ApplicationComponent, Configurable, PersistentStateComponent<MyConfiguration> {
    private static final String MAX_PROCESSING_TIME_DEFAULT = "1000";
    public static final int maxLengthToMatch = 200;
    private List<ExpressionItem> expressionItems = new ArrayList<>();
    private boolean onUpdate = false;

    @Transient
    private MyForm form;

    @Transient
    private Project project;
    @Transient
    private ConsoleView console;
    @Transient
    private Operation operation = Operation.NONE;

    public MyConfiguration() {

    }

    public static MyConfiguration getInstance() {
        return ApplicationManager.getApplication().getComponent(MyConfiguration.class);
    }

    public HighlightFilter createHighlightFilter(Project project) {
        this.project = project;
        HighlightFilter highlightFilter = new HighlightFilter();
        return highlightFilter;
    }

    public InputFilter createInputFilter() {
        return new ExpressionInputFilter(console);
    }

    public List<ExpressionItem> getExpressionItems() {
        return expressionItems;
    }

    public MyForm getForm() {
        return form;
    }

    public void setExpressionItems(ExpressionItem item) {
        checkIfExists(item);
        this.expressionItems.add(item);
        if (onUpdate) update();
    }

    private void checkIfExists(ExpressionItem item) {
        String input = item.getExpression();
        ExpressionItem onDelete = item;
        for (ExpressionItem i: expressionItems) {
            final Pattern pattern = i.getPattern();
            final Matcher matcher = pattern.matcher(input);
            if (matcher.find() && i.getColor().equals(Color.white)) {
                System.out.println("A-ha! Matches");
                onDelete = i;
                break;
            }
        }
        if (item == onDelete){
            return;
        }
        onUpdate = true;
        deleteItem(onDelete);
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
        if (console != null) {
            if (operation == Operation.ADD) {
                createHighlightFilterIfMissing(console);
                new Rehighlighter().resetHighlights(console);
            }
            else if (operation == Operation.DELETE) {
                //TODO I still can't figure out why and how...
                for (int i=0; i<5; i++){
                    createHighlightFilterIfMissing(console);
                }
                new Rehighlighter().resetHighlights(console);

            }
            operation = Operation.NONE;

        }

    }

    public ConsoleView getConsole() {
        return console;
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
        if (this.console != consoleView) {
            System.out.println("A-ha, different consoles");
        }
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
    public MyConfiguration getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MyConfiguration state) {
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

    private void update() {
        for (ExpressionItem item: expressionItems)
            System.out.println(item);
        createHighlightFilterIfMissing(console);
        new Rehighlighter().resetHighlights(console);
        onUpdate = false;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void addToPanel(String expression, Color color, Operation operation) {
        if (form == null) return;
        form.addUIItem(expression, color, operation);
    }


}
