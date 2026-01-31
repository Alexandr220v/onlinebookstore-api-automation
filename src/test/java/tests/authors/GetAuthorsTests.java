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

import java.util.List;

import static assertion.Assertion.NEW_AUTHOR_FIELDS_SHOULD_MATCH;
import static dataprovider.AuthorDataProvider.DEFAULT_FIRST_NAME;
import static dataprovider.AuthorDataProvider.DEFAULT_LAST_NAME;
import static utils.DataUtils.uniqueId;


public class GetAuthorsTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify get author by id.")
    public void verifyGetAuthorById() {
        int uniqueId = uniqueId();
        Author expectedAuthor = new AuthorDataProvider().getBaseAuthor(uniqueId);
        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as(NEW_AUTHOR_FIELDS_SHOULD_MATCH)
                .isEqualTo(expectedAuthor);
        Author getAuthorActual = authorsApi.getAuthor(uniqueId); // Possible  Bug or Demo Fake Api behaviour: Verify the author exists after created
        Assertions.assertThat(getAuthorActual)
                .as(NEW_AUTHOR_FIELDS_SHOULD_MATCH)
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test(groups = "regression")
    @Description("Verify get existing author by id.")
    public void verifyGetExistingAuthorById() {
        Author expectedAuthor = Author.builder()
                .id(200)
                .idBook(65)
                .firstName("First Name 200")
                .lastName("Last Name 200")
                .build();
        Author getAuthorActual = authorsApi.getAuthor(200);
        Assertions.assertThat(getAuthorActual)
                .as("Existing Author fields should match")
                .isEqualTo(expectedAuthor);
    }

    @Test(groups = "regression")
    @Description("Verify get non-existing author by id.")
    public void verifyGetNonExistingAuthorById() {
        ErrorResponse errorResponse = authorsApi.getAuthorError(1313);
       assertion.validateNotFoundError(errorResponse);
    }

    @Test(groups = "regression")
    @Description("Verify author exists in authors list.")
    public void verifyGreatedAuthorExistsInAllAuthor() {
        int uniqueId = uniqueId();
        Author expectedAuthor = new AuthorDataProvider().getBaseAuthor(uniqueId);
        Author newAuthorActual = authorsApi.createAuthor(expectedAuthor);
        Assertions.assertThat(newAuthorActual)
                .as(NEW_AUTHOR_FIELDS_SHOULD_MATCH)
                .isEqualTo(expectedAuthor);
        List<Author> authorsListActual = authorsApi.getAllAuthors();
        Author foundInList = authorsListActual.stream()
                .filter(a -> a.getId().equals(expectedAuthor.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Author not found in list after creation"));

        Assertions.assertThat(foundInList)
                .as("The author fields inside the list should be match.")
                .isEqualTo(expectedAuthor);
    }

    @Test(groups = "regression")
    @Description("Verify existing author is presented in authors list.")
    public void verifyExistingAuthorExistsInAllAuthor() {
        Author expectedAuthor = Author.builder()
                .id(200)
                .idBook(65)
                .firstName("First Name 200")
                .lastName("Last Name 200")
                .build();
        List<Author> authorsListActual = authorsApi.getAllAuthors();
        Author foundInList = authorsListActual.stream()
                .filter(a -> a.getId().equals(expectedAuthor.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Author not found in list after creation"));

        Assertions.assertThat(foundInList)
                .as("The author fields inside the list should be match.")
                .isEqualTo(expectedAuthor);
    }

}
