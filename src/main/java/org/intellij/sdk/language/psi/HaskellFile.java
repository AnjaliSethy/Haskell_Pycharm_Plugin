package org.intellij.sdk.language.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.psi.FileViewProvider;
import org.intellij.sdk.language.HaskellFileType;
import org.intellij.sdk.language.HaskellLanguage;
import org.jetbrains.annotations.Nullable;

public class HaskellFile extends PsiFileBase {
    public HaskellFile(FileViewProvider viewProvider) {
        super(viewProvider, HaskellLanguage.INSTANCE);
    }

    @Override
    public HaskellFileType getFileType() {
        return HaskellFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Haskell File";
    }

    /**
     * Returns the module name of the Haskell file.
     * This method parses the file content to find the module declaration.
     *
     * @return the module name as a String, or null if not found
     */
    @Nullable
    public String getModuleName() {
        String text = getText(); // Get the content of the file
        String[] lines = text.split("\n"); // Split the content into lines

        for (String line : lines) {
            line = line.trim(); // Trim whitespace
            if (line.startsWith("module ")) {
                // Extract the module name
                String moduleName = line.substring(7).split(" ")[0]; // Get the name after "module "
                return moduleName; // Return the module name
            }
        }
        return null; // Return null if no module declaration is found
    }
}