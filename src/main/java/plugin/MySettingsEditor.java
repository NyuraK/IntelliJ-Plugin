package plugin;

import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MySettingsEditor extends SettingsEditor<RunConfigurationBase> {
    private final RunConfigurationBase configuration;
    private MyConfigurable configurable;

    public MySettingsEditor(RunConfigurationBase configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void resetEditorFrom(@NotNull RunConfigurationBase s) {

    }

    @Override
    protected void applyEditorTo(@NotNull RunConfigurationBase s) throws ConfigurationException {
            configurable.apply();
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        configurable = new MyConfigurable(configuration);
        return configurable.createComponent();
    }
}
