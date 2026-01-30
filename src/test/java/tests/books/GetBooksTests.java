package tests.books;

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

import static dataprovider.BookDataProvider.*;
import static dataprovider.BookDataProvider.EXCERPT;

public class GetBooksTests extends BaseTest {


    @Test(groups = "regression")
    @Description("Verify created new book exists in books list.")
    public void verifyGreatedBookExistsInAllBookslist() {
        int uniqueId = DataUtils.uniqueId();
        Book newBookExpected = Book.builder()
                .id(uniqueId)
                .title(TITLE)
                .description(DESCRIPTION)
                .pageCount(PAGE_COUNT)
                .excerpt(EXCERPT)
                .publishDate(DateUtils.getCurrentDate())
                .build();
        Book newBookActual = booksApi.createBook(newBookExpected);
        Assertions.assertThat(newBookActual)
                .as("New book fields should match")
                .usingRecursiveComparison()
                .isEqualTo(newBookExpected);
        List<Book> bookListActual = booksApi.getBooksList();
        Book foundInList = bookListActual.stream()
                .filter(a -> a.getId().equals(newBookExpected.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Book not found in list after creation"));

        Assertions.assertThat(foundInList)
                .as("The Book object inside the list should match exactly")
                .usingRecursiveComparison()
                .isEqualTo(newBookExpected);
    }
}
