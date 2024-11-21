package org.intellij.sdk.language.debugging;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HaskellRunConfiguration extends RunConfigurationBase<RunConfigurationOptions> {
    private String filePath;
    private String binaryPath;

    protected HaskellRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new HaskellRunConfigurationEditor(getProject());
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return new HaskellRunProfileState(getProject(), environment);
    }

    // Getter for filePath
    public String getFilePath() {
        return filePath;
    }

    // Setter for filePath
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Getter for binaryPath
    public String getBinaryPath() {
        return binaryPath;
    }

    // Setter for binaryPath
    public void setBinaryPath(String binaryPath) {
        this.binaryPath = binaryPath;
    }
}