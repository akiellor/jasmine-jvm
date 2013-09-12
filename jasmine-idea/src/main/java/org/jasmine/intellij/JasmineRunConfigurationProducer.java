package org.jasmine.intellij;

import com.intellij.execution.Location;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.ClassUtil;
import org.jetbrains.annotations.Nullable;

public class JasmineRunConfigurationProducer extends RuntimeConfigurationProducer implements Cloneable {
    private PsiFile containingFile;

    public JasmineRunConfigurationProducer() {
        super(ApplicationConfigurationType.getInstance());
    }

    @Override
    public PsiElement getSourceElement() {
        return containingFile;
    }

    @Nullable
    @Override
    protected RunnerAndConfigurationSettings createConfigurationByElement(Location location, final ConfigurationContext context) {
        final PsiElement psiElement = context.getLocation().getPsiElement();
        containingFile = psiElement.getContainingFile();
        if (containingFile == null) { return null; }

        VirtualFile file = containingFile.getVirtualFile();
        if (file == null) { return null; }

        String canonicalPath = file.getCanonicalPath();
        if (canonicalPath == null || !canonicalPath.endsWith(".js")) { return null; }

        PsiClass mainClass = ClassUtil.findPsiClass(PsiManager.getInstance(context.getProject()), "org.jasmine.cli.Main");
        if (mainClass == null) { return null; }

        RunnerAndConfigurationSettings settings = cloneTemplateConfiguration(context.getProject(), context);
        ApplicationConfiguration configuration = (ApplicationConfiguration) settings.getConfiguration();
        configuration.setModule(context.getModule());
        configuration.setMainClass(mainClass);
        configuration.setProgramParameters(canonicalPath);
        configuration.setName(file.getPresentableName());
        return settings;
    }

    @Override
    public int compareTo(Object o) {
        return PREFERED;
    }
}
