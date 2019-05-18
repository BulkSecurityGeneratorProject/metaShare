package com.metashare.service;

import com.metashare.domain.ResumeHead;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ResumeHead}.
 */
public interface ResumeHeadService {

    /**
     * Save a resumeHead.
     *
     * @param resumeHead the entity to save.
     * @return the persisted entity.
     */
    ResumeHead save(ResumeHead resumeHead);

    /**
     * Get all the resumeHeads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResumeHead> findAll(Pageable pageable);


    /**
     * Get the "id" resumeHead.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResumeHead> findOne(Long id);

    /**
     * Delete the "id" resumeHead.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
