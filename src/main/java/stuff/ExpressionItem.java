package stuff;

import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.util.xmlb.annotations.Transient;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class ExpressionItem {
    public static AtomicInteger atomicInteger = new AtomicInteger();

    @Transient
    private String id;
    private String expression;
    private transient Pattern pattern;
    private boolean isCaseSensitive;
    private ItemStyle style = new ItemStyle();
    private Operation operation = Operation.NONE;

    public ExpressionItem() {
        this.id = String.valueOf(atomicInteger.incrementAndGet());
    }

    public ExpressionItem setStyle(Color foreground, Color background) {
        this.style.setForeground(foreground);
        this.style.setBackground(background);
        return this;
    }

    @Transient
    public String getId() {
        return id;
    }

    public String getExpression() {
        return expression;
    }

    public ExpressionItem setExpression(String expression) {
        if (this.expression == null || expression == null || !this.expression.equals(expression)) {
            this.expression = expression;
            this.pattern = Pattern.compile(expression, computeFlags());
        }
        return this;
    }

    public ExpressionItem setCaseSensitive(boolean caseSensitive) {
        isCaseSensitive = caseSensitive;
        return this;
    }

    public Operation getOperation() {
        return operation;
    }

    public ExpressionItem setOperation(Operation operation) {
        this.operation = operation;
        return this;
    }

    private int computeFlags() {
        return isCaseSensitive ? 0 : Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
    }

    public Pattern getPattern() {
        if (pattern == null && expression != null) {
            pattern = Pattern.compile(expression, computeFlags());
        }
        return pattern;
    }

    public boolean isEnabled() {
        return true;
    }

    //TODO (NOT MY) not very good implementation
    public ConsoleViewContentType getConsoleViewContentType(ConsoleViewContentType consoleViewContentType) {
        ConsoleViewContentType result;
        if (consoleViewContentType != null) {
            final String newId = consoleViewContentType.toString() + "-" + getId();
            result = Cache.getInstance().get(newId);
            if (result == null) {
                result = createConsoleViewContentType(newId, consoleViewContentType.getAttributes().clone());
            }
        } else {
            String cacheIdentifier = getId();
            result = Cache.getInstance().get(cacheIdentifier);
            if (result == null) {
                result = createConsoleViewContentType(cacheIdentifier, new TextAttributes());
            }
        }
        return result;
    }

    private ConsoleViewContentType createConsoleViewContentType(String newId, TextAttributes newTextAttributes) {
        ConsoleViewContentType result;
        style.applyTo(newTextAttributes);
        result = new ConsoleViewContentType(newId, newTextAttributes);
        Cache.getInstance().put(newId, result);
        return result;
    }

    @Override
    public String toString() {
        return "ExpressionItem{" +
                "expression='" + expression + '\'' +
                ", id=" + id +
                '}';
    }

    public Color getColor() {
        return style.background;
    }

    @Override
    public boolean equals(Object obj) {
        ExpressionItem item = (ExpressionItem) obj;
        return this.id.equals(item.id);
    }

    private class ItemStyle {
        private Color foreground = Color.BLACK;
        private Color background = Color.BLACK;

        public void applyTo(TextAttributes newTextAttributes) {
            if (foreground != null) {
                newTextAttributes.setForegroundColor(foreground);
            }
            if (background != null) {
                newTextAttributes.setBackgroundColor(background);
            }
        }

        public void setForeground(Color foreground) {
            this.foreground = foreground;
        }

        public void setBackground(Color background) {
            this.background = background;
        }
    }
}