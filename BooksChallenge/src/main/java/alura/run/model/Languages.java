package alura.run.model;

public enum Languages {
    en("en"),
    es("es"),
    fr("fr");

    private String languagesOmdb;

    Languages(String languagesOmdb) {
        this.languagesOmdb = languagesOmdb;
    }

    public String getLanguagesOmdb() {
        return languagesOmdb;
    }

    public static Languages fromString(String text) {
        for (Languages language : Languages.values()) {
            if (language.languagesOmdb.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("No category found for: " + text);
    }

    @Override
    public String toString() {
        return "Languages{" +
                "languagesOmdb='" + languagesOmdb + '\'' +
                '}';
    }
}
