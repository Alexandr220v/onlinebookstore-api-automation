package tests.books;

import assertion.Assertion;
import dataprovider.BookDataProvider;
import dto.Book;
import dto.ErrorResponse;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.DataUtils;
import utils.DateUtils;

import static assertion.Assertion.NEW_BOOK_FIELDS_SHOULD_MATCH;
import static assertion.Assertion.UPDATED_BOOK_FIELDS_SHOULD_MATCH;
import static dataprovider.BookDataProvider.*;
import static dataprovider.BookDataProvider.EXCERPT;


public class UpdateBooksTests extends BaseTest {

    @Test(groups = "regression")
    @Description("Verify book information  can be updated.")
    public void verifyUpdateBook() {
        int uniqueId = DataUtils.uniqueId();
        Book newBookExpected = getBaseBook(uniqueId);
        Book newBookActual = booksApi.createBook(newBookExpected);
        Assertions.assertThat(newBookActual)
                .as(NEW_BOOK_FIELDS_SHOULD_MATCH)
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
        Assertions.assertThat(updatedBookActual).as(UPDATED_BOOK_FIELDS_SHOULD_MATCH).isEqualTo(updatedBookExpected);
    }

    @Test(groups = "regression",dataProvider = "validBookData" , dataProviderClass = BookDataProvider.class)
    @Description("Verify  book information can be updated with different field values.")
    public void verifyUpdateBookWithoutFullFields(Book validBook) {
        //Create book
        int uniqueId = DataUtils.uniqueId();
        Book newBookExpected = getBaseBook(uniqueId);
        Book newBookActual = booksApi.createBook(newBookExpected);
        Assertions.assertThat(newBookActual)
                .as(NEW_BOOK_FIELDS_SHOULD_MATCH)
                .isEqualTo(newBookExpected);
        //Update book
        Book updatedBookActual = booksApi.updateBook(validBook);
        Assertions.assertThat(updatedBookActual)
                .as(UPDATED_BOOK_FIELDS_SHOULD_MATCH)
                .isEqualTo(updatedBookActual);
    }

    @Test(groups = "regression",dataProvider = "invalidBookData" , dataProviderClass = BookDataProvider.class)
    @Description("Verify invalid book information can not be updated.")
    public void verifyUpdateInvalidBooks(Book invalidBook, String expectedErrorKey) {
        //Create book
        int uniqueId = DataUtils.uniqueId();
        Book newBookExpected = getBaseBook(uniqueId);
        Book newBookActual = booksApi.createBook(newBookExpected);
        Assertions.assertThat(newBookActual)
                .as(NEW_BOOK_FIELDS_SHOULD_MATCH)
                .usingRecursiveComparison()
                .isEqualTo(newBookExpected);
        //Update book
        ErrorResponse errorResponse = booksApi.updateBookError(invalidBook);
       assertion.validateBadRequestError(expectedErrorKey, errorResponse);
    }
}
