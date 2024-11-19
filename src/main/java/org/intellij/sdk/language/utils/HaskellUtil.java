//package org.intellij.sdk.language.utils;
//
//import com.intellij.openapi.project.Project;
//import com.intellij.psi.PsiElement;
//import com.intellij.psi.PsiFile;
//import com.intellij.psi.util.FilenameIndex;
//import com.intellij.psi.PsiRecursiveElementVisitor;
//import com.intellij.openapi.util.text.StringUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HaskellUtil {
//
//    // Finds definitions of a given name in all Haskell files in the project
//    public static List<FoundDefinition> findDefinitionNode(Project project, String name, PsiElement context) {
//        List<FoundDefinition> definitions = new ArrayList<>();
//
//        // Iterate through all Haskell files in the project
//        for (PsiFile file : FilenameIndex.getAllFilesByName(project, "*.hs")) {
//            // Check if the file is a Haskell file
//            if (isHaskellFile(file)) {
//                file.accept(new PsiRecursiveElementVisitor() {
//                    @Override
//                    public void visitElement(PsiElement element) {
//                        super.visitElement(element);
//                        // Check if the element matches the name you're looking for
//                        if (element instanceof HaskellFunction && name.equals(((HaskellFunction) element).getName())) {
//                            definitions.add(new FoundDefinition(element, false)); // Assuming it's not an import
//                        }
//                    }
//                });
//            }
//        }
//
//        return definitions;
//    }
//
//    // Class to hold found definition information
//    public static class FoundDefinition {
//        public final PsiElement element;
//        public final boolean imprt; // Flag indicating if the definition is an import
//
//        public FoundDefinition(PsiElement element, boolean imprt) {
//            this.element = element;
//            this.imprt = imprt;
//        }
//    }
//
//    // Finds imported definitions of a given name in Haskell files
//    public static List<FoundDefinition> findImportedDefinitions(Project project, String name) {
//        List<FoundDefinition> importedDefinitions = new ArrayList<>();
//        // Logic to find imported definitions can be implemented here
//        return importedDefinitions;
//    }
//
//    // Checks if the file is a Haskell file
//    public static boolean isHaskellFile(PsiFile file) {
//        return file.getName().endsWith(".hs");
//    }
//}