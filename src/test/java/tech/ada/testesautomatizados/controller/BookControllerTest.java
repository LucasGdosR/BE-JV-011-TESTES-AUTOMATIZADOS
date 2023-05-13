package tech.ada.testesautomatizados.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import tech.ada.testesautomatizados.model.Book;
import tech.ada.testesautomatizados.service.BookService;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController controller;
    @Mock
    private BookService service;
    @Autowired
    private MockMvc mockMvc;
    private Book mockBook1;
    private Book mockBook2;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

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
    GET -> findAll()
     */
    @Test
    void shouldListAllBooks() throws Exception {
        List<Book> expectedBooks = List.of(mockBook1, mockBook2);
        Mockito.when(service.findAll()).thenReturn(expectedBooks);

        mockMvc.perform(get("/api/books"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].isbn").value("123-456"))
                        .andExpect(jsonPath("$[1].title").value("Mock Book 2"));
    }

    @Test
    void shouldListNoBooksIfRepositoryIsEmpty() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    /*
    GET /{id} -> findById()
     */
    @Test
    void shouldFindBookById() throws Exception {
        Mockito.when(service.findById("123-456")).thenReturn(mockBook1);
        Mockito.when(service.findById("123-456-2")).thenReturn(mockBook2);

        mockMvc.perform(get("/api/books/123-456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("123-456"));

        mockMvc.perform(get("/api/books/123-456-2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("123-456-2"));
    }

    @Test
    void shouldReturn404WhenSearchingForInexistentId() throws Exception {
        Mockito.when(service.findById("foo")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ISBN."));

        mockMvc.perform(get("/api/books/foo"))
                .andExpect(status().isNotFound());
    }

    /*
    DELETE -> deleteById()
     */
    @Test
    void shouldReturn204WhenDeletingExistentId() throws Exception {
        mockMvc.perform(delete("/api/books/123-456"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeletingInexistentId() throws Exception {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ISBN.")).when(service).deleteById("foo");

        mockMvc.perform(delete("/api/books/foo"))
                .andExpect(status().isNotFound());
    }

}