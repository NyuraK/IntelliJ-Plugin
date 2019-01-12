package plugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class MyAppComponent implements ApplicationComponent, PersistentStateComponent<Configuration> {

    private Configuration state;
    protected int cachedMaxLengthToMatch = Integer.MAX_VALUE;

    public MyAppComponent() {
    }

    public static MyAppComponent getInstance() {
        return ApplicationManager.getApplication().getComponent(MyAppComponent.class);
    }

    @Nullable
    @Override
    public Configuration getState() {
        if (state == null) {
            state = new Configuration();
        }
        return state;
    }

    @Override
    public void loadState(Configuration state) {
        this.state = state;
    }
}
