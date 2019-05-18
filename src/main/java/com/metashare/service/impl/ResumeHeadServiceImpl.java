package com.metashare.service.impl;

import com.metashare.service.ResumeHeadService;
import com.metashare.domain.ResumeHead;
import com.metashare.repository.ResumeHeadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ResumeHead}.
 */
@Service
@Transactional
public class ResumeHeadServiceImpl implements ResumeHeadService {

    private final Logger log = LoggerFactory.getLogger(ResumeHeadServiceImpl.class);

    private final ResumeHeadRepository resumeHeadRepository;

    public ResumeHeadServiceImpl(ResumeHeadRepository resumeHeadRepository) {
        this.resumeHeadRepository = resumeHeadRepository;
    }

    /**
     * Save a resumeHead.
     *
     * @param resumeHead the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ResumeHead save(ResumeHead resumeHead) {
        log.debug("Request to save ResumeHead : {}", resumeHead);
        return resumeHeadRepository.save(resumeHead);
    }

    /**
     * Get all the resumeHeads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResumeHead> findAll(Pageable pageable) {
        log.debug("Request to get all ResumeHeads");
        return resumeHeadRepository.findAll(pageable);
    }


    /**
     * Get one resumeHead by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResumeHead> findOne(Long id) {
        log.debug("Request to get ResumeHead : {}", id);
        return resumeHeadRepository.findById(id);
    }

    /**
     * Delete the resumeHead by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResumeHead : {}", id);
        resumeHeadRepository.deleteById(id);
    }
}
