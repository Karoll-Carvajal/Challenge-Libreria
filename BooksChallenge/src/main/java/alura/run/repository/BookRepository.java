package alura.run.repository;

import alura.run.model.Book;
import alura.run.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
      List<Book> findByTitleContainingIgnoreCase(String title);
      List<Book> findByLanguages(Languages language);
}
