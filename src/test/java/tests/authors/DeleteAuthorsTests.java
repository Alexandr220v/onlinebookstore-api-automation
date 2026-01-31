package tests.authors;

import assertion.Assertion;
import dataprovider.AuthorDataProvider;
import dto.Author;
import dto.ErrorResponse;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import tests.BaseTest;

import static utils.DataUtils.uniqueId;

public class DeleteAuthorsTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify existing author can be deleted.")
    public void verifyDeleteExistingAuthorById() {
        Author existingAuthor = authorsApi.getAuthor(200);
        Assertions.assertThat(existingAuthor.getFirstName())
                .as("Author first name should be match")
                .isEqualTo("First Name 200");

        //  Delete the Author
        authorsApi.deleteAuthor(200);
        Author existingAuthorDeleted = authorsApi.getAuthor(200);
        Assertions.assertThat(existingAuthorDeleted)
                .as("Deleted author fields should be match")
                .isEqualTo(existingAuthor);
    }

    @Test(groups = "regression")
    @Description("Verify new created author can be deleted.")
    public void verifyDeleteAuthorById() {
        int uniqueId = uniqueId();
        Author expectedAuthor = new AuthorDataProvider().getBaseAuthor(uniqueId);
        // 1. Create the Author
        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as("Creation response should match input")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);

        Author getAuthorActual = authorsApi.getAuthor(uniqueId); // Possible  Bug or Demo Fake Api behaviour: Verify the author exists before deleting
        Assertions.assertThat(getAuthorActual)
                .as("Author should be exists")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);

        // 3. Delete the Author
        authorsApi.deleteAuthor(uniqueId);
        ErrorResponse errorResponse = authorsApi.getAuthorError(uniqueId);
        assertion.validateNotFoundError(errorResponse);
    }

}
