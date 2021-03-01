package ru.vasilev.unused_steps_plugin.checker;

import com.intellij.psi.PsiMethod;
import gherkin.AstBuilder;
import gherkin.Parser;
import gherkin.ParserException;
import gherkin.TokenMatcher;
import gherkin.ast.Feature;
import gherkin.ast.GherkinDocument;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;

public abstract class AbstractChecker implements Checker {
    @Override
    public boolean check(PsiMethod method) {
        boolean isMethodUsedInFeatures = false;
        final Collection<File> files = FileUtils.listFiles(
                new File(
                        Paths.get(Objects.requireNonNull(method.getProject().getBasePath()))
                                .toAbsolutePath().toString()),
                new String[]{"feature"},
                true);
        for (File file :
                files) {
            Parser<GherkinDocument> parser = new Parser<>(new AstBuilder());
            TokenMatcher matcher = new TokenMatcher();

            String currentLine = readFile(file.getAbsolutePath());
            try {
                GherkinDocument gherkinDocument = parser.parse(currentLine, matcher);
                if (startChecking(gherkinDocument.getFeature(), method)) isMethodUsedInFeatures = true;
            } catch (ParserException.CompositeParserException ignore) {
            }
        }
        return isMethodUsedInFeatures;
    }

    protected abstract boolean startChecking(Feature feature, PsiMethod method);

    private String readFile(String path) {
        String fileContent = "";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path), StandardCharsets.UTF_8))) {
            String sub;
            while ((sub = br.readLine()) != null) {
                fileContent = String.format("%s%s%s", fileContent, sub, "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
