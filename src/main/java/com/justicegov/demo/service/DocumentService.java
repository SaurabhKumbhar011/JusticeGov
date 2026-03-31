package com.justicegov.demo.service;

import java.util.List;

import com.justicegov.demo.dto.*;

public interface DocumentService {
	String upload(DocumentUploadReq req);

	List<DocumentResp> getByEntity(Long id, String role);

	String verify(Long docId, String status);
}