package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CourtCase;
import com.mycompany.myapp.repository.CourtCaseRepository;
import com.mycompany.myapp.service.criteria.CourtCaseCriteria;
import com.mycompany.myapp.service.dto.CourtCaseDTO;
import com.mycompany.myapp.service.mapper.CourtCaseMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CourtCase} entities in the database.
 * The main input is a {@link CourtCaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourtCaseDTO} or a {@link Page} of {@link CourtCaseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourtCaseQueryService extends QueryService<CourtCase> {

    private final Logger log = LoggerFactory.getLogger(CourtCaseQueryService.class);

    private final CourtCaseRepository courtCaseRepository;

    private final CourtCaseMapper courtCaseMapper;

    public CourtCaseQueryService(CourtCaseRepository courtCaseRepository, CourtCaseMapper courtCaseMapper) {
        this.courtCaseRepository = courtCaseRepository;
        this.courtCaseMapper = courtCaseMapper;
    }

    /**
     * Return a {@link List} of {@link CourtCaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourtCaseDTO> findByCriteria(CourtCaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseMapper.toDto(courtCaseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourtCaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourtCaseDTO> findByCriteria(CourtCaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.findAll(specification, page).map(courtCaseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourtCaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourtCase> specification = createSpecification(criteria);
        return courtCaseRepository.count(specification);
    }

    /**
     * Function to convert {@link CourtCaseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourtCase> createSpecification(CourtCaseCriteria criteria) {
        Specification<CourtCase> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourtCase_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), CourtCase_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), CourtCase_.lastName));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), CourtCase_.address));
            }
            if (criteria.getContactNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNo(), CourtCase_.contactNo));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), CourtCase_.emailAddress));
            }
            if (criteria.getSalary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalary(), CourtCase_.salary));
            }
            if (criteria.getGstOnSalary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGstOnSalary(), CourtCase_.gstOnSalary));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), CourtCase_.lastModified));
            }
        }
        return specification;
    }
}
