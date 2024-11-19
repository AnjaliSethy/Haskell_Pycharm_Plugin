package org.intellij.sdk.language.psi;

import com.intellij.psi.tree.TokenSet;

public interface HaskellTokenSets{

    TokenSet COMMENTS = TokenSet.create(HaskellTypes.HS_COMMENT,HaskellTypes.HS_NCOMMENT);

}