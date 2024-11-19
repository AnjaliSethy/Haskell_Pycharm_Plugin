package org.intellij.sdk.language.psi;

import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.List;

public class HaskellPsiUtil {
    public static class Import {
        public String module;

        public Import(String module) {
            this.module = module;
        }
    }

    public static List<Import> parseImports(PsiFile file) {
        // Your logic to parse imports from the file
        List<Import> imports = new ArrayList<>();
        // Example: populate imports
        imports.add(new Import("SomeModule"));
        return imports;
    }
}
