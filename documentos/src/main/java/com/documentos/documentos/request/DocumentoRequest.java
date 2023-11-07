package com.documentos.documentos.request;

import org.springframework.web.multipart.MultipartFile;

public class DocumentoRequest {
    private String title;
    private String description;
    private MultipartFile docFile;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public MultipartFile getDocFile() {
        return docFile;
    }
    public void setDocFile(MultipartFile docFile) {
        this.docFile = docFile;
    }

    
}
