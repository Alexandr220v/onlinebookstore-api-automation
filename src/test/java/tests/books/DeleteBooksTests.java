package tests.books;

import api.ResponseData;
import dto.Book;
import dto.ErrorResponse;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.DataUtils;
import utils.DateUtils;

import static assertion.Assertion.NEW_BOOK_FIELDS_SHOULD_MATCH;
import static dataprovider.BookDataProvider.*;
import static dataprovider.BookDataProvider.EXCERPT;


public class DeleteBooksTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify existing book can be deleted.")
    public void verifyDeleteExistingBookById() {
        Book getBookActual = booksApi.getBook(200);
        Assertions.assertThat(getBookActual.getTitle())
                .as("Book title should be match")
                .isEqualTo("Book 200");

        //  Delete the Book
        ValidatableResponse responseData = booksApi.deleteBook(200);
        Assertions.assertThat(responseData.extract().asString())
                .as("Response body should be empty")
                .isEmpty();

    }

    @Test(groups = "regression")
    @Description("Verify created new book can be deleted.")
    public void verifyDeleteBookById() {
        int uniqueId = DataUtils.uniqueId();
        Book newBookExpected = getBaseBook(uniqueId);
        Book newBookActual = booksApi.createBook(newBookExpected);
        Assertions.assertThat(newBookActual)
                .as(NEW_BOOK_FIELDS_SHOULD_MATCH)
                .isEqualTo(newBookExpected);

        // Possible Bug: Verify the Book exists before deleting
        Book getBookActual = booksApi.getBook(uniqueId);
        Assertions.assertThat(getBookActual)
                .as("Book should be exists")
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
