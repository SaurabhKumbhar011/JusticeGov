package com.example.demo.service;

import com.example.demo.dto.AuditRequest;
import com.example.demo.dto.AuditResponse;
import com.example.demo.model.Audit;
import com.example.demo.model.User;
import com.example.demo.model.enums.AuditStatus;
import com.example.demo.repository.AuditRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepo;
    private final UserRepository userRepo;

    @Override
    public AuditResponse getAuditById(Long id) {
        Optional<Audit> opt = auditRepo.findById(id);
        
        if (opt.isPresent()) {
            return mapToAuditResponse(opt.get());
        } else {
            throw new RuntimeException("Audit Record #" + id + " not found in JusticeGov records.");
        }
    }

    @Override
    public AuditResponse createAudit(AuditRequest req) {
        User officer = userRepo.findById(req.getOfficerId()).orElse(null);
        
        if (officer == null) {
            throw new RuntimeException("Audit Officer ID " + req.getOfficerId() + " not found.");
        }

        // FIXED: Replaced .builder() with standard 'new' and 'setters' 
        // because your Audit model uses @Data without @Builder enabled.
        Audit audit = new Audit();
        audit.setOfficer(officer);
        audit.setScope(req.getScope());
        audit.setDate(req.getDate());
        
        // Logic for AuditStatus
        if (req.getStatus() != null) {
            try {
                audit.setStatus(AuditStatus.valueOf(req.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                audit.setStatus(AuditStatus.OPEN); // Fallback to default
            }
        } else {
            audit.setStatus(AuditStatus.OPEN);
        }

        Audit saved = auditRepo.save(audit);
        return mapToAuditResponse(saved);
    }

    @Override
    public AuditResponse updateAudit(Long id, AuditRequest details) {
        Audit existing = auditRepo.findById(id).orElse(null);
        
        if (existing == null) {
            throw new RuntimeException("Audit Record #" + id + " does not exist.");
        }

        if (details.getFindings() != null) {
            existing.setFindings(details.getFindings());
        }
        if (details.getStatus() != null) {
            try {
                existing.setStatus(AuditStatus.valueOf(details.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status. Use OPEN, CLOSED, or IN_PROGRESS.");
            }
        }

        Audit updated = auditRepo.save(existing);
        return mapToAuditResponse(updated);
    }

    @Override
    public List<AuditResponse> getAllAudits() {
        List<Audit> audits = auditRepo.findAll();
        List<AuditResponse> responseList = new ArrayList<>();
        
        for (Audit a : audits) {
            responseList.add(mapToAuditResponse(a));
        }
        return responseList;
    }

    private AuditResponse mapToAuditResponse(Audit a) {
        AuditResponse res = new AuditResponse();
        
        // Matches the 'private Long id' in your Audit model
        res.setAuditId(a.getId());
        
        if (a.getOfficer() != null) {
            res.setOfficerId(a.getOfficer().getId());
        }
        
        res.setScope(a.getScope());
        res.setFindings(a.getFindings());
        res.setDate(a.getDate());
        
        if (a.getStatus() != null) {
            res.setStatus(a.getStatus().name());
        }
        
        return res;
    }
}