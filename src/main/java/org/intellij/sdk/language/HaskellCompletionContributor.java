package org.intellij.sdk.language;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.intellij.sdk.language.psi.HaskellTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class HaskellCompletionContributor extends CompletionContributor {
    public HaskellCompletionContributor() {
        // Basic completion for identifiers (functions, variables)
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(HaskellTypes.HS_VAR_ID).withLanguage(HaskellLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet resultSet) {
                        addFunctionCompletions(resultSet);
                        //addCustomIdentifierCompletions(resultSet);
                    }
                }
        );

        // Completion for "import" keyword and module names
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(HaskellTypes.HS_CON_ID).withLanguage(HaskellLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet resultSet) {
                        addModuleImportCompletions(resultSet);
                    }
                }
        );

        // Completion for pragmas
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(HaskellTypes.HS_ONE_PRAGMA).withLanguage(HaskellLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet resultSet) {
                        addPragmaCompletions(resultSet);
                    }
                }
        );

        // Completion for keywords
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(HaskellTypes.HS_VAR_ID).withLanguage(HaskellLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet resultSet) {
                        addKeywordCompletions(resultSet);
                    }
                }
        );

        // Completion for types
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(HaskellTypes.HS_CON_ID).withLanguage(HaskellLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet resultSet) {
                        addTypeCompletions(resultSet);
                    }
                }
        );
    }

    private void addKeywordCompletions(CompletionResultSet resultSet) {
        List<String> keywords = Arrays.asList(
                "data", "type", "where", "module", "let", "in",
                "case", "of", "import", "instance", "newtype", "deriving",
                "if", "then", "else", "do", "as", "qualified", "hiding",
                "forall", "mdo", "proc", "infix", "infixl", "infixr",
                "class", "instance", "default", "foreign", "inline", "noinline",
                "typeclass", "typefamily", "newtype", "let", "in", "if", "then", "else"
        );
        for (String keyword : keywords) {
            resultSet.addElement(LookupElementBuilder.create(keyword));
        }
    }

    private void addFunctionCompletions(CompletionResultSet resultSet) {
        List<Pair<String, String>> functions = Arrays.asList(
                Pair.of("map", "Applies a function to each element of a list."),
                Pair.of("filter", "Filters a list based on a predicate."),
                Pair.of("foldl", "Left fold."),
                Pair.of("foldr", "Right fold."),
                Pair.of("fold", " fold."),
                Pair.of("length", "Returns the length of a list."),
                Pair.of("sum", "Calculates the sum of a list of numbers."),
                Pair.of("txSignedBy", "Checks if a transaction is signed by a given public key."),
                Pair.of("concat", "Concaten ates a list of lists."),
                Pair.of("zip", "Combines two lists into a list of pairs."),
                Pair.of("take", "Takes the first n elements from a list."),
                Pair.of("drop", "Drops the first n elements from a list."),
                Pair.of("reverse", "Reverses a list."),
                Pair.of("elem", "Checks if an element is in a list."),
                Pair.of("not", "Logical negation."),
                Pair.of("&&", "Logical conjunction."),
                Pair.of("||", "Logical disjunction."),
                Pair.of("mapM", "Maps a monadic function over a list."),
                Pair.of("sequence", "Transforms a list of actions into an action that produces a list."),
                Pair.of("liftM", "Lifts a function to a monadic context."),
                Pair.of("join", "Flattens a monadic value.")
        );
        for (Pair<String, String> function : functions) {
            resultSet.addElement(LookupElementBuilder.create(function.getLeft()).withTypeText(function.getRight()));
        }
    }

    private void addModuleImportCompletions(CompletionResultSet resultSet) {
        System.out.println("Adding module import completions");
        List<String> moduleImports = Arrays.asList(
                "Data.List", "Control.Monad", "Prelude",
                "Plutus.Contract", "Plutus.V2.Ledger.Contexts",
                "Data.Maybe", "Data.Either", "Control.Applicative",
                "Data.Functor", "Data.Tuple", "Control.Concurrent",
                "System.IO", "Data.Text", "Data.Map",
                "Data.Set", "Control.Monad.Trans", "Control.Monad.State"
        );
        for (String moduleImport : moduleImports) {
            resultSet.addElement(LookupElementBuilder.create(moduleImport));
        }
    }

    private void addPragmaCompletions(CompletionResultSet resultSet) {
        List<String> pragmas = Arrays.asList(
                "LANGUAGE ", "OPTIONS_GHC ", "WARNING ", "DEPRECATED ",
                "INLINE ", "NOINLINE ", "INLINABLE ", "CONLIKE ",
                "RULES ", "ANN ", "LINE ", "SPECIALIZE ",
                "UNPACK ", "SOURCE ", "SCC ",
                "LANGUAGE GADTs", "LANGUAGE TypeFamilies", "LANGUAGE MultiParamTypeClasses"
        );
        for (String pragma : pragmas) {
            resultSet.addElement(LookupElementBuilder.create(pragma));
        }
    }

//    private void addCustomIdentifierCompletions(CompletionResultSet resultSet) {
//        List<String> customIdentifiers = Arrays.asList(
//                "myCustomFunction", "myVariable", "anotherCustomVar",
//                "exampleList", "myDataType", "myHelperFunction",
//                "processTransaction", "calculateSum", "userInput",
//                "defaultConfig", "maxRetries", "retryDelay", "isValid",
//                "transactionFee", "blockchainState", "contractAddress"
//        );
//        for (String identifier : customIdentifiers) {
//            resultSet.addElement(LookupElementBuilder.create(identifier));
//        }
//    }

    private void addOperatorCompletions(CompletionResultSet resultSet) {
        List<String> operators = Arrays.asList(
                "+", "-", "*", "/", "==", "<", ">", "<=", ">="
        );
        for (String operator : operators) {
            resultSet.addElement(LookupElementBuilder.create(operator));
        }
    }


    private void addPlutusIdentifierCompletions(CompletionResultSet resultSet) {
        List<String> plutusIdentifiers = Arrays.asList(
                "Ledger", "PlutusTx", "Contract", "mkValidator", "txSignedBy"
        );
        for (String identifier : plutusIdentifiers) {
            resultSet.addElement(LookupElementBuilder.create(identifier));
        }
    }

    private void addTypeclassCompletions(CompletionResultSet resultSet) {
        List<String> typeclasses = Arrays.asList(
                "Eq", "Ord", "Show", "Functor", "Monad"
        );
        for (String typeclass : typeclasses) {
            resultSet.addElement(LookupElementBuilder.create(typeclass));
        }
    }


    private void addTypeCompletions(CompletionResultSet resultSet) {
        List<String> types = Arrays.asList(
                "Int", "Bool", "Char", "String", "Float", "Double",
                "Maybe", "List", "Tuple", "Either", "IO",
                "Function", "Map", "Set", "CustomType",
                "User DefinedType", "Monad", "Functor", "Applicative"
        );
        for (String type : types) {
            resultSet.addElement(LookupElementBuilder.create(type));
        }
    }
}