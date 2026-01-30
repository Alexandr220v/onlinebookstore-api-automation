package api;


import com.google.gson.Gson;
import dto.Book;
import dto.ErrorResponse;
import org.apache.http.HttpStatus;

import java.util.List;

public class BooksApi extends BaseApi {

    private String BOOKS = "/Books";
    private String BOOK_ID = BOOKS + "/%s";

    public List<Book> getBooksList() {
        return get(BOOKS, HttpStatus.SC_OK).asList(Book.class);
    }

    public Book getBook(int id) {
        return get(String.format(BOOK_ID, id), HttpStatus.SC_OK).as(Book.class);
    }

    public ErrorResponse getBookError(int id) {
        return get(String.format(BOOK_ID, id), HttpStatus.SC_NOT_FOUND).as(ErrorResponse.class);
    }

    public Book createBook(Book book) {
        return post(BOOKS,new Gson().toJson(book), HttpStatus.SC_OK).as(Book.class);
    }

    public ErrorResponse createBookError(Book book) {
        return post(BOOKS,new Gson().toJson(book), HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    public Book updateBook(Book book) {
        return put(String.format(BOOK_ID, book.getId()),new Gson().toJson(book), HttpStatus.SC_OK).as(Book.class);
    }

    public ErrorResponse updateBookError(Book book) {
        return put(String.format(BOOK_ID, book.getId()),new Gson().toJson(book), HttpStatus.SC_BAD_REQUEST).as(ErrorResponse.class);
    }

    public ResponseData deleteBook(int id) {
        return delete(String.format(BOOK_ID, id), HttpStatus.SC_OK);
    }

}
