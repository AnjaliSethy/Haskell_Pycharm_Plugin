package org.intellij.sdk.language.psi;

import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HaskellPsiUtil {

    public static class Import {
        public String module;
        public boolean isQualified;
        public List<String> hiding;
        public List<String> specificFunctions;

        public Import(String module, boolean isQualified, List<String> hiding, List<String> specificFunctions) {
            this.module = module;
            this.isQualified = isQualified;
            this.hiding = hiding;
            this.specificFunctions = specificFunctions;
        }
    }

    public static List<Import> parseImports(PsiFile file) {
        List<Import> imports = new ArrayList<>();
        String fileContent = file.getText();

        // Regex pattern to match Haskell import statements
        Pattern pattern = Pattern.compile("import\\s+(qualified\\s+)?([\\w.]+)(\\s+as\\s+[\\w.]+)?(\\s+hiding\\s*\\((.*?)\\))?|(\\s*\\((.*?)\\))?");
        Matcher matcher = pattern.matcher(fileContent);

        while (matcher.find()) {
            boolean isQualified = matcher.group(1) != null;
            String moduleName = matcher.group(2);
            List<String> hiding = new ArrayList<>();
            List<String> specificFunctions = new ArrayList<>();

            if (matcher.group(5) != null) {
                String[] hidingFunctions = matcher.group(5).split(",");
                for (String function : hidingFunctions) {
                    hiding.add(function.trim());
                }
            }

            if (matcher.group(7) != null) {
                String[] functions = matcher.group(7).split(",");
                for (String function : functions) {
                    specificFunctions.add(function.trim());
                }
            }

            imports.add(new Import(moduleName, isQualified, hiding, specificFunctions));
        }

        return imports;
    }
}