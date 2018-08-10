package ai.leverton.demo.config;

import java.io.File;
import java.util.Properties;

public class FrameworkConfig {
    private Properties frameworkProperties;

    public FrameworkConfig() {
        this(new File(System.getProperty("user.dir")+"/src/main/resources/frameworkConfig.properties"));
    }

    public FrameworkConfig(File file) {
        frameworkProperties = new PropertyReader(file).getPropertyFile();
    }

    public Properties getConfigProperties() {
        return frameworkProperties;
    }
}
