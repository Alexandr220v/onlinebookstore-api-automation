package tests.authors;

import api.AuthorsApi;
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

import static dataprovider.AuthorDataProvider.*;

public class DeleteAuthorsTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify existing author can be deleted.")
    public void verifyDeleteExistingAuthorById() {
        Author authorsApiAuthor = authorsApi.getAuthor(200);
        Assertions.assertThat(authorsApiAuthor.getFirstName())
                .as("Author first name should be match")
                .isEqualTo("First Name 200");

        //  Delete the Author
        authorsApi.deleteAuthor(200);
        ErrorResponse errorResponse = authorsApi.getAuthorError(200);
        Assertions.assertThat(errorResponse.getStatus())
                .as("Status code should be 404")
                .isEqualTo(HttpStatus.SC_NOT_FOUND);

        Assertions.assertThat(errorResponse.getTitle())
                .as("Error title should indicate the resource is missing")
                .containsIgnoringCase("Not Found");
    }

    @Test(groups = "regression")
    @Description("Verify new created author can be deleted.")
    public void verifyDeleteAuthorById() {
        int uniqueId = DataUtils.uniqueId();
        Author expectedAuthor = Author.builder()
                .id(uniqueId)
                .idBook(DataUtils.uniqueId())
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        // 1. Create the Author
        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as("Creation response should match input")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);

        // 2. Possible Technical Bug: Verify the author exists before deleting
        Author getAuthorActual = authorsApi.getAuthor(uniqueId);
        Assertions.assertThat(getAuthorActual)
                .as("Author should be retrievable after creation")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);

        // 3. Delete the Author
        authorsApi.deleteAuthor(uniqueId);
        ErrorResponse errorResponse = authorsApi.getAuthorError(uniqueId);
        Assertions.assertThat(errorResponse.getStatus())
                .as("Status code should be 404 Not Found after deletion")
                .isEqualTo(HttpStatus.SC_NOT_FOUND); // Changed from SC_BAD_REQUEST

        Assertions.assertThat(errorResponse.getTitle())
                .as("Error title should indicate the resource is missing")
                .containsIgnoringCase("Not Found");
    }
}
