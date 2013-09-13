package org.jasmine.intellij;

import com.intellij.execution.Location;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.ClassUtil;
import org.dynjs.Config;
import org.dynjs.runtime.DynJS;
import org.dynjs.runtime.JSFunction;
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
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        Config config = new Config(this.getClass().getClassLoader());
        config.setCompileMode(Config.CompileMode.OFF);
        DynJS runtime = new DynJS(config);

        JSFunction function = (JSFunction) runtime.evaluate("require('intellij/producer').produce");
        Execution execution = (Execution) runtime.getExecutionContext().call(function, (Object)null, context.getModule().getModuleFile().getCanonicalPath());
        if (execution == null) { return null; }

        PsiClass mainClass = ClassUtil.findPsiClass(PsiManager.getInstance(context.getProject()), execution.main());
        if (mainClass == null) { return null; }

        RunnerAndConfigurationSettings settings = cloneTemplateConfiguration(context.getProject(), context);
        ApplicationConfiguration configuration = (ApplicationConfiguration) settings.getConfiguration();
        configuration.setModule(context.getModule());
        configuration.setMainClass(mainClass);
        configuration.setProgramParameters(execution.arguments());
        configuration.setName(execution.name());
        return settings;
    }

    @Override
    public int compareTo(Object o) {
        return PREFERED;
    }
}
