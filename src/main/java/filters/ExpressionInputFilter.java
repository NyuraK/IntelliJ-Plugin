package filters;

import com.intellij.execution.filters.InputFilter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.util.Pair;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugin.MyConfiguration;
import stuff.ExpressionItem;
import stuff.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//todo : Почему убирает либо вообще все, либо оставляет мусор, когда надо все убрать
public class ExpressionInputFilter implements InputFilter {
    private static final List<Pair<String, ConsoleViewContentType>> REMOVE_OUTPUT = Collections.singletonList(new Pair<>(null, null));

    private List<ExpressionProcessor> expressionProcessors;
    private boolean lastLineFiltered = false;
    private boolean removeNextNewLine = false;

    public ExpressionInputFilter() {
        expressionProcessors = new ArrayList<>();
        for (ExpressionItem item : MyConfiguration.getInstance().getExpressionItems()) {
            if (item.getOperation() == Operation.DELETE) {
                expressionProcessors.add(new ExpressionProcessor(item));
            }
        }
    }

    @Nullable
    @Override
    public List<Pair<String, ConsoleViewContentType>> applyFilter(@NotNull String text, @NotNull ConsoleViewContentType contentType) {
        if (lastLineFiltered && removeNextNewLine && text.equals("\n")) {
            removeNextNewLine = false;
            return REMOVE_OUTPUT;
        }

        FilterState state = filter(text, -1);
        List<Pair<String, ConsoleViewContentType>> pairs = prepareResult(state, contentType);

        return pairs;
    }

    private List<Pair<String, ConsoleViewContentType>> prepareResult(FilterState state, ConsoleViewContentType contentType) {
        List<Pair<String, ConsoleViewContentType>> result = null;
        if (state != null) {
            if (state.isExclude()) {
                result = REMOVE_OUTPUT;
                lastLineFiltered = true;
            }
//            else if (!state.isMatchesSomething() && lastLineFiltered) {
//                result = REMOVE_OUTPUT;
//            }
        }
        if (result == null) {
            lastLineFiltered = false;
            removeNextNewLine = false;
            return null;// input is not changed
        } else {
            removeNextNewLine = state.notTerminatedWithNewline();
            return result;
        }
    }

    private FilterState filter (@Nullable String text, int offset){
        if (!StringUtils.isEmpty(text) && !expressionProcessors.isEmpty()) {
            String substring = MyConfiguration.getInstance().limitInputLength_andCutNewLine(text);
            CharSequence charSequence = MyConfiguration.getInstance().limitProcessingTime(substring);

            FilterState state = new FilterState(offset, charSequence);
            for (ExpressionProcessor processor : expressionProcessors) {
                state = processor.process(state);
                if (!continueFiltering(state))
                    return state;
            }
            return state;
        }
        return null;
    }

    private boolean continueFiltering(FilterState state) {
        return state.getNextOperation() != Operation.EXIT;
    }
}
