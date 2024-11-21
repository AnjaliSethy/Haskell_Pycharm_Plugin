package org.intellij.sdk.language.actions;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;
import com.intellij.execution.impl.ConsoleViewImpl;

public class RunHaskellAction extends AnAction {
    private ConsoleView consoleView;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;

        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file == null || !file.getExtension().equals("hs")) return;

        String filePath = file.getPath();

        // Get or create console view
        ToolWindow toolWindow = ToolWindowManager.getInstance(project)
                .getToolWindow("Terminal");
        if (toolWindow == null) return;

        if (consoleView == null) {
            consoleView = new ConsoleViewImpl(project, true);
        }

        toolWindow.show(() -> {
            try {
                // First compile
                GeneralCommandLine compileCmd = new GeneralCommandLine("ghc", filePath);
                ProcessHandler compileHandler = new OSProcessHandler(compileCmd);
                consoleView.clear();
                consoleView.print("Compiling " + filePath + "\n",
                        ConsoleViewContentType.SYSTEM_OUTPUT);
                consoleView.attachToProcess(compileHandler);
                compileHandler.startNotify();
                compileHandler.waitFor();

                if (compileHandler.getExitCode() == 0) {
                    // Then run
                    String execPath = filePath.substring(0, filePath.length() - 3);
                    GeneralCommandLine runCmd = new GeneralCommandLine(execPath);
                    ProcessHandler runHandler = new OSProcessHandler(runCmd);
                    consoleView.print("\nRunning " + execPath + "\n",
                            ConsoleViewContentType.SYSTEM_OUTPUT);
                    consoleView.attachToProcess(runHandler);
                    runHandler.startNotify();
                }

            } catch (ExecutionException ex) {
                consoleView.print("Error: " + ex.getMessage() + "\n",
                        ConsoleViewContentType.ERROR_OUTPUT);
            }
        });
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        e.getPresentation().setEnabledAndVisible(
                file != null && "hs".equals(file.getExtension())
        );
    }
}
