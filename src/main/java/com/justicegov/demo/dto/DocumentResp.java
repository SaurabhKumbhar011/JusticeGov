package com.justicegov.demo.dto;
import lombok.Data;

@Data
public class DocumentResp {
    private Long id;
    private String docType;
    private String fileUri;
    private String verificationStatus;
}