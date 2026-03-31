package com.justicegov.demo.dto;

import lombok.Data;

@Data
public class DocumentUploadReq {
    private Long ownerId;    // Matches "ownerId" in Postman
    private String role;     // Matches "role" in Postman
    private String docType;  // Matches "docType" in Postman
    private String fileUri;  // Matches "fileUri" in Postman
}