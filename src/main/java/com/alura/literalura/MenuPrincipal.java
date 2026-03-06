package com.alura.literalura;

import com.alura.literalura.service.LibroService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuPrincipal {

    private final Scanner teclado = new Scanner(System.in);
    private final LibroService libroService;

    public MenuPrincipal(LibroService libroService) {
        this.libroService = libroService;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    ==========================
                    LITERALURA - MENÚ PRINCIPAL
                    ==========================
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores de los libros registrados
                    5 - Listar autores vivos en un año específico
                    6 - Mostrar cantidad de libros por idioma
                    7 - Mostrar estadísticas de descargas
                    8 - Mostrar Top 10 libros más descargados
                    9 - Buscar autor por nombre
                    10 - Listar autores nacidos antes de un año
                    11 - Listar autores fallecidos después de un año
                    12 - Listar autores nacidos entre dos años
                    0 - Salir
                    """);

            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Intenta nuevamente.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresDeLibrosRegistrados();
                case 5 -> listarAutoresVivosEnAno();
                case 6 -> mostrarCantidadLibrosPorIdioma();
                case 7 -> libroService.mostrarEstadisticasDeLibros();
                case 8 -> libroService.mostrarTop10Libros();
                case 9 -> buscarAutorPorNombre();
                case 10 -> listarAutoresNacidosAntesDe();
                case 11 -> listarAutoresFallecidosDespuesDe();
                case 12 -> listarAutoresNacidosEntre();
                case 0 -> System.out.println("Saliendo de la aplicación...");
                default -> System.out.println("Opción inválida. Intenta nuevamente.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Ingresa el título a buscar: ");
        String titulo = teclado.nextLine();

        libroService.buscarYGuardarLibro(titulo);
    }

    private void listarLibrosRegistrados() {
        var libros = libroService.obtenerTodosLosLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        System.out.println("=== LIBROS REGISTRADOS ===");

        libros.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("------------------------");
        });
    }

    private void listarAutoresRegistrados() {
        var autores = libroService.obtenerTodosLosAutores();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }

        System.out.println("=== AUTORES REGISTRADOS ===");

        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getNacimiento());
            System.out.println("Fallecimiento: " + autor.getFallecimiento());
            System.out.println("------------------------");
        });
    }

    private void listarAutoresDeLibrosRegistrados() {
        var libros = libroService.obtenerTodosLosLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        System.out.println("=== AUTORES DE LOS LIBROS REGISTRADOS ===");

        libros.forEach(libro -> {
            var autor = libro.getAutor();
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getNacimiento());
            System.out.println("Fallecimiento: " + autor.getFallecimiento());
            System.out.println("Libro: " + libro.getTitulo());
            System.out.println("------------------------");
        });
    }

    private void listarAutoresVivosEnAno() {
        System.out.print("Ingresa un año para buscar autores vivos: ");
        int year = Integer.parseInt(teclado.nextLine());

        var autores = libroService.obtenerAutoresVivosEn(year);

        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en ese año según los registros.");
            return;
        }

        System.out.println("=== AUTORES VIVOS EN " + year + " ===");

        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getNacimiento());
            System.out.println("Fallecimiento: " + autor.getFallecimiento());
            System.out.println("------------------------");
        });
    }

    private void mostrarCantidadLibrosPorIdioma() {
        System.out.print("Ingresa el idioma (ej: en, es, fr): ");
        String idioma = teclado.nextLine().trim().toLowerCase();

        long cantidad = libroService.contarLibrosPorIdioma(idioma);

        System.out.println("Cantidad de libros en idioma '" + idioma + "': " + cantidad);
    }

    private void buscarAutorPorNombre() {
        System.out.print("Ingresa el nombre del autor: ");
        String nombre = teclado.nextLine();

        var autores = libroService.buscarAutorPorNombre(nombre);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores con ese nombre.");
            return;
        }

        System.out.println("=== AUTORES ENCONTRADOS ===");

        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getNacimiento());
            System.out.println("Fallecimiento: " + autor.getFallecimiento());
            System.out.println("------------------------");
        });
    }

    //Autores nacidos antes de cierto año
    private void listarAutoresNacidosAntesDe() {
        System.out.print("Ingresa un año: ");
        int year = Integer.parseInt(teclado.nextLine());

        var autores = libroService.autoresNacidosAntesDe(year);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores nacidos antes de " + year);
            return;
        }

        System.out.println("=== AUTORES NACIDOS ANTES DE " + year + " ===");

        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getNacimiento());
            System.out.println("Fallecimiento: " + autor.getFallecimiento());
            System.out.println("------------------------");
        });
    }

    //Autores fallecidos después de cierto año
    private void listarAutoresFallecidosDespuesDe() {
        System.out.print("Ingresa un año: ");
        int year = Integer.parseInt(teclado.nextLine());

        var autores = libroService.autoresFallecidosDespuesDe(year);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores fallecidos después de " + year);
            return;
        }

        System.out.println("=== AUTORES FALLECIDOS DESPUÉS DE " + year + " ===");

        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getNacimiento());
            System.out.println("Fallecimiento: " + autor.getFallecimiento());
            System.out.println("------------------------");
        });
    }

    //Autores nacidos entre dos años
    private void listarAutoresNacidosEntre() {
        System.out.print("Ingresa el año inicial: ");
        int inicio = Integer.parseInt(teclado.nextLine());

        System.out.print("Ingresa el año final: ");
        int fin = Integer.parseInt(teclado.nextLine());

        var autores = libroService.autoresNacidosEntre(inicio, fin);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores nacidos entre " + inicio + " y " + fin);
            return;
        }

        System.out.println("=== AUTORES NACIDOS ENTRE " + inicio + " Y " + fin + " ===");

        autores.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getNacimiento());
            System.out.println("Fallecimiento: " + autor.getFallecimiento());
            System.out.println("------------------------");
        });
    }

}