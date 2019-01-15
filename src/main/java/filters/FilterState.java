package filters;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import org.jetbrains.annotations.NotNull;
import stuff.ExpressionItem;
import stuff.Operation;

import java.util.ArrayList;
import java.util.List;

public class FilterState {
    private int offset;
    protected ConsoleViewContentType consoleViewContentType;
    protected List<Filter.ResultItem> resultItemList;
    private boolean matchesSomething;
    private CharSequence charSequence;
    private boolean isExclude;

    public FilterState(int offset, CharSequence charSequence) {
        this.offset = offset;
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

    public boolean isExclude() {
        return isExclude;
    }

    private void setExclude(boolean exclude) {
        isExclude = exclude;
    }

    public void executeAction(ExpressionItem expressionItem) {
        Operation action = expressionItem.getOperation();
        if (Operation.DELETE.equals(action)) {
            setExclude(true);

        }
        setMatchesSomething(true);
    }

    public boolean notTerminatedWithNewline() {
        if (charSequence.length() == 0) {
            return false;
        }
        return charSequence.charAt(charSequence.length() - 1) != '\n';
    }
}
