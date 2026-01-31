package tests.authors;

import assertion.Assertion;
import dataprovider.AuthorDataProvider;
import dto.Author;
import dto.ErrorResponse;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.DataUtils;

import static assertion.Assertion.NEW_AUTHOR_FIELDS_SHOULD_MATCH;
import static assertion.Assertion.UPDATED_AUTHOR_FIELDS_SHOULD_MATCH;
import static dataprovider.AuthorDataProvider.DEFAULT_FIRST_NAME;
import static dataprovider.AuthorDataProvider.DEFAULT_LAST_NAME;
import static utils.DataUtils.uniqueId;


public class UpdateAuthorsTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify author information can be updated.")
    public void verifyUpdateAuthor() {
        int uniqueId = uniqueId();
        Author expectedAuthor = new AuthorDataProvider().getBaseAuthor(uniqueId);

        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as(NEW_AUTHOR_FIELDS_SHOULD_MATCH)
                .isEqualTo(expectedAuthor);
        Author updatedAuthorExpected =  Author.builder()
                .id(uniqueId)
                .idBook(DataUtils.uniqueId())
                .firstName(DEFAULT_FIRST_NAME + "Updated")
                .lastName(DEFAULT_LAST_NAME + "Updated")
                .build();
        Author updatedBookActual = authorsApi.updateAuthor(updatedAuthorExpected);
        Assertions.assertThat(updatedBookActual).as("Updated book fields should match").isEqualTo(updatedAuthorExpected);
    }

    @Test(groups = "regression", dataProvider = "validAuthorData" , dataProviderClass = AuthorDataProvider.class)
    @Description("Verify author information can be updated partially.")
    public void verifyAuthorInformationCanBeUpdatedPartially(Author validAuthor) {
        int uniqueId = uniqueId();
        Author expectedAuthor = new AuthorDataProvider().getBaseAuthor(uniqueId);

        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as(NEW_AUTHOR_FIELDS_SHOULD_MATCH)
                .isEqualTo(expectedAuthor);

        Author updatedAuthorActual = authorsApi.updateAuthor(validAuthor);
        Assertions.assertThat(updatedAuthorActual)
                .as(UPDATED_AUTHOR_FIELDS_SHOULD_MATCH)
                .isEqualTo(validAuthor);
    }

    @Test(groups = "regression",dataProvider = "invalidAuthorData" , dataProviderClass = AuthorDataProvider.class)
    @Description("Verify author information cannot be updated.")
    public void verifyCreateInvalidAuthors(Author invalidAuthor, String expectedErrorKey) {
        int uniqueId = uniqueId();
        Author expectedAuthor = new AuthorDataProvider().getBaseAuthor(uniqueId);

        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as(NEW_AUTHOR_FIELDS_SHOULD_MATCH)
                .isEqualTo(expectedAuthor);

        ErrorResponse errorResponse = authorsApi.updateAuthorError(invalidAuthor);
        assertion.validateBadRequestError(expectedErrorKey, errorResponse);
    }
}
