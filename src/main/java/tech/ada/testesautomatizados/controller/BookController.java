package tech.ada.testesautomatizados.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.testesautomatizados.model.Book;
import tech.ada.testesautomatizados.service.BookService;

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
}
