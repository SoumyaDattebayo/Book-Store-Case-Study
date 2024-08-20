package com.bookstore.book_service.service;

import com.bookstore.book_service.dataModel.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();
    Book getBookById(long id);
    Book saveBook(Book book);
    void deleteBookById(long id);
    Book updateBookById(long id, Book book);
}
