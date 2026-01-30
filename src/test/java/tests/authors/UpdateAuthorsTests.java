package tests.authors;

import api.AuthorsApi;
import api.BooksApi;
import com.google.inject.Inject;
import config.ApiConfig;
import dataprovider.AuthorDataProvider;
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

import static dataprovider.AuthorDataProvider.FIRST_NAME;
import static dataprovider.AuthorDataProvider.LAST_NAME;
import static dataprovider.BookDataProvider.*;
import static dataprovider.BookDataProvider.EXCERPT;


public class UpdateAuthorsTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify updating author information.")
    public void verifyUpdateAuthor() {
        int uniqueId = DataUtils.uniqueId();
        Author expectedAuthor = Author.builder()
                .id(uniqueId)
                .idBook(DataUtils.uniqueId())
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as("New Author fields should match")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
        Author updatedAuthorExpected =  Author.builder()
                .id(uniqueId)
                .idBook(DataUtils.uniqueId())
                .firstName(FIRST_NAME + "Updated")
                .lastName(LAST_NAME + "Updated")
                .build();
        Author updatedBookActual = authorsApi.updateAuthor(updatedAuthorExpected);
        Assertions.assertThat(updatedBookActual).as("Updated book fields should match").isEqualTo(updatedAuthorExpected);
    }

    @Test(groups = "regression", dataProvider = "validAuthorData" , dataProviderClass = AuthorDataProvider.class)
    @Description("Verify updating author information partially.")
    public void verifyCreateAuthorWithoutFullFields(Author validAuthor) {
        int uniqueId = DataUtils.uniqueId();
        Author expectedAuthor = Author.builder()
                .id(uniqueId)
                .idBook(DataUtils.uniqueId())
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as("New Author fields should match")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
        Author updatedAuthorActual = authorsApi.updateAuthor(validAuthor);
        Assertions.assertThat(updatedAuthorActual)
                .as("New Author fields should match")
                .usingRecursiveComparison()
                .isEqualTo(validAuthor);
    }

    @Test(groups = "regression",dataProvider = "invalidAuthorData" , dataProviderClass = AuthorDataProvider.class)
    @Description("Verify  author information cannot be updated.")
    public void verifyCreateInvalidAuthors(Author invalidAuthor, String expectedErrorKey) {
        int uniqueId = DataUtils.uniqueId();
        Author expectedAuthor = Author.builder()
                .id(uniqueId)
                .idBook(DataUtils.uniqueId())
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as("New Author fields should match")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);

        ErrorResponse errorResponse = authorsApi.updateAuthorError(invalidAuthor);
        Assertions.assertThat(errorResponse.getTitle()).as("Error title")
                .isEqualTo("One or more validation errors occurred.");
        Assertions.assertThat(errorResponse.getErrors().keySet())
                .as("Errors map should contain a key related to: " + expectedErrorKey)
                .anyMatch(key -> key.contains(expectedErrorKey));
    }
}
