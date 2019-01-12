package plugin;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Transient;
import highlight.HighlightFilter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stuff.ExpressionItem;
import ui.MyForm;

import javax.swing.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@State(name = "MyConsole", storages = { @Storage(value = "MyConsole.xml") })
public class MyConfigurable implements ApplicationComponent, Configurable, PersistentStateComponent<MyConfigurable>, ProjectComponent {
    private static final String MAX_PROCESSING_TIME_DEFAULT = "1000";

    @Transient
    private MyForm form;
    private Project project;
    private List<ExpressionItem> expressionItems = new ArrayList<>();

    private List<WeakReference<HighlightFilter>> highlightFilters = new ArrayList<>();

    public static MyConfigurable getInstance() {
        return ApplicationManager.getApplication().getComponent(MyConfigurable.class);
    }

    public Filter createHighlightFilter(Project project) {
        this.project = project;
        HighlightFilter highlightFilter = new HighlightFilter(project, getState());
        highlightFilters.add(new WeakReference<>(highlightFilter));
//        System.out.println("we're in createHighlightFilter" + highlightFilter.getExpressionItem().toString());
        return highlightFilter;
    }


    public void prepareForm() {
        form = new MyForm(this);
    }

    public List<ExpressionItem> getExpressionItems() {
        return expressionItems;
    }

    //TODO maybe here might be another better logic (i.e. in his 'Profile' he resets list)
    public void addExpressionItem(ExpressionItem item) {
        System.out.println("We're adding new item to config " + item.toString());
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
        return "Highlighted console";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (form == null) {
            form = new MyForm(this);
        }
        return form.getRootComponent();
    }

    @Override
    public boolean isModified() {
        if (form.isChanged()) {
//            new HighlightFilter(project, this);
            return true;
        }
        return false;
    }

    //TODO здесь кидает ошибку, мол Project is null
    @Override
    public void apply() throws ConfigurationException {
        System.out.println("Apply changes =>");
//        final String text = form.textField1.getText();
        //без этого и не будет работать
        new ConsoleViewImpl(project, false);
        loadState(this);
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

    @NotNull
    public String limitInputLength_andCutNewLine(@NotNull String text) {
        int endIndex = text.length();
        if (text.endsWith("\n")) {
            --endIndex;
        }
//        if (this.isEnableMaxLengthLimit()) {
//            endIndex = Math.min(endIndex, this.getMaxLengthToMatchAsInt());
//        }
        return text.substring(0, endIndex);
    }

    @NotNull
    public CharSequence limitProcessingTime(String substring) {
        return StringUtil.newBombedCharSequence(substring, Integer.valueOf(MAX_PROCESSING_TIME_DEFAULT));
    }

}
