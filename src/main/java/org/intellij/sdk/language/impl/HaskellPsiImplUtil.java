package org.intellij.sdk.language.impl;

import org.intellij.sdk.language.psi.HaskellExpression;
import org.intellij.sdk.language.psi.HaskellPragma;
import org.intellij.sdk.language.psi.HaskellQName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HaskellPsiImplUtil {

    /**
     * Gets the qualified name of a Haskell expression if applicable.
     *
     * @param expression the Haskell expression
     * @return the qualified name of the expression, or null if not available
     */
    @Nullable
    public static String getQualifiedName(@NotNull HaskellExpression expression) {
        List<HaskellQName> qNames = expression.getQNameList();
        if (!qNames.isEmpty()) {
            // Assuming you want the first qualified name as a representative
            return qNames.get(0).getText(); // Assuming getText() returns the name
        }
        return null; // No qualified name available
    }

    /**
     * Checks if a given expression is a variable.
     *
     * @param expression the Haskell expression
     * @return true if the expression is a variable, false otherwise
     */
    public static boolean isVariable(@NotNull HaskellExpression expression) {
        // Assuming that a variable would be represented by a specific QName or other criteria
        return expression.getQNameList().stream()
                .anyMatch(qName -> isVariableQName(qName)); // Implement isVariableQName method based on your logic
    }

    /**
     * Gets the type of a Haskell expression if applicable.
     *
     * @param expression the Haskell expression
     * @return the type of the expression, or null if not available
     */
    @Nullable
    public static String getExpressionType(@NotNull HaskellExpression expression) {
        // Assuming that type information can be derived from pragmas or other elements
        // You need to implement the logic to extract type information based on your PSI structure
        return extractTypeFromPragmas(expression.getPragmaList());
    }

    /**
     * Helper method to extract type information from pragmas.
     *
     * @param pragmas the list of pragmas
     * @return the type information as a String, or null if not found
     */
    @Nullable
    private static String extractTypeFromPragmas(@NotNull List<HaskellPragma> pragmas) {
        // Implement logic to extract type information from pragmas
        // For example, you might look for specific pragma types that define types
        return null; // Placeholder return
    }

    /**
     * Checks if a given qualified name represents a variable.
     *
     * @param qName the qualified name to check
     * @return true if it represents a variable, false otherwise
     */
    private static boolean isVariableQName(@NotNull HaskellQName qName) {
        // Implement logic to determine if the qualified name is a variable
        // This might involve checking the name format or other criteria
        return true; // Placeholder return, replace with actual logic
    }

    /**
     * Gets the parameters of a Haskell expression if it represents a function application.
     *
     * @param expression the Haskell expression
     * @return array of parameter names, or empty array if none
     */
    @NotNull
    public static String[] getExpressionParameters(@NotNull HaskellExpression expression) {
        // Assuming that parameters can be derived from the expression in some way
        // You need to implement the logic to extract parameters based on your PSI structure
        return new String[0]; // Placeholder return
    }

    // Add more utility methods as needed for your Haskell PSI implementation
}