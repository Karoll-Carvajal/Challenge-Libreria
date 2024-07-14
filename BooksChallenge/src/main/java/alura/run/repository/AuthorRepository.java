package alura.run.repository;

import alura.run.model.Author;
import alura.run.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAll();
}
