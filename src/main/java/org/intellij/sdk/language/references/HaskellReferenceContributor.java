package org.intellij.sdk.language.references;

import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import org.intellij.sdk.language.HaskellLanguage;
import org.intellij.sdk.language.psi.HaskellTypes; // Import the HaskellTypes interface
import org.jetbrains.annotations.NotNull;

/**
 * Adds references so they later can be resolved.
 */
public class HaskellReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        // Define a pattern to match Haskell variable and constructor identifiers
        PsiElementPattern.Capture<PsiElement> pattern = PlatformPatterns.psiElement()
                .withLanguage(HaskellLanguage.INSTANCE)
                .and(PlatformPatterns.or(
                        PlatformPatterns.psiElement().withElementType(HaskellTypes.HS_VARID), // Match variable identifiers
                        PlatformPatterns.psiElement().withElementType(HaskellTypes.HS_CONID)  // Match constructor identifiers
                ));

        // Register a reference provider for the matched pattern
        registrar.registerReferenceProvider(pattern, new HaskellReferenceProvider());
    }
}