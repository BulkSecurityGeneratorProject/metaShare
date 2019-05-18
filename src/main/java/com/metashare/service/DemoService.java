package com.metashare.service;

import com.metashare.domain.Demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Demo}.
 */
public interface DemoService {

    /**
     * Save a demo.
     *
     * @param demo the entity to save.
     * @return the persisted entity.
     */
    Demo save(Demo demo);

    /**
     * Get all the demos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Demo> findAll(Pageable pageable);


    /**
     * Get the "id" demo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Demo> findOne(Long id);

    /**
     * Delete the "id" demo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
