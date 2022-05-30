package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CourtCase;
import com.mycompany.myapp.repository.CourtCaseRepository;
import com.mycompany.myapp.service.CourtCaseService;
import com.mycompany.myapp.service.dto.CourtCaseDTO;
import com.mycompany.myapp.service.mapper.CourtCaseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourtCase}.
 */
@Service
@Transactional
public class CourtCaseServiceImpl implements CourtCaseService {

    private final Logger log = LoggerFactory.getLogger(CourtCaseServiceImpl.class);

    private final CourtCaseRepository courtCaseRepository;

    private final CourtCaseMapper courtCaseMapper;

    public CourtCaseServiceImpl(CourtCaseRepository courtCaseRepository, CourtCaseMapper courtCaseMapper) {
        this.courtCaseRepository = courtCaseRepository;
        this.courtCaseMapper = courtCaseMapper;
    }

    @Override
    public CourtCaseDTO save(CourtCaseDTO courtCaseDTO) {
        log.debug("Request to save CourtCase : {}", courtCaseDTO);
        CourtCase courtCase = courtCaseMapper.toEntity(courtCaseDTO);
        courtCase = courtCaseRepository.save(courtCase);
        return courtCaseMapper.toDto(courtCase);
    }

    @Override
    public CourtCaseDTO update(CourtCaseDTO courtCaseDTO) {
        log.debug("Request to save CourtCase : {}", courtCaseDTO);
        CourtCase courtCase = courtCaseMapper.toEntity(courtCaseDTO);
        courtCase = courtCaseRepository.save(courtCase);
        return courtCaseMapper.toDto(courtCase);
    }

    @Override
    public Optional<CourtCaseDTO> partialUpdate(CourtCaseDTO courtCaseDTO) {
        log.debug("Request to partially update CourtCase : {}", courtCaseDTO);

        return courtCaseRepository
            .findById(courtCaseDTO.getId())
            .map(existingCourtCase -> {
                courtCaseMapper.partialUpdate(existingCourtCase, courtCaseDTO);

                return existingCourtCase;
            })
            .map(courtCaseRepository::save)
            .map(courtCaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourtCaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourtCases");
        return courtCaseRepository.findAll(pageable).map(courtCaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourtCaseDTO> findOne(Long id) {
        log.debug("Request to get CourtCase : {}", id);
        return courtCaseRepository.findById(id).map(courtCaseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourtCase : {}", id);
        courtCaseRepository.deleteById(id);
    }
}
