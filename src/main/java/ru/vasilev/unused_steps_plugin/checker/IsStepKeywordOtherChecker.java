package ru.vasilev.unused_steps_plugin.checker;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;

import java.util.Objects;

public class IsStepKeywordOtherChecker extends IsMethodUsedChecker {

    @Override
    protected boolean isStepUsedInAnnotations(Step step, PsiAnnotation[] annotations) {
        boolean isStepUsedInAnnotations = false;
        for (PsiAnnotation annotation :
                annotations) {
            if (Objects.requireNonNull(annotation.getQualifiedName()).endsWith(step.getKeyword().trim())
                    && isStepUsedInAttributes(step, annotation.getParameterList().getAttributes())) {
                isStepUsedInAnnotations = true;
            }
        }
        return isStepUsedInAnnotations;
    }
}
