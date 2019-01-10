package plugin;

import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import stuff.ExpressionItem;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    public static final String DEFAULT = "120";
    public static final String DEFAULT_GREP = "1000";
    private static final String MAX_PROCESSING_TIME_DEFAULT = "1000";

    private List<ExpressionItem> expressionItems = new ArrayList<>();

    public List<ExpressionItem> getExpressionItems() {
        return expressionItems;
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
