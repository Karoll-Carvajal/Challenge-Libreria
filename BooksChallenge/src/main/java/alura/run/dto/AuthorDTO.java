package alura.run.dto;

public record AuthorDTO(
         Long id,
         String name,
         Integer birth_year,
         Integer death_year
) {
}
