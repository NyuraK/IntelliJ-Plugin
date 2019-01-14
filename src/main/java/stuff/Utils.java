package stuff;

import com.intellij.ide.CopyProvider;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ide.CopyPasteManager;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class Utils {

    public static String getSelectedString(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        if (editor == null) {
            return null;
        }
        CaretModel caretModel = editor.getCaretModel();
        Caret primaryCaret = caretModel.getPrimaryCaret();
        return primaryCaret.getSelectedText();
    }

    public static String getString(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        CopyProvider provider = PlatformDataKeys.COPY_PROVIDER.getData(dataContext);
        if (provider == null) {
            return null;
        }
        provider.performCopy(dataContext);
        Transferable contents = CopyPasteManager.getInstance().getContents();
        try {
            return contents == null ? null : (String) contents.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e1) {
            return null;
        }
    }

    public static boolean isSelectedText(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        CopyProvider provider = PlatformDataKeys.COPY_PROVIDER.getData(dataContext);
        return provider != null && provider.isCopyEnabled(dataContext) && provider.isCopyVisible(dataContext);
    }
}
