package tests.books;

import api.BooksApi;
import api.ResponseData;
import com.google.inject.Inject;
import config.ApiConfig;
import dto.Author;
import dto.Book;
import dto.ErrorResponse;
import io.qameta.allure.Description;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.DataUtils;
import utils.DateUtils;

import static dataprovider.AuthorDataProvider.FIRST_NAME;
import static dataprovider.AuthorDataProvider.LAST_NAME;
import static dataprovider.BookDataProvider.*;
import static dataprovider.BookDataProvider.EXCERPT;


public class DeleteBooksTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify existing book can be deleted.")
    public void verifyDeleteExistingBookById() {
        int uniqueId = DataUtils.uniqueId();
        Book getBookActual = booksApi.getBook(200);
        Assertions.assertThat(getBookActual.getTitle())
                .as("Book title should be match")
                .isEqualTo("Book 200");

        //  Delete the Book
        booksApi.deleteBook(200);
        ErrorResponse errorResponse = booksApi.getBookError(200);
        Assertions.assertThat(errorResponse.getStatus())
                .as("Status code should be 404")
                .isEqualTo(HttpStatus.SC_NOT_FOUND);

        Assertions.assertThat(errorResponse.getTitle())
                .as("Error title should indicate the resource is missing")
                .containsIgnoringCase("Not Found");
    }

    @Test(groups = "regression")
    @Description("Verify created new book can be deleted.")
    public void verifyDeleteBookById() {

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

        // Possible Technical Bug: Verify the Book exists before deleting
        Book getBookActual = booksApi.getBook(uniqueId);
        Assertions.assertThat(getBookActual)
                .as("Book should be exists")
                .usingRecursiveComparison()
                .isEqualTo(newBookExpected);

        //  Delete the Book
        booksApi.deleteBook(uniqueId);
        ErrorResponse errorResponse = booksApi.getBookError(uniqueId);
        Assertions.assertThat(errorResponse.getStatus())
                .as("Status code should be 404")
                .isEqualTo(HttpStatus.SC_NOT_FOUND);

        Assertions.assertThat(errorResponse.getTitle())
                .as("Error title should indicate the resource is missing")
                .containsIgnoringCase("Not Found");
    }
}
