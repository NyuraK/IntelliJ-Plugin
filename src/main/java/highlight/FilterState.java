package highlight;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import org.jetbrains.annotations.NotNull;
import plugin.Configuration;
import stuff.ExpressionItem;

import java.util.ArrayList;
import java.util.List;

public class FilterState {
    private int offset;
    protected ConsoleViewContentType consoleViewContentType;
    protected List<Filter.ResultItem> resultItemList;
    private boolean exclude;
    private boolean matchesSomething;
    private String text;
    private final Configuration configuration;
    private CharSequence charSequence;
    private boolean textChanged;

    public FilterState(int offset, String text, Configuration configuration, CharSequence charSequence) {
        this.offset = offset;
        this.text = text;
        this.configuration = configuration;
        this.charSequence = charSequence;
    }

    @NotNull
    public CharSequence getCharSequence() {
        return charSequence;
    }


    public void setConsoleViewContentType(ConsoleViewContentType consoleViewContentType) {
        this.consoleViewContentType = consoleViewContentType;
    }

    public ConsoleViewContentType getConsoleViewContentType() {
        return consoleViewContentType;
    }

    public TextAttributes getTextAttributes() {
        if (consoleViewContentType == null) {
            return null;
        }
        return consoleViewContentType.getAttributes();
    }

    public int getOffset() {
        return offset;
    }

    public List<Filter.ResultItem> getResultItemList() {
        return resultItemList;
    }

    public boolean add(Filter.ResultItem resultItem) {
        if (resultItemList == null) {
            resultItemList = new ArrayList<>();
        }
        return resultItemList.add(resultItem);
    }
    public void executeAction(ExpressionItem expressionItem) {

        if (expressionItem.shallHide()) {
            setExclude(true);
        }

        setMatchesSomething(true);

    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    public void setMatchesSomething(boolean matchesSomething) {
        this.matchesSomething = matchesSomething;
    }

    public boolean isMatchesSomething() {
        return matchesSomething;
    }

}
