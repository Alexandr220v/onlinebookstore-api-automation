package tests.books;

import api.BooksApi;
import com.google.inject.Inject;
import config.ApiConfig;
import dataprovider.BookDataProvider;
import dto.Book;
import dto.ErrorResponse;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.DataUtils;
import utils.DateUtils;

import static dataprovider.BookDataProvider.*;
import static dataprovider.BookDataProvider.EXCERPT;


public class UpdateBooksTests extends BaseTest {

    @Test(groups = "regression")
    @Description("Verify book information  can be updated.")
    public void verifyUpdateBook() {
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

        Book updatedBookExpected = Book.builder()
                .id(uniqueId)
                .title(TITLE + "Updated")
                .description(DESCRIPTION + " Updated")
                .pageCount(PAGE_COUNT + 1)
                .excerpt(EXCERPT + " Updated")
                .publishDate(DateUtils.getCurrentDate())
                .build();
        Book updatedBookActual = booksApi.updateBook(updatedBookExpected);
        Assertions.assertThat(updatedBookActual).as("Updated book fields should match").isEqualTo(updatedBookExpected);
    }

    @Test(groups = "regression",dataProvider = "validBookData" , dataProviderClass = BookDataProvider.class)
    @Description("Verify  book information can be updated with different field values.")
    public void verifyUpdateBookWithoutFullFields(Book validBook) {
        //Create book
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
        //Update book
        Book updatedBookActual = booksApi.createBook(validBook);
        Assertions.assertThat(updatedBookActual)
                .as("New book fields should match")
                .usingRecursiveComparison()
                .isEqualTo(validBook);
    }

    @Test(groups = "regression",dataProvider = "invalidBookData" , dataProviderClass = BookDataProvider.class)
    @Description("Verify updating book information -negative scenarios.")
    public void verifyUpdateInvalidBooks(Book invalidBook, String expectedErrorKey) {
        //Create book
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
        //Update book
        ErrorResponse errorResponse = booksApi.updateBookError(invalidBook);
        Assertions.assertThat(errorResponse.getTitle()).as("Error title")
                .isEqualTo("One or more validation errors occurred.");
        Assertions.assertThat(errorResponse.getErrors().keySet())
                .as("Errors map should contain a key related to: " + expectedErrorKey)
                .anyMatch(key -> key.contains(expectedErrorKey));
    }
}
