package alura.run.dto;

import alura.run.model.Author;
import alura.run.model.Languages;


import java.util.List;

public record BookDTO(
         Long id,
         String title,
         Double download_count,
         List<Languages>languages,
        List<Author> authors
) {
}
