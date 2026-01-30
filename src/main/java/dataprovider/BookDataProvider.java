package dataprovider;

import dto.Book;
import org.testng.annotations.DataProvider;
import utils.DataUtils;
import utils.DateUtils;

import java.util.Random;


public class BookDataProvider {

    public static final String TITLE = "Clean Code";
    public static final String DESCRIPTION = "Clean Code in Java";
    public static final String EXCERPT = "Even bad code can work";
    public static final int PAGE_COUNT = new Random().nextInt(1000);

    @DataProvider(name = "validBookData")
    public Object[][] validBookData() {
        return new Object[][]{
                // 1. Title Empty
                {
                        Book.builder().id(DataUtils.uniqueId()).title("").publishDate(DateUtils.getCurrentDate()).build()
                },
                // 2. Description Empty
                {
                        Book.builder().id(DataUtils.uniqueId()).title(TITLE).description("").publishDate(DateUtils.getCurrentDate()).build()
                },
                // 3. Excerpt Empty
                {
                        Book.builder().id(DataUtils.uniqueId()).title(TITLE).excerpt("").publishDate(DateUtils.getCurrentDate()).build()
                }
        };
    }

    @DataProvider(name = "invalidBookData")
    public Object[][] invalidBookData() {
        return new Object[][]{
                // 1. Invalid Date
                {
                        Book.builder().id(DataUtils.uniqueId()).title(TITLE).publishDate("invalid date").build(),
                        "publishDate"
                },
                // 2. Negative ID
                {
                        Book.builder().id(-1).title(TITLE).publishDate(DateUtils.getCurrentDate()).build(),
                        "id"
                },
                // 3. Null ID
                {
                        Book.builder().title(TITLE).publishDate(DateUtils.getCurrentDate()).build(),
                        "id"
                },
                // 5. Page Count Negative
                {
                        Book.builder().id(DataUtils.uniqueId()).title(TITLE).pageCount(-50).publishDate(DateUtils.getCurrentDate()).build(),
                        "pageCount"
                },
                // 6. Publish Date in Future (Example: Year 2050)
                {
                        Book.builder().id(DataUtils.uniqueId()).title(TITLE).publishDate("2050-01-01T00:00:00Z").build(),
                        "publishDate"
                }
        };
    }

}
