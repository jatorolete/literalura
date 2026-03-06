package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Autores con fallecimiento conocido
    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(int nacimiento, int fallecimiento);

    // Autores sin fallecimiento (posiblemente vivos)
    List<Autor> findByNacimientoLessThanEqualAndFallecimientoIsNull(int nacimiento);

    // Buscar autor por nombre
    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    // Consultas adicionales por nacimiento/fallecimiento
    List<Autor> findByNacimientoLessThan(int year);
    List<Autor> findByFallecimientoGreaterThan(int year);
    List<Autor> findByNacimientoBetween(int inicio, int fin);
}