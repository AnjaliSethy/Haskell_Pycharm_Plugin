//package org.intellij.sdk.language.index;
//
//import com.intellij.util.indexing.DataIndexer;
//import com.intellij.util.indexing.FileContent;
//import com.intellij.util.indexing.ID;
//import org.intellij.sdk.language.psi.HaskellFile;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class HaskellFileIndex implements DataIndexer<String, HaskellFile, FileContent> {
//    @Override
//    public Map<String, HaskellFile> map(FileContent inputData) {
//        Map<String, HaskellFile> result = new HashMap<>();
//        HaskellFile file = (HaskellFile) inputData.getPsiFile();
//        String moduleName = file.getModuleName();
//        if (moduleName != null) {
//            result.put(moduleName, file);
//        }
//        return result;
//    }
//}