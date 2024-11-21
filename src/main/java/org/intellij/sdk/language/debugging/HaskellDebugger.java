//package org.intellij.sdk.language.debugging;
//
//import java.io.IOException;
//
//public class HaskellDebugger {
//
//    public void debug(String binaryPath) throws IOException {
//        ProcessBuilder processBuilder = new ProcessBuilder("gdb", binaryPath);
//        processBuilder.inheritIO(); // Redirect GDB output to console
//        Process process = processBuilder.start();
//        // You may want to handle user input for GDB commands here
//    }
//}
