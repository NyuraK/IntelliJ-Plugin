package filters;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleViewContentType;
import org.jetbrains.annotations.NotNull;
import stuff.ExpressionItem;
import stuff.Operation;

import java.util.ArrayList;
import java.util.List;

public class FilterState {
    protected ConsoleViewContentType consoleViewContentType;
    protected List<Filter.ResultItem> resultItemList;
    private int offset;
    private boolean matchesSomething;
    private CharSequence charSequence;
    private boolean isExclude;
    private Operation nextOperation = Operation.CONTINUE_MATCHING;

    public FilterState(int offset, CharSequence charSequence) {
        this.offset = offset;
        this.charSequence = charSequence;
    }

    public Operation getNextOperation() {
        return nextOperation;
    }

    public void setNextOperation(Operation nextOperation) {
        this.nextOperation = nextOperation;
    }

    @NotNull
    public CharSequence getCharSequence() {
        return charSequence;
    }

    public ConsoleViewContentType getConsoleViewContentType() {
        return consoleViewContentType;
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

    public boolean isListEmpty() {
        return resultItemList == null;
    }

    public boolean isMatchesSomething() {
        return matchesSomething;
    }

    public void setMatchesSomething(boolean matchesSomething) {
        this.matchesSomething = matchesSomething;
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
        setNextOperation(Operation.EXIT);
        setMatchesSomething(true);
    }

    public boolean notTerminatedWithNewline() {
        if (charSequence.length() == 0) {
            return false;
        }
        return charSequence.charAt(charSequence.length() - 1) != '\n';
    }
}
