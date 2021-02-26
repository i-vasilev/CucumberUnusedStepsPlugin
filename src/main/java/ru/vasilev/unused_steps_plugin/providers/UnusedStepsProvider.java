package ru.vasilev.unused_steps_plugin.providers;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInsight.daemon.ImplicitUsageProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.annotations.NotNull;
import ru.vasilev.unused_steps_plugin.checker.Checker;
import ru.vasilev.unused_steps_plugin.checker.IsMethodUsedChecker;

import java.util.Collection;
import java.util.List;

public class UnusedStepsProvider implements ImplicitUsageProvider {

    public static final String CUCUMBER_WHEN_ANNOTATION = "cucumber.api.java.en.When";
    public static final String CUCUMBER_THEN_ANNOTATION = "cucumber.api.java.en.Then";
    public static final String CUCUMBER_GIVEN_ANNOTATION = "cucumber.api.java.en.Given";

    public static final Collection<String> CUCUMBER_ANNOTATIONS = List.of(CUCUMBER_THEN_ANNOTATION, CUCUMBER_WHEN_ANNOTATION, CUCUMBER_GIVEN_ANNOTATION);

    @Override
    public boolean isImplicitUsage(@NotNull PsiElement element) {
        if (element instanceof PsiMethod) {
            Checker checker = new IsMethodUsedChecker();
            return AnnotationUtil.isAnnotated((PsiModifierListOwner) element, CUCUMBER_ANNOTATIONS, 0)
                    && checker.check((PsiMethod) element);
        }
        return false;
    }

    @Override
    public boolean isImplicitRead(@NotNull PsiElement element) {
        return false;
    }

    @Override
    public boolean isImplicitWrite(@NotNull PsiElement element) {
        return false;
    }
}
