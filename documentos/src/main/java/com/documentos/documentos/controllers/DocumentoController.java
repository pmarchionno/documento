package com.documentos.documentos.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.documentos.documentos.Exception.ResourceNotFoundException;
import com.documentos.documentos.models.Documento;
import com.documentos.documentos.repositories.DocumentoRepository;
import com.documentos.documentos.request.DocumentoRequest;
import com.documentos.documentos.utils.FileUploadUtil;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentoController {
    private DocumentoRepository documentoRepository;

    public DocumentoController(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    String uploadDir = "documentos";

    @GetMapping("/documentos/file/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        // Define la ruta completa de la imagen en tu sistema de archivos
        Path filePath = Paths.get(uploadDir, fileName);
        // Verifica si el archivo existe
        if (Files.exists(filePath)) {
            try {
                // Carga el archivo en un recurso
                Resource resource = new UrlResource(filePath.toUri());

                // Devuelve la imagen como respuesta
                return ResponseEntity.ok()
                        // .contentType(MediaType.IMAGE_JPEG) // Cambia el tipo de contenido según el
                        // formato de tus imágenes
                        .body(resource);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/documentos/{id}")
    public ResponseEntity<Documento> obtenerDocumentoPorId(@PathVariable Long id) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el documento con el ID :" + id));
        return ResponseEntity.ok(documento);

    }

    @GetMapping("/documentos")
    public List<Documento> listarTodosLosDocumentos() {
        return documentoRepository.findAll();
    }

    @PostMapping("/documentos")
    public Documento guardarDocumento(@RequestParam("title") String title,
    @RequestParam("description") String description,
    @RequestParam("docFile") MultipartFile multipartFile) throws IOException {

    // public Documento guardarDocumento(@RequestBody DocumentoRequest request) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Documento documento = new Documento(null, title, description, fileName);
        // Establacecemos la imagen
        documento.setFileName(fileName);
        System.out.println(documento.getFileName());

        // Guardamos el docFile
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return documentoRepository.save(documento);
    }

    @PutMapping("/documentos/{id}")
    public ResponseEntity<Documento> actualizarDocumentoPorId(@PathVariable Long id,
            @RequestBody Documento data) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el documento con el ID :" + id));
        documento.setTitle(data.getTitle());
        documento.setDescription(data.getDescription());

        Documento documentoActualizado = documentoRepository.save(documento);
        return ResponseEntity.ok(documentoActualizado);

    }

    @DeleteMapping("/documentos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarEmpleado(@PathVariable Long id) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el documento con el ID : " + id));

        documentoRepository.delete(documento);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminar", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
