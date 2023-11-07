package com.documentos.documentos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.documentos.documentos.Exception.ResourceNotFoundException;
import com.documentos.documentos.models.Documento;
import com.documentos.documentos.repositories.DocumentoRepository;

@Service
@Transactional
public class DocumentoService {
    private DocumentoRepository documentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    public Documento addDocumento(Documento documento) {
        return documentoRepository.save(documento);
    }

    public List<Documento> finAllDocumentos() {
        return documentoRepository.findAll();
    }

    public Documento findDocumentoById(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No se ha encontrado ningún documento con el id " + id));
    }

    public Documento updateDocumento(Documento documento) {
        return documentoRepository.save(documento);
    }

    public void deleteDocumento(Long id) {
        documentoRepository.deleteById(id);
    }
}
