package config;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Optional;

public class ThreadListener implements IAlterSuiteListener {

    @Override
    public void alter(List<XmlSuite> suites) {
        int threads = BookStoreConfig.instance.threadCount();
        suites.forEach(suite -> {suite.setThreadCount(threads);
            Optional.ofNullable(suite.getParallel())
                    .filter(mode -> mode != XmlSuite.ParallelMode.NONE)
                    .map(Optional::of)
                    .orElseGet(() -> {
                        suite.setParallel(XmlSuite.ParallelMode.METHODS);
                        return Optional.of(XmlSuite.ParallelMode.METHODS);
                    });
        });
    }
}
