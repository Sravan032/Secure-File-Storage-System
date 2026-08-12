package com.sravan.Secure.File.Storage.dto;

public class FileDownloadResponse {

    private final byte[] data;
    private final String fileName;
    private final String contentType;

    public FileDownloadResponse(byte[] data,
                                String fileName,
                                String contentType) {

        this.data = data;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }
}