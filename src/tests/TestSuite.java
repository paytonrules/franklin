package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RequestReaderTest.class,
        DirectoryGeneratorTest.class,
        ResponseWriterTest.class,
        UtilitiesTest.class,
        RouterTest.class,
        ServerTest.class,
        RedirectResponderTest.class,
        FileSystemResponderTest.class,
})
public class TestSuite {
}
