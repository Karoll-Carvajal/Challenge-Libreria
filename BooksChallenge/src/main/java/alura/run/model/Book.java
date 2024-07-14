package alura.run.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name="book")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double download_count;
    @ElementCollection(targetClass = Languages.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    private List<Languages> languages;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @JsonProperty("authors")
    private List<Author> authors;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", download_count=" + download_count +
                ", languages=" + languages +
                ", authors=" + authors +
                '}';
    }
}
