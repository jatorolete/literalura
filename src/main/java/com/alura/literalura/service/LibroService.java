package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosRespuesta;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos conversor;

    public LibroService(LibroRepository libroRepository,
                        AutorRepository autorRepository,
                        ConsumoAPI consumoAPI,
                        ConvierteDatos conversor) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.consumoAPI = consumoAPI;
        this.conversor = conversor;
    }

    // MÉTODO NUEVO: buscar en API y guardar en BD
    public void buscarYGuardarLibro(String titulo) {

        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
        String json = consumoAPI.obtenerDatos(url);

        var datos = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (datos.results().isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
            return;
        }

        var libroAPI = datos.results().get(0);
        var autorAPI = libroAPI.authors().get(0);

        // Buscar si el autor ya existe
        Autor autorExistente = autorRepository
                .findAll()
                .stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(autorAPI.name()))
                .findFirst()
                .orElse(null);

        Autor autorEntidad;

        if (autorExistente != null) {
            autorEntidad = autorExistente;
        } else {
            autorEntidad = new Autor(
                    autorAPI.name(),
                    autorAPI.birth_year(),
                    autorAPI.death_year()
            );
            autorRepository.save(autorEntidad);
        }

        // Crear libro
        var libroEntidad = new Libro(
                libroAPI.title(),
                libroAPI.languages().isEmpty() ? "desconocido" : libroAPI.languages().get(0),
                libroAPI.download_count(),
                autorEntidad
        );

        libroRepository.save(libroEntidad);

        System.out.println("Libro guardado en la base de datos.");
    }

    public Libro guardarLibro(Libro libro) {

        Autor autor = libro.getAutor();

        Autor autorExistente = autorRepository
                .findAll()
                .stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(autor.getNombre()))
                .findFirst()
                .orElse(null);

        if (autorExistente != null) {
            libro.setAutor(autorExistente);
        } else {
            autorRepository.save(autor);
        }

        return libroRepository.save(libro);
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> obtenerAutoresVivosEn(int year) {

        var autoresConFallecimiento = autorRepository
                .findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(year, year);

        var autoresSinFallecimiento = autorRepository
                .findByNacimientoLessThanEqualAndFallecimientoIsNull(year);

        autoresConFallecimiento.addAll(autoresSinFallecimiento);

        return autoresConFallecimiento;
    }

    public long contarLibrosPorIdioma(String idioma) {
        return libroRepository.countByIdiomaIgnoreCase(idioma);
    }

    public void mostrarEstadisticasDeLibros() {
        var libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados para generar estadísticas.");
            return;
        }

        var stats = libros.stream()
                .mapToInt(Libro::getDescargas)
                .summaryStatistics();

        System.out.println("=== ESTADÍSTICAS DE DESCARGAS ===");
        System.out.println("Cantidad de libros: " + stats.getCount());
        System.out.println("Máximo de descargas: " + stats.getMax());
        System.out.println("Mínimo de descargas: " + stats.getMin());
        System.out.println("Promedio de descargas: " + stats.getAverage());
    }

    public void mostrarTop10Libros() {
        var top10 = libroRepository.findTop10ByOrderByDescargasDesc();

        if (top10.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("=== TOP 10 LIBROS MÁS DESCARGADOS ===");

        top10.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("------------------------");
        });
    }

    public List<Autor> buscarAutorPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Consultas adicionales por nacimiento/fallecimiento

    public List<Autor> autoresNacidosAntesDe(int year) {
        return autorRepository.findByNacimientoLessThan(year);
    }

    public List<Autor> autoresFallecidosDespuesDe(int year) {
        return autorRepository.findByFallecimientoGreaterThan(year);
    }

    public List<Autor> autoresNacidosEntre(int inicio, int fin) {
        return autorRepository.findByNacimientoBetween(inicio, fin);
    }
}