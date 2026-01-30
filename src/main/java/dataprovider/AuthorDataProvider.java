package dataprovider;

import dto.Author;
import dto.Book;
import org.testng.annotations.DataProvider;
import utils.DataUtils;
import utils.DateUtils;

import java.util.Random;


public class AuthorDataProvider {
    public static final String FIRST_NAME = "Agata";
    public static final String LAST_NAME = "Crysty";
    public static final int ID_BOOK = new Random().nextInt(1000);


    @DataProvider(name = "validAuthorData")
    public Object[][] validAuthorData() {
        return new Object[][]{
                // Partial: Only names (assuming ID/idBook might be auto-generated or optional)
                { Author.builder().firstName("Agata").lastName("Crysty").build() },
                // Only First Name
                { Author.builder().firstName("Agata").build() }
        };
    }

    @DataProvider(name = "invalidAuthorData")
    public Object[][] invalidAuthorData() {
        return new Object[][]{
                // 1. Negative ID
                { Author.builder().id(-7857).firstName("Agata").lastName("Crysty").build(), "id" },

                // 2. Null ID
                { Author.builder().idBook(ID_BOOK).firstName("Agata").lastName("Crysty").build(), "id" },

                // 3. Negative idBook
                { Author.builder().id(7857).idBook(-6192).firstName("Agata").build(), "idBook" }
        };
        };

}
