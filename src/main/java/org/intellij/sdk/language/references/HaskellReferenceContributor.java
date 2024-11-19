package org.intellij.sdk.language.references;

import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import org.intellij.sdk.language.HaskellLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * Adds references so they later can be resolved.
 */
public class HaskellReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {

    }
}