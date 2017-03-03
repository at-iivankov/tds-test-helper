package app.testcase.formatter;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class TestCaseFormatterSrv {

    public static TestCaseFormatterDo formatTestCase(String originalText) throws Exception {
        TestCaseFormatterBo testCaseFormatterBo = new TestCaseFormatterBo(originalText);
        return testCaseFormatterBo.format();
    }
}
