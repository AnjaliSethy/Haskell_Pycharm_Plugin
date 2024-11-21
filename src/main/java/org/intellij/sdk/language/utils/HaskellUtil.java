package org.intellij.sdk.language.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.intellij.sdk.language.psi.HaskellTypes; // Import your HaskellTypes
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HaskellUtil {

    // Finds identifiers by name in all Haskell files in the project
    public static List<PsiElement> findIdentifiers(Project project, String name) {
        List<PsiElement> foundIdentifiers = new ArrayList<>();

        // Iterate through all files in the project
        for (PsiFile file : getAllHaskellFiles(project)) {
            // Use PsiTreeUtil to find all identifiers in the file
            file.accept(new PsiRecursiveElementVisitor() {
                @Override
                public void visitElement(PsiElement element) {
                    super.visitElement(element);
                    // Check if the element is a variable or constructor identifier
                    if ((element.getNode().getElementType() == HaskellTypes.HS_VARID ||
                            element.getNode().getElementType() == HaskellTypes.HS_CONID) &&
                            name.equals(element.getText())) {
                        foundIdentifiers.add(element);
                    }
                }
            });
        }

        return foundIdentifiers;
    }

    // Finds all identifiers in all Haskell files in the project
    public static List<PsiElement> findAllIdentifiers(Project project) {
        Set<PsiElement> allIdentifiers = new HashSet<>(); // Use a Set to avoid duplicates

        // Iterate through all files in the project
        for (PsiFile file : getAllHaskellFiles(project)) {
            // Use PsiTreeUtil to find all identifiers in the file
            file.accept(new PsiRecursiveElementVisitor() {
                @Override
                public void visitElement(PsiElement element) {
                    super.visitElement(element);
                    // Check if the element is a variable or constructor identifier
                    if (element.getNode().getElementType() == HaskellTypes.HS_VARID ||
                            element.getNode().getElementType() == HaskellTypes.HS_CONID) {
                        allIdentifiers.add(element);
                    }
                }
            });
        }

        return new ArrayList<>(allIdentifiers); // Convert back to List
    }

    // Checks if the file is a Haskell file
    public static boolean isHaskellFile(PsiFile file) {
        return file.getName().endsWith(".hs");
    }

    // Get all Haskell files in the project (recursive search)
    private static List<PsiFile> getAllHaskellFiles(Project project) {
        List<PsiFile> haskellFiles = new ArrayList<>();
        PsiManager psiManager = PsiManager.getInstance(project);

        // Use getBasePath() to get the base path of the project
        String basePath = project.getBasePath();
        if (basePath != null) {
            VirtualFile baseDir = VirtualFileManager.getInstance().findFileByUrl("file://" + basePath);
            if (baseDir != null) {
                // Recursively search for Haskell files in the base directory
                haskellFiles.addAll(getHaskellFilesInDirectory(baseDir, psiManager));
            }
        }

        return haskellFiles;
    }

    // Helper method to get Haskell files in a directory recursively
    private static List<PsiFile> getHaskellFilesInDirectory(VirtualFile directory, PsiManager psiManager) {
        List<PsiFile> haskellFiles = new ArrayList<>();
        for (VirtualFile virtualFile : directory.getChildren()) {
            if (virtualFile.isDirectory()) {
                haskellFiles.addAll(getHaskellFilesInDirectory(virtualFile, psiManager)); // Recursive call
            } else {
                PsiFile file = psiManager.findFile(virtualFile);
                if (file != null && isHaskellFile(file)) {
                    haskellFiles.add(file);
                }
            }
        }
        return haskellFiles;
    }
}