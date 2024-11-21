//package org.intellij.sdk.language.debugging;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//public class HaskellCompiler {
//
//    public void compile(String filePath) throws IOException, InterruptedException {
//        ProcessBuilder processBuilder = new ProcessBuilder("ghc", "-g", filePath);
//        processBuilder.redirectErrorStream(true);
//        Process process = processBuilder.start();
//        int exitCode = process.waitFor();
//        if (exitCode != 0) {
//            // Handle compilation errors
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line); // You might want to display this in the IDE
//            }
//        }
//    }
//}