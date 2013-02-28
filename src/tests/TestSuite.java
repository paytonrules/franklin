package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({RequestTest.class, ResponseTest.class, RouterTest.class, ServerTest.class})
public class TestSuite {
}
