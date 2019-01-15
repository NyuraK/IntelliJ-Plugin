package filters;

import com.intellij.execution.filters.Filter;
import org.apache.commons.lang.StringUtils;
import stuff.ExpressionItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionProcessor {
    private ExpressionItem expressionItem;

    public ExpressionProcessor(ExpressionItem expressionItem) {
        this.expressionItem = expressionItem;
    }

    public ExpressionItem getExpressionItem() {
        return expressionItem;
    }

    public FilterState process(FilterState state) {
        if (expressionItem.isEnabled() && !StringUtils.isEmpty(expressionItem.getExpression())) {
            CharSequence input = state.getCharSequence();
            Pattern pattern = expressionItem.getPattern();
            if (pattern != null) {
                final Matcher matcher = pattern.matcher(input);
                while (matcher.find()) {
                    final int start = matcher.start();
                    final int end = matcher.end();
                    state.executeAction(expressionItem);
                    Filter.Result resultItem = new Filter.Result
                            (state.getOffset() + start, state.getOffset() + end,
                            null, expressionItem.getConsoleViewContentType(null).getAttributes());
                    state.add(resultItem);
                }
            }
        }
        return state;
    }


}
