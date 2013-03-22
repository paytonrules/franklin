package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.responders.FileSystemResponderTest;
import tests.responders.ParameterResponderTest;
import tests.responders.RedirectResponderTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RequestTest.class,
        HtmlGeneratorTest.class,
        ResponseWriterTest.class,
        UtilitiesTest.class,
        RequestHandlerTest.class,
        RouterTest.class,
        ServerTest.class,
        ParameterResponderTest.class,
        RedirectResponderTest.class,
        FileSystemResponderTest.class,
})
public class TestSuite {
}
