package ru.vasilev.unused_steps_plugin.providers;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInspection.*;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vasilev.unused_steps_plugin.checker.Checker;
import ru.vasilev.unused_steps_plugin.checker.IsMethodUsedChecker;

import java.util.Collection;
import java.util.List;

public class UnusedStepsProvider extends AbstractBaseJavaLocalInspectionTool {

    public static final String CUCUMBER_WHEN_ANNOTATION = "cucumber.api.java.en.When";
    public static final String CUCUMBER_THEN_ANNOTATION = "cucumber.api.java.en.Then";
    public static final String CUCUMBER_GIVEN_ANNOTATION = "cucumber.api.java.en.Given";
    public static final String CUCUMBER_6_WHEN_ANNOTATION = "io.cucumber.java.en.When";
    public static final String CUCUMBER_6_THEN_ANNOTATION = "io.cucumber.java.en.Then";
    public static final String CUCUMBER_6_GIVEN_ANNOTATION = "io.cucumber.java.en.Given";

    public static final Collection<String> CUCUMBER_ANNOTATIONS = List.of(CUCUMBER_THEN_ANNOTATION, CUCUMBER_WHEN_ANNOTATION, CUCUMBER_GIVEN_ANNOTATION, CUCUMBER_6_THEN_ANNOTATION, CUCUMBER_6_WHEN_ANNOTATION, CUCUMBER_6_GIVEN_ANNOTATION);

    private final Checker isMethodUsedChecker = new IsMethodUsedChecker();

    @Nullable
    @Override
    public ProblemDescriptor[] checkMethod(@NotNull PsiMethod method, @NotNull InspectionManager manager, boolean isOnTheFly) {
        ProblemsHolder problemsHolder = new ProblemsHolder(manager, method.getContainingFile(), isOnTheFly);
        if (AnnotationUtil.isAnnotated(method, UnusedStepsProvider.CUCUMBER_ANNOTATIONS, 0)
                && !isMethodUsedChecker.check(method)) {
            problemsHolder.registerProblem(method.getAnnotations()[0],
                    String.format("Step '%s' is never used",
                            method.getAnnotations()[0].getParameterList().getAttributes()[0].getLiteralValue()),
                    ProblemHighlightType.WARNING);
            return problemsHolder.getResultsArray();
        }
        return ProblemDescriptor.EMPTY_ARRAY;
    }

}
