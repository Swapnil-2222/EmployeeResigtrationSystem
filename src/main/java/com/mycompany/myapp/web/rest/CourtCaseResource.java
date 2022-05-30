package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CourtCaseRepository;
import com.mycompany.myapp.service.CourtCaseQueryService;
import com.mycompany.myapp.service.CourtCaseService;
import com.mycompany.myapp.service.criteria.CourtCaseCriteria;
import com.mycompany.myapp.service.dto.CourtCaseDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CourtCase}.
 */
@RestController
@RequestMapping("/api")
public class CourtCaseResource {

    private final Logger log = LoggerFactory.getLogger(CourtCaseResource.class);

    private static final String ENTITY_NAME = "courtCase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourtCaseService courtCaseService;

    private final CourtCaseRepository courtCaseRepository;

    private final CourtCaseQueryService courtCaseQueryService;

    public CourtCaseResource(
        CourtCaseService courtCaseService,
        CourtCaseRepository courtCaseRepository,
        CourtCaseQueryService courtCaseQueryService
    ) {
        this.courtCaseService = courtCaseService;
        this.courtCaseRepository = courtCaseRepository;
        this.courtCaseQueryService = courtCaseQueryService;
    }

    /**
     * {@code POST  /court-cases} : Create a new courtCase.
     *
     * @param courtCaseDTO the courtCaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courtCaseDTO, or with status {@code 400 (Bad Request)} if the courtCase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/court-cases")
    public ResponseEntity<CourtCaseDTO> createCourtCase(@RequestBody CourtCaseDTO courtCaseDTO) throws URISyntaxException {
        log.debug("REST request to save CourtCase : {}", courtCaseDTO);
        if (courtCaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new courtCase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourtCaseDTO result = courtCaseService.save(courtCaseDTO);
        return ResponseEntity
            .created(new URI("/api/court-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /court-cases/:id} : Updates an existing courtCase.
     *
     * @param id the id of the courtCaseDTO to save.
     * @param courtCaseDTO the courtCaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courtCaseDTO,
     * or with status {@code 400 (Bad Request)} if the courtCaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courtCaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/court-cases/{id}")
    public ResponseEntity<CourtCaseDTO> updateCourtCase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCaseDTO courtCaseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourtCase : {}, {}", id, courtCaseDTO);
        if (courtCaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourtCaseDTO result = courtCaseService.update(courtCaseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /court-cases/:id} : Partial updates given fields of an existing courtCase, field will ignore if it is null
     *
     * @param id the id of the courtCaseDTO to save.
     * @param courtCaseDTO the courtCaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courtCaseDTO,
     * or with status {@code 400 (Bad Request)} if the courtCaseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courtCaseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courtCaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/court-cases/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourtCaseDTO> partialUpdateCourtCase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CourtCaseDTO courtCaseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourtCase partially : {}, {}", id, courtCaseDTO);
        if (courtCaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courtCaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courtCaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourtCaseDTO> result = courtCaseService.partialUpdate(courtCaseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courtCaseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /court-cases} : get all the courtCases.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courtCases in body.
     */
    @GetMapping("/court-cases")
    public ResponseEntity<List<CourtCaseDTO>> getAllCourtCases(
        CourtCaseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourtCases by criteria: {}", criteria);
        Page<CourtCaseDTO> page = courtCaseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /court-cases/count} : count all the courtCases.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/court-cases/count")
    public ResponseEntity<Long> countCourtCases(CourtCaseCriteria criteria) {
        log.debug("REST request to count CourtCases by criteria: {}", criteria);
        return ResponseEntity.ok().body(courtCaseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /court-cases/:id} : get the "id" courtCase.
     *
     * @param id the id of the courtCaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courtCaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/court-cases/{id}")
    public ResponseEntity<CourtCaseDTO> getCourtCase(@PathVariable Long id) {
        log.debug("REST request to get CourtCase : {}", id);
        Optional<CourtCaseDTO> courtCaseDTO = courtCaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courtCaseDTO);
    }

    /**
     * {@code DELETE  /court-cases/:id} : delete the "id" courtCase.
     *
     * @param id the id of the courtCaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/court-cases/{id}")
    public ResponseEntity<Void> deleteCourtCase(@PathVariable Long id) {
        log.debug("REST request to delete CourtCase : {}", id);
        courtCaseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
