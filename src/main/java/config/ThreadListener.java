package config;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Optional;

public class ThreadListener implements IAlterSuiteListener {

    @Override
    public void alter(List<XmlSuite> suites) {
        int threads = BookStoreConfig.instance.threadCount();

        for (XmlSuite suite : suites) {
            suite.setThreadCount(threads);
            if (suite.getParallel() == null || suite.getParallel() == XmlSuite.ParallelMode.NONE) {
                suite.setParallel(XmlSuite.ParallelMode.METHODS);
            }

            System.out.println("TESTNG DEBUG: Suite '" + suite.getName() +
                    "' is running with " + suite.getThreadCount() + " threads.");
        }
    }
}
