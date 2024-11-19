package org.intellij.sdk.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import javax.swing.*;

public final class HaskellFileType extends LanguageFileType {

    public static final HaskellFileType INSTANCE = new HaskellFileType();

    private HaskellFileType() {
        super(HaskellLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Haskell File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Haskell language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "haskell";
    }

    @Override
    public Icon getIcon() {
        return HaskellIcons.FILE;
    }

}