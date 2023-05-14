package tech.ada.testesautomatizados.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.ada.testesautomatizados.model.Book;
import tech.ada.testesautomatizados.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Invalid ISBN."));
    }

    public Book save(Book book) {
        repository.findById(book.getIsbn()).ifPresent(e -> {throw new ResponseStatusException(
                HttpStatus.CONFLICT, "ISBN already exists.");});

        return repository.save(book);
    }

    public Book editById(String id, Book updatedBook) throws ResponseStatusException {
        findById(id);
        updatedBook.setIsbn(id);
        return repository.save(updatedBook);
    }

    public void deleteById(String id) throws ResponseStatusException {
        findById(id);
        repository.deleteById(id);
    }
}
