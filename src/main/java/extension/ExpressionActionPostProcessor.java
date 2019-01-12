package extension;

import actions.ConsoleAction;
import com.intellij.execution.actions.ConsoleActionsPostProcessor;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class ExpressionActionPostProcessor extends ConsoleActionsPostProcessor {

    @NotNull
    @Override
    public AnAction[] postProcess(@NotNull ConsoleView console, @NotNull AnAction[] actions) {
//        MyConfigurable.getInstance().createHighlightFilterIfMissing(console);

        ArrayList<AnAction> anActions = new ArrayList<>();
        anActions.add(new ConsoleAction(console));
        anActions.addAll(Arrays.asList(actions));

//        replaceClearAction(anActions);
        return anActions.toArray(new AnAction[anActions.size()]);
    }

    private void replaceClearAction(ArrayList<AnAction> anActions) {
        for (int i = 0; i < anActions.size(); i++) {
            AnAction anAction = anActions.get(i);
            if (anAction instanceof ConsoleViewImpl.ClearAllAction) {
                anActions.set(i, clearAction());
            }
        }
    }

    private ConsoleViewImpl.ClearAllAction clearAction() {
        return new ConsoleViewImpl.ClearAllAction() {
            @Override
            public void actionPerformed(AnActionEvent e) {
                super.actionPerformed(e);
                final ConsoleView consoleView = e.getData(LangDataKeys.CONSOLE_VIEW);
                if (consoleView != null) {
                    try {
                        //StatisticsManager.clearCount(consoleView);
                    } catch (Exception e1) {
                        // tough luck
                    }
                }
            }
        };
    }

}
