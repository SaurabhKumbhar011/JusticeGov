package com.example.demo.service;

import com.example.demo.dto.*;
import java.util.List;

public interface DocumentService {
	String upload(DocumentUploadReq req);

	List<DocumentResp> getByEntity(Long id, String role);

	String verify(Long docId, String status);
}