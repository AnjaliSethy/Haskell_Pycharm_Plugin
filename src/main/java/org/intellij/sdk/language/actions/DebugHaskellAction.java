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
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DebugHaskellAction extends AnAction {
    private ConsoleView consoleView;
    private OSProcessHandler processHandler;
    private BufferedWriter processInput;
    private Process process;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;

        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        Editor editor = e.getData(CommonDataKeys.EDITOR);

        if (file == null || !file.getExtension().equals("hs") || editor == null) return;

        String filePath = file.getPath();
        LogicalPosition caretPosition = editor.getCaretModel().getLogicalPosition();

        // Get or create debug console
        ToolWindow toolWindow = ToolWindowManager.getInstance(project)
                .getToolWindow("Debug");

        if (toolWindow == null) return;

        if (consoleView == null) {
            consoleView = new ConsoleViewImpl(project, true);
            Content content = ContentFactory.getInstance().createContent(
                    consoleView.getComponent(),
                    "Haskell Debug",
                    false
            );
            toolWindow.getContentManager().addContent(content);
        }

        toolWindow.show(() -> {
            try {
                startDebugSession(filePath);
            } catch (ExecutionException ex) {
                consoleView.print("Debug Error: " + ex.getMessage() + "\n",
                        ConsoleViewContentType.ERROR_OUTPUT);
            }
        });
    }

    private void startDebugSession(String filePath) throws ExecutionException {
        // Clear previous session if exists
        if (processHandler != null && !processHandler.isProcessTerminated()) {
            processHandler.destroyProcess();
        }

        consoleView.clear();
        consoleView.print("Starting debug session for " + filePath + "\n",
                ConsoleViewContentType.SYSTEM_OUTPUT);

        // Start GHCi with debugging options
        List<String> commands = new ArrayList<>();
        commands.add("ghci");
        commands.add("-fbreak-on-exception");
        commands.add("-fbreak-on-error");
        commands.add(filePath);

        GeneralCommandLine commandLine = new GeneralCommandLine(commands);
        process = commandLine.createProcess();
        processHandler = new OSProcessHandler(process, commandLine.getCommandLineString());

        // Set up input/output streams
        processInput = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

        // Attach console to process
        consoleView.attachToProcess(processHandler);

        // Add process listener for debug events
        processHandler.addProcessListener(new com.intellij.execution.process.ProcessAdapter() {
            @Override
            public void onTextAvailable(@NotNull com.intellij.execution.process.ProcessEvent event,
                                        @NotNull com.intellij.openapi.util.Key outputType) {
                String text = event.getText();
                if (text.contains("Stopped at")) {
                    handleBreakpoint(text);
                }
            }

            @Override
            public void processTerminated(@NotNull com.intellij.execution.process.ProcessEvent event) {
                consoleView.print("Debug session ended\n",
                        ConsoleViewContentType.SYSTEM_OUTPUT);
            }
        });

        // Start the debug process
        processHandler.startNotify();

        // Set up initial debug environment
        sendCommand(":set -fbreak-on-exception");
        sendCommand(":set -fbreak-on-error");
        sendCommand(":set stop :list");
        sendCommand(":show breaks");
    }

    private void handleBreakpoint(String breakpointInfo) {
        consoleView.print("Breakpoint hit: " + breakpointInfo + "\n",
                ConsoleViewContentType.SYSTEM_OUTPUT);
        sendCommand(":show breaks");
        sendCommand(":show context");
    }

    private void sendCommand(String command) {
        try {
            if (processInput != null) {
                processInput.write(command + "\n");
                processInput.flush();
            }
        } catch (IOException e) {
            consoleView.print("Error sending command: " + e.getMessage() + "\n",
                    ConsoleViewContentType.ERROR_OUTPUT);
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        e.getPresentation().setEnabledAndVisible(
                file != null && "hs".equals(file.getExtension())
        );
    }

    // Debug commands that can be called from the UI
    public void stepOver() {
        sendCommand(":step");
    }

    public void stepInto() {
        sendCommand(":steplocal");
    }

    public void stepOut() {
        sendCommand(":stepmodule");
    }

    public void continue_() {
        sendCommand(":continue");
    }

    public void setBreakpoint(String module, int line) {
        sendCommand(":break " + module + " " + line);
    }

    public void removeBreakpoint(String module, int line) {
        sendCommand(":delete " + module + " " + line);
    }

    public void showBacktrace() {
        sendCommand(":backtrace");
    }

    public void showBindings() {
        sendCommand(":show bindings");
    }

    public void evaluate(String expression) {
        sendCommand(":print " + expression);
    }

    public void quit() {
        sendCommand(":quit");
        if (processHandler != null) {
            processHandler.destroyProcess();
        }
    }
}