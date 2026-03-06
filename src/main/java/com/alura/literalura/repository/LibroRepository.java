package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    long countByIdiomaIgnoreCase(String idioma);

    // NUEVO: Top 10 libros más descargados
    List<Libro> findTop10ByOrderByDescargasDesc();

}