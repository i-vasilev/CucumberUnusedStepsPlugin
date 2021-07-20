package ru.vasilev.unused_steps_plugin.checker;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNameValuePair;
import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;

import java.util.Objects;

public class IsMethodUsedChecker extends AbstractChecker {

    @Override
    protected boolean startChecking(Feature feature, PsiMethod method) {
        boolean isMethodUsedInFeature = false;
        if (feature != null) {
            for (ScenarioDefinition scenario :
                    feature.getChildren()) {
                if (isMethodUsedInScenario(scenario, method))
                    isMethodUsedInFeature = true;
            }
        }
        return isMethodUsedInFeature;
    }

    protected boolean isMethodUsedInScenario(ScenarioDefinition scenario, PsiMethod method) {
        boolean isMethodUsedInScenario = false;
        for (Step step :
                scenario.getSteps()) {
            if (isStepUsedInAnnotations(step, method.getAnnotations())) {
                isMethodUsedInScenario = true;
            }
        }
        return isMethodUsedInScenario;
    }

    protected boolean isStepUsedInAnnotations(Step step, PsiAnnotation[] annotations) {
        boolean isStepUsedInAnnotations = false;
        for (PsiAnnotation annotation :
                annotations) {
            if (isStepUsedInAttributes(step, annotation.getParameterList().getAttributes())) {
                isStepUsedInAnnotations = true;
            }
        }
        return isStepUsedInAnnotations;
    }

    protected boolean isStepUsedInAttributes(Step step, PsiNameValuePair[] attributes) {
        boolean isStepUsedInAttributes = false;
        for (PsiNameValuePair attribute :
                attributes) {
            if (step.getText().matches(
                    Objects.requireNonNull(attribute.getLiteralValue())
                            .replace("\\\\", "\\"))
            ) {
                isStepUsedInAttributes = true;
            }
        }
        return isStepUsedInAttributes;
    }
}
