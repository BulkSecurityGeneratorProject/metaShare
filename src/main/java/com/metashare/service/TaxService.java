package com.metashare.service;

import com.metashare.domain.Tax;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Tax}.
 */
public interface TaxService {

    /**
     * Save a tax.
     *
     * @param tax the entity to save.
     * @return the persisted entity.
     */
    Tax save(Tax tax);

    /**
     * Get all the taxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tax> findAll(Pageable pageable);


    /**
     * Get the "id" tax.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tax> findOne(Long id);

    /**
     * Delete the "id" tax.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
