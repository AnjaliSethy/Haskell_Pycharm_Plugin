package org.intellij.sdk.language;

import com.intellij.lexer.FlexAdapter;
import org.intellij.sdk.language.grammars._HaskellLexer;

public class HaskellLexerAdapter extends FlexAdapter {
    public HaskellLexerAdapter() {
        super(new _HaskellLexer(null));
    }
}
