package alura.run.service;

import alura.run.model.Book;
import alura.run.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllTheBooks() {
        return bookRepository.findAll();
    }

    public  List<Book>findBooksByTitle(String title){
        return  bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
