package assertion;

import dto.ErrorResponse;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

import java.util.Optional;

public class Assertion {

    public static final String NEW_AUTHOR_FIELDS_SHOULD_MATCH = "New Author fields should match";
    public static final String UPDATED_AUTHOR_FIELDS_SHOULD_MATCH = "Updated Author fields should match";
    public static final String NEW_BOOK_FIELDS_SHOULD_MATCH = "New book fields should match";
    public static final String UPDATED_BOOK_FIELDS_SHOULD_MATCH = "Updated book fields should match";


    public void validateBadRequestError(String expectedErrorKey, ErrorResponse errorResponse) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(errorResponse.getTitle()).as("Response Title").isEqualTo("One or more validation errors occurred.");
        softly.assertThat(errorResponse.getStatus()).as("HTTP Status Code").isEqualTo(HttpStatus.SC_BAD_REQUEST);

        Optional.ofNullable(errorResponse.getErrors())
                .map(errors -> errors.get(expectedErrorKey))
                .ifPresent(errorList -> {
                    softly.assertThat(errorList)
                            .asList()
                            .anySatisfy(msg -> softly.assertThat(msg.toString())
                                    .contains(expectedErrorKey));
                    softly.assertAll();
                });
    }

    public  void validateNotFoundError(ErrorResponse errorResponse) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(errorResponse.getStatus()).as("HTTP Status Code").isEqualTo(HttpStatus.SC_NOT_FOUND);
        softly.assertThat(errorResponse.getTitle()).as("Error Title").containsIgnoringCase("Not Found");
        softly.assertAll();
    }


}
