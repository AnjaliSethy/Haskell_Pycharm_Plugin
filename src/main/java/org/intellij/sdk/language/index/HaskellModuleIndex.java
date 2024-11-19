//package org.intellij.sdk.language.index;
//
//import com.intellij.openapi.project.Project;
//import com.intellij.psi.search.GlobalSearchScope;
//import com.intellij.util.indexing.FileBasedIndex;
//import com.intellij.util.indexing.ID;
//import org.intellij.sdk.language.psi.HaskellFile;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class HaskellModuleIndex {
//
//    // Define a unique ID for the index
//    public static final ID<String, HaskellFile> HASKELL_MODULE_INDEX_ID = ID.create("Haskell.ModuleIndex");
//
//    /**
//     * Retrieves all Haskell files associated with a given module name.
//     *
//     * @param project    the current project
//     * @param moduleName the name of the module to search for
//     * @param scope      the search scope
//     * @return a list of HaskellFile objects that match the given module name
//     */
//    @NotNull
//    public static List<HaskellFile> getFilesByModuleName(@NotNull Project project, @NotNull String moduleName, @NotNull GlobalSearchScope scope) {
//        List<HaskellFile> result = new ArrayList<>();
//
//        // Use the FileBasedIndex to get all files that match the module name
//        Collection<HaskellFile> files = FileBasedIndex.getInstance().getValues(HASKELL_MODULE_INDEX_ID, moduleName, scope);
//
//        for (HaskellFile file : files) {
//            // Ensure the file is a HaskellFile instance
//            if (file instanceof HaskellFile) {
//                result.add(file);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Indexing method to register Haskell files and their associated module names.
//     *
//     * @param file the Haskell file to index
//     */
//    public static void indexHaskellFile(@NotNull HaskellFile file) {
//        String moduleName = file.getModuleName(); // Get the module name
//        if (moduleName != null) {
//            // Use the FileBasedIndex to index the Haskell file with the module name
//            FileBasedIndex.getInstance().updateSingleIndex(HASKELL_MODULE_INDEX_ID, moduleName, file.getVirtualFile());
//        }
//    }
//}