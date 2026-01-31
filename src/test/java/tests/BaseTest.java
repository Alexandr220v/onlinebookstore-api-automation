package tests;

import api.AuthorsApi;
import api.BooksApi;
import assertion.Assertion;
import com.google.inject.Inject;
import config.ApiConfig;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;

import java.lang.reflect.Method;

@Guice(modules = ApiConfig.class)
public abstract class BaseTest {
    @Inject
    protected AuthorsApi authorsApi;

    @Inject
    protected BooksApi booksApi;

    @Inject
    protected Assertion assertion;
}
