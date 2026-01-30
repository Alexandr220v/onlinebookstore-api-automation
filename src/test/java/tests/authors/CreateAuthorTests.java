package tests.authors;

import api.AuthorsApi;
import api.BooksApi;
import com.google.inject.Inject;
import config.ApiConfig;
import dataprovider.AuthorDataProvider;
import dto.Author;
import dto.ErrorResponse;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.DataUtils;
import utils.DateUtils;

import static dataprovider.AuthorDataProvider.*;
import static utils.DataUtils.uniqueId;

public class CreateAuthorTests extends BaseTest {

    @Test(groups = "regression")
    @Description("Verify that an author can be created.")
    public void verifyCreateAuthorWithFullFields() {
        int uniqueId = uniqueId();
        Author expectedAuthor = Author.builder()
                .id(uniqueId)
                .idBook(uniqueId)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as("New Author fields should match")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }


    @Test(groups = "regression", dataProvider = "validAuthorData", dataProviderClass = AuthorDataProvider.class)
    @Description("Verify that an authors with different field values can be created.")
    public void verifyCreateAuthorWithoutFullFields(Author validAuthor) {
        Author newAuthorActual = authorsApi.createAuthor(validAuthor);
        Assertions.assertThat(newAuthorActual)
                .as("New Author fields should match")
                .usingRecursiveComparison()
                .isEqualTo(validAuthor);
    }

    @Test(groups = "regression", dataProvider = "invalidAuthorData", dataProviderClass = AuthorDataProvider.class)
    @Description("Verify  new author can NOT be created.")
    public void verifyCreateInvalidAuthors(Author invalidAuthor, String expectedErrorKey) {

        ErrorResponse errorResponse = authorsApi.createAuthorError(invalidAuthor);
        Assertions.assertThat(errorResponse.getTitle()).as("Error title")
                .isEqualTo("One or more validation errors occurred.");
        Assertions.assertThat(errorResponse.getErrors().keySet())
                .as("Errors map should contain a key related to: " + expectedErrorKey)
                .anyMatch(key -> key.contains(expectedErrorKey));
    }
}
