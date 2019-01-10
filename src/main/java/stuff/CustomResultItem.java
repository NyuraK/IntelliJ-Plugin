package stuff;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomResultItem {
    public final int highlightStartOffset;
    public final int highlightEndOffset;
    
    @Nullable
    public final ConsoleViewContentType consoleViewContentType;
    @Nullable
    public final HyperlinkInfo hyperlinkInfo;

    public CustomResultItem(int highlightStartOffset, int highlightEndOffset, @Nullable HyperlinkInfo hyperlinkInfo,
                            @Nullable ConsoleViewContentType consoleViewContentType) {
        this.highlightStartOffset = highlightStartOffset;
        this.highlightEndOffset = highlightEndOffset;
        this.hyperlinkInfo = hyperlinkInfo;
        this.consoleViewContentType = consoleViewContentType;
    }

    public int getHighlightStartOffset() {
        return highlightStartOffset;
    }

    public int getHighlightEndOffset() {
        return highlightEndOffset;
    }

    @Nullable
    public ConsoleViewContentType getConsoleViewContentType() {
        return consoleViewContentType;
    }

    @Nullable
    public HyperlinkInfo getHyperlinkInfo() {
        return hyperlinkInfo;
    }

    @Nullable
    public TextAttributes getAttributes() {
        return consoleViewContentType.getAttributes();
    }

    public static List<Filter.ResultItem> toIJ(List<CustomResultItem> resultItemList) {
        if (resultItemList == null) {
            return null;
        }
        List<Filter.ResultItem> transformed = new ArrayList<>(resultItemList.size());
        for (CustomResultItem CustomResult : resultItemList) {
            transformed.add(new Filter.ResultItem(CustomResult.highlightStartOffset, CustomResult.highlightEndOffset,
                    CustomResult.hyperlinkInfo, CustomResult.getAttributes()));
        }
        return transformed;
    }
}
