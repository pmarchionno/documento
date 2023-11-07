package com.documentos.documentos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.documentos.documentos.models.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long>{
    List<Documento> findByTitle(String title);

    void deleteById(Long id);

    Optional<Documento> findById(Long id);
    
}
