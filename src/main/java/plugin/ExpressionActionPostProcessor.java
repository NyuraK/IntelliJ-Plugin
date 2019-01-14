package plugin;

import actions.AddHighlightAction;
import actions.ClearFromHighlights;
import actions.ConsoleAction;
import com.intellij.execution.actions.ConsoleActionsPostProcessor;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class ExpressionActionPostProcessor extends ConsoleActionsPostProcessor {

    @NotNull
    @Override
    public AnAction[] postProcess(@NotNull ConsoleView console, @NotNull AnAction[] actions) {
        ArrayList<AnAction> anActions = new ArrayList<>();
        anActions.add(new ConsoleAction(console));
        anActions.add(new ClearFromHighlights(console));
        anActions.addAll(Arrays.asList(actions));

        return anActions.toArray(new AnAction[anActions.size()]);
    }

    @NotNull
    @Override
    public AnAction[] postProcessPopupActions(@NotNull ConsoleView console, @NotNull AnAction[] actions) {
        ArrayList<AnAction> anActions = new ArrayList<>();
        anActions.add(new AddHighlightAction());
        anActions.addAll(Arrays.asList(super.postProcessPopupActions(console, actions)));
        return anActions.toArray(new AnAction[anActions.size()]);
    }
}
