package plugin;

import com.intellij.execution.filters.Filter;
import highlight.FilterState;
import org.apache.commons.lang.StringUtils;
import stuff.ExpressionItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionProcessor {
    private ExpressionItem expressionItem;
    private int matches;

    public ExpressionProcessor(ExpressionItem expressionItem) {
        this.expressionItem = expressionItem;
    }

    public ExpressionItem getExpressionItem() {
        return expressionItem;
    }

    public int getMatches() {
        return matches;
    }

    public void resetMatches() {
        this.matches = 0;
    }

    @SuppressWarnings("Duplicates")
    public FilterState process(FilterState state) {
        if (expressionItem.isEnabled() && !StringUtils.isEmpty(expressionItem.getExpression())) {
            CharSequence input = state.getCharSequence();
            if (expressionItem.isWholeLine()) { //not whole line
                Pattern pattern = expressionItem.getPattern();
                if (pattern != null) {
                    final Matcher matcher = pattern.matcher(input);
                    while (matcher.find()) {
                        matches++;
                        final int start = matcher.start();
                        final int end = matcher.end();
                        state.setMatchesSomething(true);
                        Filter.Result resultItem = new Filter.Result
                                (state.getOffset() + start, state.getOffset() + end,
                                null, expressionItem.getConsoleViewContentType(null).getAttributes());

                        state.add(resultItem);
                    }
                }
            }
            else if (matches(input)) {//whole line
                matches++;
                state.setConsoleViewContentType(
                        expressionItem.getConsoleViewContentType(state.getConsoleViewContentType()));
                state.setMatchesSomething(true);
            }
        }
        return state;
    }

    private boolean matches(CharSequence input) {
        Pattern pattern = expressionItem.getPattern();
        boolean matches = false;
        if (pattern != null) {
            matches = pattern.matcher(input).matches();
        }
        return matches;
    }

}
