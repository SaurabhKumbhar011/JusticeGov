package com.example.demo.service;

import com.example.demo.dto.CaseRequestDTO;
import com.example.demo.dto.CaseResponseDTO;
import com.example.demo.model.Case;
import com.example.demo.model.Citizen;
import com.example.demo.model.Lawyer;
import com.example.demo.model.enums.CaseStatus;
import com.example.demo.repository.CaseRepository;
import com.example.demo.repository.CitizenRepository;
import com.example.demo.repository.LawyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private LawyerRepository lawyerRepository;

    @Override
    public CaseResponseDTO fileCase(CaseRequestDTO dto) {
        Citizen citizen = citizenRepository.findById(dto.getCitizenId())
                .orElseThrow(() -> new RuntimeException("Citizen not found"));
        Lawyer lawyer = null;
        if (dto.getLawyerId() != null) {
            lawyer = lawyerRepository.findById(dto.getLawyerId())
                    .orElseThrow(() -> new RuntimeException("Lawyer not found"));
        }
        Case newCase = Case.builder()
                .citizen(citizen)
                .lawyer(lawyer)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .filedDate(dto.getFiledDate())
                .status(CaseStatus.FILED)
                .build();
        Case saved = caseRepository.save(newCase);
        return toResponseDTO(saved);
    }

    @Override
    public List<CaseResponseDTO> getAllCases() {
        return caseRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public CaseResponseDTO getCaseById(Long id) {
        Case c = caseRepository.findById(id).orElseThrow(() -> new RuntimeException("Case not found"));
        return toResponseDTO(c);
    }

    private CaseResponseDTO toResponseDTO(Case c) {
        CaseResponseDTO dto = new CaseResponseDTO();
        dto.setId(c.getId());
        dto.setCitizenId(c.getCitizen() != null ? c.getCitizen().getId() : null);
        dto.setLawyerId(c.getLawyer() != null ? c.getLawyer().getId() : null);
        dto.setTitle(c.getTitle());
        dto.setDescription(c.getDescription());
        dto.setFiledDate(c.getFiledDate());
        dto.setStatus(c.getStatus() != null ? c.getStatus().name() : null);
        return dto;
    }
}