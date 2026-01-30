package tests.authors;

import api.AuthorsApi;
import api.BooksApi;
import com.google.inject.Inject;
import config.ApiConfig;
import dto.Author;
import dto.Book;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.DataUtils;
import utils.DateUtils;

import java.util.List;

import static dataprovider.AuthorDataProvider.FIRST_NAME;
import static dataprovider.AuthorDataProvider.LAST_NAME;


public class GetAuthorsTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify get author by id.")
    public void verifyGetAuthorById() {
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
        Author getAuthorActual = authorsApi.getAuthor(uniqueId);
        Assertions.assertThat(getAuthorActual)
                .as("New Author fields should match")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test(groups = "regression")
    @Description("Verify author exists in authors list.")
    public void verifyGreatedAuthorExistsInAllAuthor() {
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
        List<Author> authorsListActual = authorsApi.getAllAuthors();
        Author foundInList = authorsListActual.stream()
                .filter(a -> a.getId().equals(expectedAuthor.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Author not found in list after creation"));

        Assertions.assertThat(foundInList)
                .as("The author object inside the list should match exactly")
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

}
