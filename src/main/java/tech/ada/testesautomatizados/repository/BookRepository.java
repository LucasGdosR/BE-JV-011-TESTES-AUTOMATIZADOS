package tech.ada.testesautomatizados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.testesautomatizados.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
