package com.metashare.service.impl;

import com.metashare.service.DemoService;
import com.metashare.domain.Demo;
import com.metashare.repository.DemoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Demo}.
 */
@Service
@Transactional
public class DemoServiceImpl implements DemoService {

    private final Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);

    private final DemoRepository demoRepository;

    public DemoServiceImpl(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    /**
     * Save a demo.
     *
     * @param demo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Demo save(Demo demo) {
        log.debug("Request to save Demo : {}", demo);
        return demoRepository.save(demo);
    }

    /**
     * Get all the demos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Demo> findAll(Pageable pageable) {
        log.debug("Request to get all Demos");
        return demoRepository.findAll(pageable);
    }


    /**
     * Get one demo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Demo> findOne(Long id) {
        log.debug("Request to get Demo : {}", id);
        return demoRepository.findById(id);
    }

    /**
     * Delete the demo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Demo : {}", id);
        demoRepository.deleteById(id);
    }
}
