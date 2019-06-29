package com.zxz.ebook.serviceimpl;

import com.zxz.ebook.dao.BookimageDao;
import com.zxz.ebook.entity.Book;
import com.zxz.ebook.service.BookService;
import com.zxz.ebook.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookimageDao bookImageDao;

    @Override
    public List<Book> lookAllBook() {
        List<Book> books=bookDao.findAll();
        for (Book book:books) {
            book.setBookimage(bookImageDao.findByBookid(book.getId()).getImage());
        }
        return books;
    }

    @Override
    public List<Book> lookBookLike(String name) {
        return bookDao.findByNameContaining(name);
    }

    @Override
    public Book lookBook(int id) {
        return bookDao.findById(id);
    }


}
