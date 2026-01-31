package tests.authors;

import assertion.Assertion;
import dataprovider.AuthorDataProvider;
import dto.Author;
import dto.ErrorResponse;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import tests.BaseTest;

import static assertion.Assertion.NEW_AUTHOR_FIELDS_SHOULD_MATCH;
import static utils.DataUtils.uniqueId;

public class CreateAuthorTests extends BaseTest {

    @Test(groups = "regression")
    @Description("Verify that an author can be created.")
    public void verifyCreateAuthorWithFullFields() {
        int uniqueId = uniqueId();
        Author expectedAuthor = new AuthorDataProvider().getBaseAuthor(uniqueId);
        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as(NEW_AUTHOR_FIELDS_SHOULD_MATCH)
                .isEqualTo(expectedAuthor);
    }

    @Test(groups = "regression", dataProvider = "validAuthorData", dataProviderClass = AuthorDataProvider.class)
    @Description("Verify that  authors  can be created with different field values.")
    public void verifyCreateAuthorWithDifferentFields(Author validAuthor) {
        Author newAuthorActual = authorsApi.createAuthor(validAuthor);
        Assertions.assertThat(newAuthorActual)
                .as(NEW_AUTHOR_FIELDS_SHOULD_MATCH)
                .isEqualTo(validAuthor);
    }

    @Test(groups = "regression", dataProvider = "invalidAuthorData", dataProviderClass = AuthorDataProvider.class)
    @Description("Verify invalid author can NOT be created.")
    public void verifyCreateInvalidAuthors(Author invalidAuthor, String expectedErrorKey) {

        ErrorResponse errorResponse = authorsApi.createAuthorError(invalidAuthor);
        assertion.validateBadRequestError(expectedErrorKey, errorResponse);
    }
}
