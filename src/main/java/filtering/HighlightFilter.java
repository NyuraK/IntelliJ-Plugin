package filtering;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleViewContentType;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;
import plugin.MyConfiguration;
import stuff.ExpressionItem;
import stuff.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HighlightFilter implements Filter {

    private List<ExpressionProcessor> expressionProcessors;
    private ConsoleViewContentType lastTextAttributes = null;

    public HighlightFilter() {
        expressionProcessors = new ArrayList<>();
        for (ExpressionItem item : MyConfiguration.getInstance().getExpressionItems()) {
            if (!contains(item) && item.getOperation() == Operation.ADD) {
                expressionProcessors.add(new ExpressionProcessor(item));
            }
        }
    }

    private boolean contains(ExpressionItem onDelete) {
        for (Iterator<ExpressionProcessor> i = expressionProcessors.iterator(); i.hasNext(); ) {
            ExpressionProcessor item = i.next();
            if (item.getExpressionItem().equals(onDelete)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public Result applyFilter(String line, int entireLength) {
        int offset = entireLength;
        if (line != null)
            offset = entireLength-line.length();
        FilterState state = filter(line, offset);
        Result result = null;
        if (state != null) {
            result = prepareResult(entireLength, state);
        }
        return result;
    }

    private FilterState filter(@Nullable String text, int offset) {
        if (!StringUtils.isEmpty(text) && !expressionProcessors.isEmpty()) {
            String substring = MyConfiguration.getInstance().limitInputLength_andCutNewLine(text);
            CharSequence charSequence = MyConfiguration.getInstance().limitProcessingTime(substring);

            FilterState state = new FilterState(offset, charSequence);
            for (ExpressionProcessor processor : expressionProcessors) {
                    state = processor.process(state);
            }

            return state;
        }
        return null;
    }

    private Result prepareResult(int entireLength, FilterState state) {
        Result result = null;
        List<ResultItem> resultItemList = adjustWholeLineMatch(entireLength, state);
        if (resultItemList != null) {
            result = new Result(resultItemList);
            result.setNextAction(NextAction.CONTINUE_FILTERING);
        }
        return result;
    }

    protected List<ResultItem> adjustWholeLineMatch(int entireLength, FilterState state) {
        ConsoleViewContentType textAttributes = state.getConsoleViewContentType();
        List<ResultItem> resultItemList = state.getResultItemList();
        if (textAttributes != null) {
            lastTextAttributes = textAttributes;
            if (resultItemList == null) {
                resultItemList = Collections.singletonList(getResultItem(entireLength, state, textAttributes));
            } else {
                resultItemList.add(getResultItem(entireLength, state, textAttributes));
            }
        } else if (lastTextAttributes != null) {
            if (resultItemList == null) {
                resultItemList = Collections.singletonList(getResultItem(entireLength, state, lastTextAttributes));
            } else {
                resultItemList.add(getResultItem(entireLength, state, lastTextAttributes));
            }
        }
        return resultItemList;
    }
    private ResultItem getResultItem(int entireLength, FilterState state, ConsoleViewContentType textAttributes) {
        return new ResultItem(state.getOffset(), entireLength, null, textAttributes.getAttributes());
    }

}
