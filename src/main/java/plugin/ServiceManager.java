package plugin;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import highlight.HighlightFilter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
    private static final ServiceManager SERVICE_MANAGER = new ServiceManager();

    private List<WeakReference<HighlightFilter>> highlightFilters = new ArrayList<>();

    public static ServiceManager getInstance() {
        return SERVICE_MANAGER;
    }


    public Filter createHighlightFilter(Project project, ConsoleView consoleView) {

        HighlightFilter highlightFilter = new HighlightFilter(project, MyAppComponent.getInstance().getState());
        highlightFilters.add(new WeakReference<>(highlightFilter));
//        if (consoleView != null) {
//            ConsoleViewData consoleViewData = getOrCreateData(consoleView);
//            consoleViewData.grepHighlightFilter = grepHighlightFilter;
//        }
        return highlightFilter;
    }

    private class ConsoleViewData {

    }
}
