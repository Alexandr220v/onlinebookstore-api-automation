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

import static assertion.Assertion.NEW_BOOK_FIELDS_SHOULD_MATCH;
import static dataprovider.BookDataProvider.*;


public class CreateBooksTests extends BaseTest {

    @Test(groups = "regression")
    @Description("Verify book can be created.")
    public void verifyCreateBookWithFullFields() {
        int uniqueId = DataUtils.uniqueId();
        Book newBookExpected = getBaseBook(uniqueId);
        Book newBookActual = booksApi.createBook(newBookExpected);
        Assertions.assertThat(newBookActual)
                .as(NEW_BOOK_FIELDS_SHOULD_MATCH)
                .isEqualTo(newBookExpected);
    }

    @Test(groups = "regression",dataProvider = "validBookData" , dataProviderClass = BookDataProvider.class)
    @Description("Verify book can be created with different field values")
    public void verifyBookCanBeCreatedWithDifferentFields(Book validBook) {
        Book newBookActual = booksApi.createBook(validBook);
        Assertions.assertThat(newBookActual)
                .as(NEW_BOOK_FIELDS_SHOULD_MATCH)
                .isEqualTo(validBook);
    }

    @Test(groups = "regression",dataProvider = "invalidBookData" , dataProviderClass = BookDataProvider.class)
    @Description("Verify invalid book can NOT be created with different field values")
    public void verifyCreateInvalidBooks(Book invalidBook, String expectedErrorKey) {

        ErrorResponse errorResponse = booksApi.createBookError(invalidBook);
        assertion.validateBadRequestError(expectedErrorKey, errorResponse);
    }

}
