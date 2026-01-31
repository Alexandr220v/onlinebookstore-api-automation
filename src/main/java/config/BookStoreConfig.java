package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Config.Sources("classpath:bookstore.properties")
public interface BookStoreConfig extends Config {
    BookStoreConfig instance = ConfigFactory.create(BookStoreConfig.class, System.getProperties());

    @Key("baseURI")
    String baseURI();

    @Key("thread.count")
    @DefaultValue("1")
    int threadCount();

}
