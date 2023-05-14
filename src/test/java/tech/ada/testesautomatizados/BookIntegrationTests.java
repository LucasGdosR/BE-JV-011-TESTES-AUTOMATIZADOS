package tech.ada.testesautomatizados;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import tech.ada.testesautomatizados.model.Book;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should add a book, and then delete it.")
    void shouldSaveThenDelete() throws Exception {
        Book book = new Book();
        String id = "123-456";
        book.setIsbn(id);
        book.setTitle("Mock Book");
        book.setResumo("Resumo do livro");
        book.setPrice(BigDecimal.valueOf(20.00));
        book.setNumberOfPages(100);

        String bookJson = mapper.writeValueAsString(book);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().json(bookJson))
                        .andExpect(redirectedUrl("http://localhost/api/books/"+id));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("["+bookJson+"]"));

        mockMvc.perform(delete("/api/books/"+id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    @DisplayName("Should add a book, and then edit it.")
    void shouldSaveThenEdit() throws Exception {
        Book book = new Book();
        Book book2;
        book2 = new Book();

        String id = "123-456";

        book.setIsbn(id);
        book.setTitle("Mock Book");
        book.setResumo("Resumo do livro");
        book.setPrice(BigDecimal.valueOf(20.00));
        book.setNumberOfPages(100);

        book2.setTitle("Mock Book 2");
        book2.setResumo("Resumo do livro 2");
        book2.setPrice(BigDecimal.valueOf(20.00));
        book2.setNumberOfPages(100);

        String bookJson = mapper.writeValueAsString(book);
        String bookJson2 = mapper.writeValueAsString(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(bookJson))
                .andExpect(redirectedUrl("http://localhost/api/books/"+id));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("["+bookJson+"]"));

        mockMvc.perform(put("/api/books/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson2))
                .andExpect(status().isOk());

       mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("["+bookJson2+"]"));
    }
}
