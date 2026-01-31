package dataprovider;

import dto.Author;
import org.testng.annotations.DataProvider;
import utils.DataUtils;

import java.util.Random;

import static utils.DataUtils.uniqueId;


public class AuthorDataProvider {
    public static final String DEFAULT_FIRST_NAME = "Agata";
    public static final String DEFAULT_LAST_NAME = "Crysty";
    public static final int ID_BOOK = new Random().nextInt(1000);

    public Author getBaseAuthor(int id) {
        return Author.builder()
                .id(id)
                .idBook(id)
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .build();
    }

    @DataProvider(name = "validAuthorData")

    public Object[][] validAuthorData() {
        return new Object[][]{
                // Names are NULL
                {Author.builder()
                        .id(uniqueId())
                        .idBook(uniqueId())
                        .build()
                },
                // idBook is Negative (might be validation)
                {Author.builder()
                        .id(uniqueId())
                        .idBook(-10)
                        .firstName(DEFAULT_FIRST_NAME)
                        .lastName(DEFAULT_LAST_NAME)
                        .build()
                },
                // id is Negative (might be validation)
                {Author.builder()
                        .id(-10)
                        .idBook(uniqueId())
                        .firstName(DEFAULT_FIRST_NAME)
                        .lastName(DEFAULT_LAST_NAME)
                        .build()
                },
        };
    }

    @DataProvider(name = "invalidAuthorData")
    public Object[][] invalidAuthorData() {
        return new Object[][]{

                // Id is  Null
                {Author.builder()
                        .idBook(ID_BOOK)
                        .firstName(DEFAULT_FIRST_NAME)
                        .lastName(DEFAULT_LAST_NAME)
                        .build(),
                        "id"},

                // 2. idBook is  Null
                {Author.builder()
                        .id(uniqueId())
                        .firstName(DEFAULT_FIRST_NAME)
                        .lastName(DEFAULT_LAST_NAME)
                        .build()
                        , "idBook"
                },
        };
    }
}
