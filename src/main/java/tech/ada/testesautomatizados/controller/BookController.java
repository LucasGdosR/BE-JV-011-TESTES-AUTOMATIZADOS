package tech.ada.testesautomatizados.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tech.ada.testesautomatizados.model.Book;
import tech.ada.testesautomatizados.service.BookService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody @Valid Book book, UriComponentsBuilder uriBuilder) {

        URI uri = uriBuilder
                .path("api/books/{id}")
                .buildAndExpand(book.getIsbn())
                .toUri();

        return ResponseEntity.created(uri).body(service.save(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> editById(@PathVariable String id,
                                         @RequestBody @Valid Book book) {

        return ResponseEntity.ok(service.editById(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
