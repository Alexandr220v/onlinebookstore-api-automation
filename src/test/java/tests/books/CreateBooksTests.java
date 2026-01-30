package tests.books;

import api.AuthorsApi;
import api.BooksApi;
import com.google.inject.Inject;
import config.ApiConfig;
import dataprovider.BookDataProvider;
import dto.Book;
import dto.ErrorResponse;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.DataUtils;
import utils.DateUtils;

import java.util.Random;

import static dataprovider.BookDataProvider.*;


public class CreateBooksTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify  book can be created.")
    public void verifyCreateBookWithFullFields() {
        int uniqueId = DataUtils.uniqueId();
        Book newBookExpected = Book.builder()
                .id(uniqueId)
                .title(TITLE)
                .description(DESCRIPTION)
                .pageCount(PAGE_COUNT)
                .excerpt(EXCERPT)
                .publishDate(DateUtils.getCurrentDate())
                .build();
        Book newBookActual = booksApi.createBook(newBookExpected);
        Assertions.assertThat(newBookActual)
                .as("New book fields should match")
                .usingRecursiveComparison()
                .isEqualTo(newBookExpected);
    }

    @Test(groups = "regression",dataProvider = "validBookData" , dataProviderClass = BookDataProvider.class)
    @Description("Verify book can be created with different field values")
    public void verifyCreateWithoutFullBook(Book validBook) {
        Book newBookActual = booksApi.createBook(validBook);
        Assertions.assertThat(newBookActual)
                .as("New book fields should match")
                .usingRecursiveComparison()
                .isEqualTo(validBook);
    }

    @Test(groups = "regression",dataProvider = "invalidBookData" , dataProviderClass = BookDataProvider.class)
    @Description("Verify book can NOT be created with different field values")
    public void verifyCreateInvalids(Book invalidBook, String expectedErrorKey) {

        ErrorResponse errorResponse = booksApi.createBookError(invalidBook);
        Assertions.assertThat(errorResponse.getTitle()).as("Error title")
                .isEqualTo("One or more validation errors occurred.");
        Assertions.assertThat(errorResponse.getErrors().keySet())
                .as("Errors map should contain a key related to: " + expectedErrorKey)
                .anyMatch(key -> key.contains(expectedErrorKey));
    }
}
