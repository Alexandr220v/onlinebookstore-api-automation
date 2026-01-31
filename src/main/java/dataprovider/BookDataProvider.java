package dataprovider;

import dto.Author;
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

    public static Book getBaseBook(int id) {
        return Book.builder()
                .id(id)
                .title(TITLE)
                .description(DESCRIPTION)
                .pageCount(PAGE_COUNT)
                .excerpt(EXCERPT)
                .publishDate(DateUtils.getCurrentDate())
                .build();
    }

    @DataProvider(name = "validBookData")
    public Object[][] validBookData() {
        return new Object[][]{
                // 1. Title is NULL
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .excerpt(EXCERPT)
                                .publishDate(DateUtils.getCurrentDate())
                                .build()
                },
                // 2. Description  is NULL
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .title(TITLE)
                                .pageCount(PAGE_COUNT)
                                .excerpt(EXCERPT)
                                .publishDate(DateUtils.getCurrentDate())
                                .build()
                },
                // 3. Excerpt is NULL
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .title(TITLE)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .publishDate(DateUtils.getCurrentDate())
                                .build()
                }
                ,
                // 4. PublishDate is NULL
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .title(TITLE)
                                .excerpt(EXCERPT)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .build()
                }
                ,
                // 5. Publish Date in Future (Example: Year 2050)
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .title(TITLE)
                                .excerpt(EXCERPT)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .publishDate("2050-01-01T00:00:00Z")
                                .build()
                }
                ,
                // 5. Publish Date in Past (Example: Year 1050)
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .title(TITLE)
                                .excerpt(EXCERPT)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .publishDate("1050-01-01T00:00:00Z")
                                .build()
                }
        };
    }

    @DataProvider(name = "invalidBookData")
    public Object[][] invalidBookData() {
        return new Object[][]{
                // Invalid Date format
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .title(TITLE)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .excerpt(EXCERPT)
                                .publishDate("invalid date")
                                .build(),
                        "publishDate"
                },
                // Invalid Date without Z
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .title(TITLE)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .excerpt(EXCERPT)
                                .publishDate("2050-01-01T00:00:00")
                                .build(),
                        "publishDate"
                },
                //Invalid Date 31 February
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .title(TITLE)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .excerpt(EXCERPT)
                                .publishDate("2026-02-31T00:00:00Z").build(),
                        "publishDate"
                },
                //Invalid Date 31 MOnth
                {
                        Book.builder()
                                .id(DataUtils.uniqueId())
                                .title(TITLE)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .excerpt(EXCERPT)
                                .publishDate("2026-31-31T00:00:00Z")
                                .build(),
                        "publishDate"
                },
                // Invalid Date missing T
                {
                        Book.builder().id(DataUtils.uniqueId())
                                .title(TITLE)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .excerpt(EXCERPT)
                                .publishDate("2050-01-01 00:00:00Z")
                                .build(),
                        "publishDate"
                },
                // pageCount is NULL
                {
                        Book.builder().id(DataUtils.uniqueId())
                                .title(TITLE)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .excerpt(EXCERPT)
                                .publishDate("2050-01-01 00:00:00Z")
                                .build(),
                        "publishDate"
                },
                // id is NULL
                {
                        Book.builder()
                                .title(TITLE)
                                .description(DESCRIPTION)
                                .pageCount(PAGE_COUNT)
                                .excerpt(EXCERPT)
                                .publishDate(DateUtils.getCurrentDate())
                                .build(),
                        "id"
                }
        };
    }

}
