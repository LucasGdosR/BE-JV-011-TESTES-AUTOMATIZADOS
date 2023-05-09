package tech.ada.testesautomatizados.controller;

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
    public ResponseEntity<Book> save(@RequestBody Book book, UriComponentsBuilder uriBuilder) {

        URI uri = uriBuilder
                .path("api/usuarios/{id}")
                .buildAndExpand(book.getIsbn())
                .toUri();

        return ResponseEntity.created(uri).body(service.save(book));
    }
}
