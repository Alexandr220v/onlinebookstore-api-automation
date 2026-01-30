package api;

import dto.Author;
import dto.Book;
import dto.ErrorResponse;
import io.restassured.RestAssured;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.put;

public class AuthorsApi  extends  BaseApi{

    private String AUTHORS = "/Authors";

    private String AUTHOR_ID = AUTHORS + "/%s";

    public List<Author> getAllAuthors() {
        return get(AUTHORS, HttpStatus.SC_OK).asList(Author.class);
    }

    public Author getAuthor(int id) {
        return get(String.format(AUTHOR_ID, id), HttpStatus.SC_OK).as(Author.class);
    }

    public ErrorResponse getAuthorError(int id) {
        return get(String.format(AUTHOR_ID, id), HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }
    public Author createAuthor(Author author) {
        return post(AUTHORS,author, HttpStatus.SC_OK).as(Author.class);
    }

    public ErrorResponse createAuthorError(Author author) {
        return post(AUTHORS,author, HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    public ResponseData createAuthorError(Author author, int statusCode) {
        return post(AUTHORS,author, statusCode);
    }

    public Author updateAuthor(Author author) {
        return put(String.format(AUTHOR_ID, author.getId()),author, HttpStatus.SC_OK).as(Author.class);
    }

    public ErrorResponse updateAuthorError(Author author) {
        return put(String.format(AUTHOR_ID, author.getId()),author, HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    public ResponseData updateAuthorError(Author author, int statusCode) {
        return put(String.format(AUTHOR_ID, author.getId()),author, statusCode);
    }

    public ResponseData deleteAuthor(int id) {
        return delete(String.format(AUTHOR_ID, id), HttpStatus.SC_OK);
    }

}
