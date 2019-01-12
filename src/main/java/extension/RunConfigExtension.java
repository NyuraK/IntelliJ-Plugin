package extension;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunConfigurationExtension;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugin.MyConfigurable;

public class RunConfigExtension extends RunConfigurationExtension {
    @Override
    public <T extends RunConfigurationBase> void updateJavaParameters(T configuration, JavaParameters params, RunnerSettings runnerSettings) throws ExecutionException {
        MyConfigurable.getInstance().setConfigurationBase(configuration);
    }

    @Override
    protected boolean isApplicableFor(@NotNull RunConfigurationBase configuration) {
        return true;
    }

    @Nullable
    public SettingsEditor createEditor(@NotNull RunConfigurationBase configuration) {
        MyConfigurable.getInstance().setConfigurationBase(configuration);
        return MyConfigurable.getInstance();
    }
}
