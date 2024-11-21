package org.intellij.sdk.language.debugging;

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public abstract class HaskellRunConfigurationType extends SettingsEditor<HaskellRunConfiguration> {

    private JTextField filePathField;
    private JTextField binaryPathField;

    public HaskellRunConfigurationType() {
        filePathField = new JTextField();
        binaryPathField = new JTextField();
    }

    @Override
    protected JComponent createEditor() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Haskell File:"));
        panel.add(filePathField);
        panel.add(new JLabel("Binary Path:"));
        panel.add(binaryPathField);
        return panel;
    }

    @Override
    public void resetEditorFrom(HaskellRunConfiguration configuration) {
        filePathField.setText(configuration.getFilePath());
        binaryPathField.setText(configuration.getBinaryPath());
    }

    @Override
    public void applyEditorTo(HaskellRunConfiguration configuration) throws ConfigurationException {
        configuration.setFilePath(filePathField.getText());
        configuration.setBinaryPath(binaryPathField.getText());
    }

    public abstract RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment);

    public abstract HaskellRunConfigurationType getConfigurationEditor();
}