package org.intellij.sdk.language.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.intellij.sdk.language.psi.HaskellTypes; // Import your HaskellTypes
import org.jetbrains.annotations.NotNull;

public class HaskellReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                 @NotNull ProcessingContext context) {
        // Check if the element is a variable or constructor identifier
        if (element.getNode().getElementType() == HaskellTypes.HS_VARID ||
                element.getNode().getElementType() == HaskellTypes.HS_CONID) {

            TextRange textRange = element.getTextRange(); // Get the text range of the element
            return new PsiReference[]{new HaskellReference(element, textRange)};
        }
        return PsiReference.EMPTY_ARRAY; // Return an empty array if the element is not valid
    }
}