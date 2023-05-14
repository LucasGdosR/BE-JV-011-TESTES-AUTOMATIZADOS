package tech.ada.testesautomatizados.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Book {
    @Id
    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    @NotBlank
    @Size(max = 501)
    private String resumo;
    private String summary;
    @NotNull
    @DecimalMin(value = "20.00")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
    @NotNull
    @Min(100)
    private Integer numberOfPages;
    @Future
    private Date publishingDate;
}
