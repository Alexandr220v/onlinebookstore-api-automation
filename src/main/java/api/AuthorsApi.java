package api;

import dto.Author;
import dto.Book;
import dto.ErrorResponse;
import io.qameta.allure.Step;
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

    @Step("Get all authors")
    public List<Author> getAllAuthors() {
        return get(AUTHORS, HttpStatus.SC_OK).asList(Author.class);
    }

    @Step("Get author by ID: {id}")
    public Author getAuthor(int id) {
        return get(String.format(AUTHOR_ID, id), HttpStatus.SC_OK).as(Author.class);
    }

    @Step("Attempt to get author by ID: {id} (expecting error)")
    public ErrorResponse getAuthorError(int id) {
        return get(String.format(AUTHOR_ID, id), HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    @Step("Create new author: {author}")
    public Author createAuthor(Author author) {
        return post(AUTHORS, author, HttpStatus.SC_OK).as(Author.class);
    }

    @Step("Attempt to create author (expecting 400 Bad Request): {author}")
    public ErrorResponse createAuthorError(Author author) {
        return post(AUTHORS, author, HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    @Step("Create author and expect status code {statusCode}")
    public ResponseData createAuthorError(Author author, int statusCode) {
        return post(AUTHORS, author, statusCode);
    }

    @Step("Update author: {author}")
    public Author updateAuthor(Author author) {
        return put(String.format(AUTHOR_ID, author.getId()), author, HttpStatus.SC_OK).as(Author.class);
    }
    @Step("Update author error: {author}")
    public ErrorResponse updateAuthorError(Author author) {
        return put(String.format(AUTHOR_ID, author.getId()),author, HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    @Step("Delete author with ID: {id}")
    public ResponseData deleteAuthor(int id) {
        return delete(String.format(AUTHOR_ID, id), HttpStatus.SC_OK);
    }

    @Step("Delete author with ID: {id}")
    public ErrorResponse deleteAuthorError(int id) {
        return delete(String.format(AUTHOR_ID, id), HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    public ResponseData updateAuthorError(Author author, int statusCode) {
        return put(String.format(AUTHOR_ID, author.getId()),author, statusCode);
    }
}
