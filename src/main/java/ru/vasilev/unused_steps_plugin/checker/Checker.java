package ru.vasilev.unused_steps_plugin.checker;

import com.intellij.psi.PsiMethod;

public interface Checker {
    boolean check(PsiMethod method);
}
