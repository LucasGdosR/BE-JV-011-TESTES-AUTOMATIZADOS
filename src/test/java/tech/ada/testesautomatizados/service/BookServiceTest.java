package tech.ada.testesautomatizados.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.ada.testesautomatizados.model.Book;
import tech.ada.testesautomatizados.repository.BookRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;
    @InjectMocks
    private BookService service;
    private Book mockBook1;
    private Book mockBook2;

    @BeforeEach
    void setUp() {
        mockBook1 = new Book();
        mockBook1.setIsbn("123-456");
        mockBook1.setTitle("Mock Book");
        mockBook1.setResumo("Resumo do livro");
        mockBook1.setPrice(BigDecimal.valueOf(20.00));
        mockBook1.setNumberOfPages(100);

        mockBook2 = new Book();
        mockBook2.setIsbn("123-456-2");
        mockBook2.setTitle("Mock Book 2");
        mockBook2.setResumo("Resumo do livro 2");
        mockBook2.setPrice(BigDecimal.valueOf(22.22));
        mockBook2.setNumberOfPages(102);
    }

    /*
    GET -> findAll
     */
    @Test
    void shouldFindAllBooksInRepository() {
        List<Book> expectedBooks = List.of(mockBook1, mockBook2);
        when(repository.findAll()).thenReturn(expectedBooks);

        List<Book> receivedBooks = service.findAll();

        assertEquals(mockBook1, receivedBooks.get(0));
        assertEquals(mockBook2, receivedBooks.get(1));
    }

    @Test
    void shouldFindEmptyListIfRepositoryIsEmpty() {
        List<Book> receivedBooks = service.findAll();

        assertNotNull(receivedBooks);
        assertTrue(receivedBooks.isEmpty());
        assertDoesNotThrow(() -> service.findAll());
    }

    /*
    GET /{id} -> findById()
     */
    @Test
    void shouldFindBookById() {
        when(repository.findById("123-456")).thenReturn(Optional.of(mockBook1));
        when(repository.findById("123-456-2")).thenReturn(Optional.of(mockBook2));

        Book receivedBook1 = service.findById("123-456");
        Book receivedBook2 = service.findById("123-456-2");

        assertEquals(mockBook1, receivedBook1);
        assertEquals(mockBook2, receivedBook2);
    }

    @Test
    void shouldThrowExceptionWhenSearchingForInexistentId() {
        assertThrows(ResponseStatusException.class, () -> service.findById("foo"));
    }

    /*
    DELETE -> deleteById()
     */
    @Test
    void shouldThrowExceptionWhenDeletingInexistentId() {
        assertThrows(ResponseStatusException.class, () -> service.deleteById("foo"));
    }

    /*
    POST -> save()
     */
    @Test
    void shouldReturnSavedBook() {
        when(repository.save(mockBook1)).thenReturn(mockBook1);
        assertEquals(mockBook1, service.save(mockBook1));
    }

    @Test
    void shouldThrowExceptionIfIdAlreadyExists() {
        when(repository.findById(mockBook1.getIsbn())).thenReturn(Optional.of(mockBook1));
        assertThrows(ResponseStatusException.class, () -> service.save(mockBook1));
    }

    /*
    PUT -> editById(id, updatedBook)
     */
    @Test
    void shouldReturnUpdatedBookWithPathId() {
        when(repository.save(mockBook1)).thenReturn(mockBook1);
        when(repository.findById(mockBook1.getIsbn())).thenReturn(Optional.of(mockBook1));

        assertEquals(mockBook1, service.editById(mockBook1.getIsbn(), mockBook1));
    }

    @Test
    void shouldThrowExcecptionIfInexistentId() {
        when(repository.findById(mockBook1.getIsbn())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.editById(mockBook1.getIsbn(), mockBook1));
    }
}