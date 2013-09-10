package org.jasmine.intellij;

import com.intellij.execution.Location;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.ClassUtil;
import org.jetbrains.annotations.Nullable;

public class JasmineRunConfigurationProducer extends RuntimeConfigurationProducer implements Cloneable {
    private PsiFile myFile;

    public JasmineRunConfigurationProducer() {
        super(ApplicationConfigurationType.getInstance());
    }

    @Override
    public PsiElement getSourceElement() {
        return myFile;
    }

    @Nullable
    @Override
    protected RunnerAndConfigurationSettings createConfigurationByElement(Location location, ConfigurationContext context) {
        PsiElement psiElement = location.getPsiElement();
        myFile = psiElement.getContainingFile();
        if(!myFile.getVirtualFile().getCanonicalPath().endsWith(".js")) { return null; }

        RunnerAndConfigurationSettings settings = cloneTemplateConfiguration(psiElement.getProject(), context);
        ApplicationConfiguration configuration = (ApplicationConfiguration)settings.getConfiguration();

        Module module = ModuleUtilCore.findModuleForPsiElement(psiElement);
        if (module != null) {
            configuration.setModule(module);
        }

        PsiClass mainClass = ClassUtil.findPsiClass(PsiManager.getInstance(context.getProject()), "org.jasmine.cli.Main");
        configuration.setMainClass(mainClass);
        configuration.setProgramParameters(location.getVirtualFile().getCanonicalPath());
        return settings;
    }

    @Override
    public int compareTo(Object o) {
        return PREFERED;
    }
}
