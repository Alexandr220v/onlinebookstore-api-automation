package api;


import com.google.gson.Gson;
import dto.Book;
import dto.ErrorResponse;
import io.qameta.allure.Step;
import org.apache.http.HttpStatus;

import java.util.List;

public class BooksApi extends BaseApi {

    private String BOOKS = "/Books";
    private String BOOK_ID = BOOKS + "/%s";

    @Step("Fetch list of all books")
    public List<Book> getBooksList() {
        return get(BOOKS, HttpStatus.SC_OK).asList(Book.class);
    }

    @Step("Get book details for ID: {id}")
    public Book getBook(int id) {
        return get(String.format(BOOK_ID, id), HttpStatus.SC_OK).as(Book.class);
    }

    @Step("Attempt to get non-existent book ID: {id}")
    public ErrorResponse getBookError(int id) {
        return get(String.format(BOOK_ID, id), HttpStatus.SC_NOT_FOUND).as(ErrorResponse.class);
    }

    @Step("Create a new book: {book}")
    public Book createBook(Book book) {
        return post(BOOKS, new Gson().toJson(book), HttpStatus.SC_OK).as(Book.class);
    }

    @Step("Create book and expect error: {book}")
    public ErrorResponse createBookError(Book book) {
        return post(BOOKS, new Gson().toJson(book), HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    @Step("Update book details for ID: {book.id}")
    public Book updateBook(Book book) {
        return put(String.format(BOOK_ID, book.getId()), new Gson().toJson(book), HttpStatus.SC_OK).as(Book.class);
    }

    @Step("Update book and expect error: {book}")
    public ErrorResponse updateBookError(Book book) {
        return put(String.format(BOOK_ID, book.getId()), new Gson().toJson(book), HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    @Step("Delete book with ID: {id}")
    public ResponseData deleteBook(int id) {
        return delete(String.format(BOOK_ID, id), HttpStatus.SC_OK);
    }

}
