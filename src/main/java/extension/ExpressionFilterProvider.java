package extension;

import com.intellij.execution.console.ConsoleViewWrapperBase;
import com.intellij.execution.filters.ConsoleDependentFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import plugin.MyConfigurable;
import plugin.ServiceManager;


public class ExpressionFilterProvider extends ConsoleDependentFilterProvider {

    @NotNull
    @Override
    public Filter[] getDefaultFilters(@NotNull ConsoleView consoleView, @NotNull Project project, @NotNull GlobalSearchScope scope) {
        System.out.println("We're in getDefaultFilters");
//        new ConsoleViewWrapperBase(consoleView).addMessageFilter(MyConfigurable.getInstance().createHighlightFilter(project));
        return new Filter[]{MyConfigurable.getInstance().createHighlightFilter(project)};
    }
}
