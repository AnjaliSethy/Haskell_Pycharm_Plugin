package org.intellij.sdk.language.debugging;


import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class HaskellRunConfigurationEditor extends SettingsEditor<HaskellRunConfiguration> {
    private JPanel myPanel;
    private final Project project;

    public HaskellRunConfigurationEditor(Project project) {
        this.project = project;
        myPanel = new JPanel();
    }

    @Override
    protected void resetEditorFrom(@NotNull HaskellRunConfiguration s) {
    }

    @Override
    protected void applyEditorTo(@NotNull HaskellRunConfiguration s) {
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return myPanel;
    }
}