package org.jasmine.cli;

import org.jasmine.*;
import org.jasmine.Runtime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CompileModeTest {
    @Mock Runtime.Builder builder;

    @Test
    public void shouldBuildRuntimeWithJitCompilation(){
        CompileMode.JIT.apply(builder);

        verify(builder).jitCompile();
    }

    @Test
    public void shouldBuildRuntimeWithForcedCompilation(){
        CompileMode.FORCE.apply(builder);

        verify(builder).forceCompile();
    }

    @Test
    public void shouldBuildRuntimeWithCompilationOff(){
        CompileMode.OFF.apply(builder);

        verify(builder).noCompile();
    }
}
