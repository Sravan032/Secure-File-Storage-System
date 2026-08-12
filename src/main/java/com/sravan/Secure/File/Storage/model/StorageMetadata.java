package com.sravan.Secure.File.Storage.model;

public class StorageMetadata {

    private String originalFileName;
    private String contentType;
    private long fileSize;
    private String encryptionAlgorithm;
    private String keyEncryptionAlgorithm;
    private String uploadedAt;

    public StorageMetadata() {
    }

    public StorageMetadata(String originalFileName,
                           String contentType,
                           long fileSize,
                           String encryptionAlgorithm,
                           String keyEncryptionAlgorithm,
                           String uploadedAt) {

        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.keyEncryptionAlgorithm = keyEncryptionAlgorithm;
        this.uploadedAt = uploadedAt;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public String getKeyEncryptionAlgorithm() {
        return keyEncryptionAlgorithm;
    }

    public void setKeyEncryptionAlgorithm(String keyEncryptionAlgorithm) {
        this.keyEncryptionAlgorithm = keyEncryptionAlgorithm;
    }

    public String getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(String uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}