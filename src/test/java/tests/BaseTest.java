package tests;

import api.AuthorsApi;
import api.BooksApi;
import com.google.inject.Inject;
import config.ApiConfig;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;

@Guice(modules = ApiConfig.class)
public abstract class BaseTest {
    @Inject
    protected AuthorsApi authorsApi;

    @Inject
    protected BooksApi booksApi;

}
