package org.intellij.sdk.language.debugging;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class HaskellRunProfileState extends CommandLineState {
    private final Project project;

    protected HaskellRunProfileState(Project project, ExecutionEnvironment environment) {
        super(environment);
        this.project = project;
    }

    @Override
    protected @NotNull ProcessHandler startProcess() throws ExecutionException {
        GeneralCommandLine commandLine = new GeneralCommandLine("ghc");
        commandLine.setWorkDirectory(project.getBasePath());
        return new OSProcessHandler(commandLine);
    }
}

