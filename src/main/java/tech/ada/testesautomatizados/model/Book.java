package tech.ada.testesautomatizados.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    @NotBlank
    @Size(max = 500)
    private String resumo;
    private String summary;
    @NotNull
    @Min(20)
    private BigDecimal price;
    @NotNull
    @Min(100)
    private Integer numberOfPages;
    @Future
    private LocalDate publishingDate;
}
