package highlight;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import org.jetbrains.annotations.NotNull;
import plugin.MyConfigurable;

import java.util.ArrayList;
import java.util.List;

public class FilterState {
    private int offset;
    protected ConsoleViewContentType consoleViewContentType;
    protected List<Filter.ResultItem> resultItemList;
    private boolean matchesSomething;
    private final MyConfigurable configuration;
    private CharSequence charSequence;

    public FilterState(int offset, MyConfigurable configuration, CharSequence charSequence) {
        this.offset = offset;
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


    public void setMatchesSomething(boolean matchesSomething) {
        this.matchesSomething = matchesSomething;
    }

    public boolean isMatchesSomething() {
        return matchesSomething;
    }

}
