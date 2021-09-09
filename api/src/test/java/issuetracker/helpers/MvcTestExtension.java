package issuetracker.helpers;

import org.junit.jupiter.api.extension.*;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class MvcTestExtension implements TestInstancePostProcessor, BeforeEachCallback, ParameterResolver {

    private ApplicationContext applicationContext;

    public MvcTestHelper getMvcTestHelper() {
        return applicationContext.getBean(MvcTestHelper.class);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        getMvcTestHelper().reset();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == MvcTestHelper.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getMvcTestHelper();
    }

    @Override
    public void postProcessTestInstance(Object o, ExtensionContext extensionContext) throws Exception {
        applicationContext = SpringExtension.getApplicationContext(extensionContext);
    }
}
