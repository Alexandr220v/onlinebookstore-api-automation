package tests.books;

import api.BooksApi;
import assertion.Assertion;
import com.google.inject.Inject;
import config.ApiConfig;
import dto.Author;
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

import java.util.List;

import static assertion.Assertion.NEW_BOOK_FIELDS_SHOULD_MATCH;
import static dataprovider.BookDataProvider.*;
import static dataprovider.BookDataProvider.EXCERPT;

public class GetBooksTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify created new book exists in books list.")
    public void verifyGreatedBookExistsInAllBookslist() {
        int uniqueId = DataUtils.uniqueId();
        Book newBookExpected = getBaseBook(uniqueId);
        Book newBookActual = booksApi.createBook(newBookExpected);
        Assertions.assertThat(newBookActual)
                .as(NEW_BOOK_FIELDS_SHOULD_MATCH)
                .isEqualTo(newBookExpected);
        List<Book> bookListActual = booksApi.getBooksList();
        Book foundInList = bookListActual.stream()
                .filter(a -> a.getId().equals(newBookExpected.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Book not found in list after creation"));

        Assertions.assertThat(foundInList)
                .as("The Book object inside the list should match exactly")
                .usingRecursiveComparison()
                .isEqualTo(newBookExpected);
    }

    @Test(groups = "regression")
    @Description("Verify existing book Presented in books list.")
    public void verifyExistingBookIsPresentedInAllBookslist() {
        Book existingBookExpected = Book.builder()
                .id(200)
                .title("Book 200")
                .description(DESCRIPTION)
                .pageCount(20000)
                .excerpt(EXCERPT)
                .publishDate(DateUtils.getCurrentDate())
                .build();
        List<Book> bookListActual = booksApi.getBooksList();
        Book existingBookActual = bookListActual.stream()
                .filter(a -> a.getId().equals(existingBookExpected.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Book not found in list after creation"));

        Assertions.assertThat(existingBookActual.getTitle())
                .as("The Book title inside the list should match exactly")
                .isEqualTo(existingBookExpected.getTitle());
    }
    @Test(groups = "regression")
    @Description("Verify get existing Book by id.")
    public void verifyGetExistingBookById() {
        Book existingBookExpected = Book.builder()
                .id(200)
                .title("Book 200")
                .description(DESCRIPTION)
                .pageCount(20000)
                .excerpt(EXCERPT)
                .publishDate(DateUtils.getCurrentDate())
                .build();
        Book getAuthorActual = booksApi.getBook(200);
        Assertions.assertThat(getAuthorActual.getTitle())
                .as("Existing Book fields should match")
                .isEqualTo(existingBookExpected.getTitle());
    }

    @Test(groups = "regression")
    @Description("Verify get non-existing Book by id.")
    public void verifyGetNonExistingBookById() {
        ErrorResponse errorResponse = booksApi.getBookError(1313);
        assertion.validateNotFoundError(errorResponse);
    }

}
