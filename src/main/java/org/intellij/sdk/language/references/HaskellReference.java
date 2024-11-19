//package org.intellij.sdk.language.references;
//
//import com.intellij.openapi.project.Project;
//import com.intellij.psi.PsiElement;
//import com.intellij.psi.PsiPolyVariantReference;
//import com.intellij.psi.PsiReferenceBase;
//import com.intellij.psi.ResolveResult;
//import com.intellij.openapi.util.TextRange;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import java.util.ArrayList;
//import java.util.List;
//import com.intellij.codeInsight.lookup.LookupElement;
//import com.intellij.codeInsight.lookup.LookupElementBuilder;
//
//final class HaskellReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
//
//    private final String name;
//
//    HaskellReference(@NotNull PsiElement element, TextRange textRange) {
//        super(element, textRange);
//        name = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
//    }
//
//    @Override
//    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
//        Project project = myElement.getProject();
//        List<HaskellFunction> functions = HaskellUtil.findFunctions(project, name);
//        List<ResolveResult> results = new ArrayList<>();
//        for (HaskellFunction function : functions) {
//            results.add(new PsiElementResolveResult(function));
//        }
//        return results.toArray(new ResolveResult[0]);
//    }
//
//    @Nullable
//    @Override
//    public PsiElement resolve() {
//        ResolveResult[] resolveResults = multiResolve(false);
//        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
//    }
//
//    @Override
//    public Object @NotNull [] getVariants() {
//        Project project = myElement.getProject();
//        List<HaskellFunction> functions = HaskellUtil.findFunctions(project);
//        List<LookupElement> variants = new ArrayList<>();
//        for (HaskellFunction function : functions) {
//            if (function.getName() != null) {
//                variants.add(LookupElementBuilder.create(function)
//                        .withIcon(HaskellIcons.FUNCTION)
//                        .withTypeText(function.getContainingFile().getName()));
//            }
//        }
//        return variants.toArray();
//    }
//}