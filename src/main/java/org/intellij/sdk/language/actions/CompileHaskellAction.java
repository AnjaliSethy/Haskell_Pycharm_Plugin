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
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import com.intellij.execution.impl.ConsoleViewImpl;

public class CompileHaskellAction extends AnAction {
    private ConsoleView consoleView;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;

        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file == null || !file.getExtension().equals("hs")) return;

        String filePath = file.getPath();

        // Create or get the PyCharm terminal tool window
        ToolWindow toolWindow = ToolWindowManager.getInstance(project)
                .getToolWindow("Terminal");

        if (toolWindow == null) {
            return;
        }

        // Initialize console view if not already created
        if (consoleView == null) {
            consoleView = new ConsoleViewImpl(project, true);
            Content content = ContentFactory.getInstance().createContent(
                    consoleView.getComponent(),
                    "Haskell Compiler",
                    false
            );
            toolWindow.getContentManager().addContent(content);
        }

        // Show the terminal window
        toolWindow.show(() -> {
            try {
                // Prepare the GHC command
                GeneralCommandLine commandLine = new GeneralCommandLine("ghc", filePath);
                commandLine.setWorkDirectory(project.getBasePath());

                // Create process handler
                ProcessHandler processHandler = new OSProcessHandler(commandLine);
                consoleView.clear();
                consoleView.attachToProcess(processHandler);

                // Print initial message
                consoleView.print("Compiling " + filePath + "\n",
                        ConsoleViewContentType.SYSTEM_OUTPUT);

                // Add process termination callback
                processHandler.addProcessListener(new com.intellij.execution.process.ProcessAdapter() {
                    @Override
                    public void processTerminated(@NotNull com.intellij.execution.process.ProcessEvent event) {
                        int exitCode = event.getExitCode();
                        if (exitCode == 0) {
                            consoleView.print("Compilation successful!\n",
                                    ConsoleViewContentType.SYSTEM_OUTPUT);
                        } else {
                            consoleView.print("Compilation failed with exit code: " + exitCode + "\n",
                                    ConsoleViewContentType.ERROR_OUTPUT);
                        }
                    }
                });

                // Start the process
                processHandler.startNotify();

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