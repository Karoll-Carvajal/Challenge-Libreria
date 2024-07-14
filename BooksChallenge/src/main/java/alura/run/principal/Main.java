            package alura.run.principal;

            import alura.run.model.Author;
            import alura.run.model.Book;
            import alura.run.model.Languages;
            import alura.run.repository.AuthorRepository;
            import alura.run.repository.BookRepository;
            import alura.run.service.BookService;
            import alura.run.service.ConsumptionAPI;
            import alura.run.service.ConvertData;
            import com.fasterxml.jackson.databind.JsonNode;
            import com.fasterxml.jackson.databind.ObjectMapper;
            import org.springframework.beans.factory.annotation.Autowired;

            import java.util.List;
            import java.util.Scanner;

            public class Main {
                //Declaration of variables
                private Scanner keyboard = new Scanner(System.in);
                private ConvertData convertData = new ConvertData();
                private ConsumptionAPI consumptionAPI = new ConsumptionAPI();
                private final String BASE_URL = "https://gutendex.com/books/";
                private final String KEY_API = "&apikey=4fc7c187";
                private ConvertData conversor = new ConvertData();
                private ObjectMapper mapper = new ObjectMapper();
                private Book book= new Book();
                private Author authors = new Author();
                private BookRepository bookRepository;
                private AuthorRepository authorRepository;
                @Autowired
                private BookService bookService;
                private String titles;
                //Method Constructor
                public Main(BookRepository bookRepository, BookService bookService, AuthorRepository authorRepository ) {
                    this.bookRepository = bookRepository;
                    this.bookService = bookService;
                    this.authorRepository= authorRepository;
                }

                //Method
                public void ShowMenu(){
                    var option= -1;
                    while (option != 0) {
                        var menu = """
                                *************************************************************************
                                1 - Mostrar Toda la infromación libro
                                2 - Listar Todos los libros
                                3 - Buscar Libro por titulo
                                4 - Mostrar libros por Idioma
                                5 - Listar Todos los autores
                                6 - Listar autores vivos en X año
                                0 - Salir
                                *************************************************************************
                                """;
                        System.out.println(menu);
                        option = keyboard.nextInt();
                        keyboard.nextLine();

                        switch (option) {
                            case 1:
                                System.out.println("Esta es la Informacion general");
                                listAllTheBooks();

                                break;
                            case 2:
                                listTheBooks();
                                break;
                            case 3:
                                System.out.print("Ingrese el título de la serie que desea buscar: ");
                                String titles = keyboard.nextLine().trim();
                                searchBookByTitle(titles);
                                break;
                            case 4:
                                System.out.print("Ingrese el lenguage a buscar es decir 'en', 'es', 'fr': ");
                                String lang = keyboard.nextLine().trim();
                                listBooksByLanguage(lang);
                                break;
                            case 5:
                                listAllTheAuthors();
                                break;
                            case 6:
                                listAllAuthors();
                                break;
                            case 0:
                                System.out.println("Close the aplication, seen you soon...");
                                break;
                            default:
                                System.out.println("Invalid Option ");
                        }



                        }
                    }

                public void  listAllTheBooks(){
                    try {
                        String json = consumptionAPI.obtenerDatos(BASE_URL);

                        JsonNode rootNode = mapper.readTree(json);
                        JsonNode authorsNode = rootNode.get("results");
                        if (authorsNode != null && authorsNode.isArray()) {
                            Book[] books = mapper.readValue(authorsNode.traverse(), Book[].class);
                            for (Book book : books) {
                                System.out.println(book.toString());
                                bookRepository.save(book);
                            }
                        } else {
                            System.out.println("No se encontró la lista de libros en el JSON.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al procesar los datos: " + e.getMessage());
                    }
                }


                public void listTheBooks() {
                    bookService.getAllTheBooks().forEach(book -> {
                        System.out.println("Titulo: " + book.getTitle());
                    });
                }

                //Search book by title
                private void  searchBookByTitle(String title){

                    try {
                        String json = consumptionAPI.obtenerDatos(BASE_URL);
                        JsonNode rootNode = mapper.readTree(json);
                        JsonNode authorsNode = rootNode.get("results");
                        if (authorsNode != null && authorsNode.isArray()) {
                            for (JsonNode bookNode : authorsNode) {
                                String bookTitle = bookNode.get("title").asText();
                                if (bookTitle.equalsIgnoreCase(title)) {

                                    Book book = mapper.treeToValue(bookNode, Book.class);
                                    System.out.println(book.toString());
                                    System.out.println(bookRepository);
                                    return;
                                }
                            }
                            System.out.println("No se encontró el libro con título: " + title);
                        } else {
                            System.out.println("No se encontró la lista de libros en el JSON.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al procesar los datos: " + e.getMessage());
                    }
                }

                public void listBooksByLanguage(String lang) {
                    try {
                        Languages language = Languages.fromString(lang);
                        List<Book> books = bookRepository.findByLanguages(language);
                        if (!books.isEmpty()) {
                            System.out.println("Libros en el idioma '" + lang + "':");
                            books.forEach(book -> System.out.println("Titulo: " + book.getTitle()));
                        } else {
                            System.out.println("No se encontraron libros en el idioma '" + lang + "'.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Idioma no válido. Use 'en', 'es' o 'fr'.");
                    }
                }

                public void  listAllTheAuthors(){
                    List<Author> authors = authorRepository.findAll();
                    if (!authors.isEmpty()) {
                        System.out.println("Lista de todos los autores:");
                        authors.forEach(author -> System.out.println("Author: " + author.getName()));
                    } else {
                        System.out.println("No se encontraron autores.");
                    }
                }

                public void listAllAuthors() {
                    System.out.print("Ingrese el año para filtrar autores vivos: ");
                    int year = keyboard.nextInt();
                    keyboard.nextLine();

                    List<Author> authors = authorRepository.findAll();
                    if (!authors.isEmpty()) {
                        System.out.println("Lista de autores vivos en el año " + year + ":");

                        for (Author author : authors) {
                            if (author.getBirth_year() != null && author.getDeath_year() != null) {
                                if (author.getBirth_year() <= year && year <= author.getDeath_year()) {
                                    System.out.println("Author: " + author.getName());
                                }
                            } else if (author.getBirth_year() != null && author.getDeath_year() == null) {
                                if (author.getBirth_year() <= year) {
                                    System.out.println("Author: " + author.getName());
                                }
                            } else if (author.getBirth_year() == null && author.getDeath_year() != null) {
                                if (year <= author.getDeath_year()) {
                                    System.out.println("Author: " + author.getName());
                                }
                            }
                        }
                    } else {
                        System.out.println("No se encontraron autores.");
                    }
                }

            }



