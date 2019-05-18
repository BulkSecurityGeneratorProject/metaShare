package com.metashare.web.rest;

import com.metashare.domain.ResumeHead;
import com.metashare.service.ResumeHeadService;
import com.metashare.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.metashare.domain.ResumeHead}.
 */
@RestController
@RequestMapping("/api")
public class ResumeHeadResource {

    private final Logger log = LoggerFactory.getLogger(ResumeHeadResource.class);

    private static final String ENTITY_NAME = "resumeHead";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumeHeadService resumeHeadService;

    public ResumeHeadResource(ResumeHeadService resumeHeadService) {
        this.resumeHeadService = resumeHeadService;
    }

    /**
     * {@code POST  /resume-heads} : Create a new resumeHead.
     *
     * @param resumeHead the resumeHead to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumeHead, or with status {@code 400 (Bad Request)} if the resumeHead has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resume-heads")
    public ResponseEntity<ResumeHead> createResumeHead(@Valid @RequestBody ResumeHead resumeHead) throws URISyntaxException {
        log.debug("REST request to save ResumeHead : {}", resumeHead);
        if (resumeHead.getId() != null) {
            throw new BadRequestAlertException("A new resumeHead cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeHead result = resumeHeadService.save(resumeHead);
        return ResponseEntity.created(new URI("/api/resume-heads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resume-heads} : Updates an existing resumeHead.
     *
     * @param resumeHead the resumeHead to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumeHead,
     * or with status {@code 400 (Bad Request)} if the resumeHead is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumeHead couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resume-heads")
    public ResponseEntity<ResumeHead> updateResumeHead(@Valid @RequestBody ResumeHead resumeHead) throws URISyntaxException {
        log.debug("REST request to update ResumeHead : {}", resumeHead);
        if (resumeHead.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResumeHead result = resumeHeadService.save(resumeHead);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resumeHead.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resume-heads} : get all the resumeHeads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumeHeads in body.
     */
    @GetMapping("/resume-heads")
    public ResponseEntity<List<ResumeHead>> getAllResumeHeads(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ResumeHeads");
        Page<ResumeHead> page = resumeHeadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resume-heads/:id} : get the "id" resumeHead.
     *
     * @param id the id of the resumeHead to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumeHead, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resume-heads/{id}")
    public ResponseEntity<ResumeHead> getResumeHead(@PathVariable Long id) {
        log.debug("REST request to get ResumeHead : {}", id);
        Optional<ResumeHead> resumeHead = resumeHeadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resumeHead);
    }

    /**
     * {@code DELETE  /resume-heads/:id} : delete the "id" resumeHead.
     *
     * @param id the id of the resumeHead to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resume-heads/{id}")
    public ResponseEntity<Void> deleteResumeHead(@PathVariable Long id) {
        log.debug("REST request to delete ResumeHead : {}", id);
        resumeHeadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
