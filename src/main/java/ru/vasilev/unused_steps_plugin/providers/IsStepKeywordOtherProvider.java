package ru.vasilev.unused_steps_plugin.providers;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInspection.*;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vasilev.unused_steps_plugin.checker.Checker;
import ru.vasilev.unused_steps_plugin.checker.IsMethodUsedChecker;
import ru.vasilev.unused_steps_plugin.checker.IsStepKeywordOtherChecker;

import static ru.vasilev.unused_steps_plugin.providers.UnusedStepsProvider.CUCUMBER_ANNOTATIONS;

public class IsStepKeywordOtherProvider extends AbstractBaseJavaLocalInspectionTool {
    Checker isStepKeywordOtherChecker = new IsStepKeywordOtherChecker();
    Checker isMethodUsedChecker = new IsMethodUsedChecker();

    @Nullable
    @Override
    public ProblemDescriptor[] checkMethod(@NotNull PsiMethod method, @NotNull InspectionManager manager, boolean isOnTheFly) {
        ProblemsHolder problemsHolder = new ProblemsHolder(manager, method.getContainingFile(), isOnTheFly);
        if (AnnotationUtil.isAnnotated(method, CUCUMBER_ANNOTATIONS, 0)
                && !isStepKeywordOtherChecker.check(method) && isMethodUsedChecker.check(method)) {
            problemsHolder.registerProblem(method.getAnnotations()[0],
                    "Incorrect method's keyword usage in features", ProblemHighlightType.WARNING);
            return problemsHolder.getResultsArray();
        }
        return ProblemDescriptor.EMPTY_ARRAY;
    }
}
