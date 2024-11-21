package org.intellij.sdk.language.references;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.openapi.util.TextRange;
import org.intellij.sdk.language.icons.HaskellIcons;
import org.intellij.sdk.language.utils.HaskellUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;

final class HaskellReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String name;

    HaskellReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
        name = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        List<PsiElement> resolvedElements = HaskellUtil.findIdentifiers(project, name);
        List<ResolveResult> results = new ArrayList<>();
        for (PsiElement resolvedElement : resolvedElements) {
            results.add(new PsiElementResolveResult(resolvedElement));
        }
        return results.toArray(new ResolveResult[0]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        Project project = myElement.getProject();
        List<PsiElement> identifiers = HaskellUtil.findAllIdentifiers(project);
        List<LookupElement> variants = new ArrayList<>();
        for (PsiElement identifier : identifiers) {
            String identifierName = identifier.getText();
            if (identifierName != null) {
                variants.add(LookupElementBuilder.create(identifierName)
                        .withIcon(HaskellIcons.FILE) // Ensure HaskellIcons is defined
                        .withTypeText(identifier.getContainingFile().getName()));
            }
        }
        return variants.toArray();
    }
}