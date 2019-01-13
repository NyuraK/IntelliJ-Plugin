package plugin;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.ide.ui.AppearanceConfigurable;
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
import highlight.HighlightFilter;
import highlight.Rehighlighter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stuff.ExpressionItem;
import ui.MyForm;
import ui.UIExprItem;

import javax.swing.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
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

//    @Transient
    private MyForm form;
    private Project project;
    private ConsoleView console;
    private List<WeakReference<HighlightFilter>> highlightFilters = new ArrayList<>();

    public MyConfigurable() {

    }

    public static MyConfigurable getInstance() {
        return ApplicationManager.getApplication().getComponent(MyConfigurable.class);
    }

    public HighlightFilter createHighlightFilter(Project project) {
        this.project = project;
        HighlightFilter highlightFilter = new HighlightFilter(project, getState());
        highlightFilters.add(new WeakReference<>(highlightFilter));
        return highlightFilter;
    }


    public void prepareForm() {
//        form = new MyForm(this);
        createComponent();
    }

    public List<ExpressionItem> getExpressionItems() {
        return expressionItems;
    }

    //TODO maybe here might be another better logic (i.e. in his 'Profile' he resets list)
    public void setExpressionItems(ExpressionItem item) {
        this.expressionItems.add(item);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Highlighting console";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        System.out.println("We're in Configurable createComponent()");
        if (form == null) {
            form = new MyForm(this);
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
            createHighlightFilterIfMissing(console);
            new Rehighlighter().resetHighlights(console);
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
}
