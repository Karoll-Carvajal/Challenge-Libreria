package alura.run.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorsData(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") String birth_year,
        @JsonAlias("death_year") String death_year
) {
}
