package com.metashare.service.impl;

import com.metashare.service.TaxService;
import com.metashare.domain.Tax;
import com.metashare.repository.TaxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tax}.
 */
@Service
@Transactional
public class TaxServiceImpl implements TaxService {

    private final Logger log = LoggerFactory.getLogger(TaxServiceImpl.class);

    private final TaxRepository taxRepository;

    public TaxServiceImpl(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    /**
     * Save a tax.
     *
     * @param tax the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Tax save(Tax tax) {
        log.debug("Request to save Tax : {}", tax);
        return taxRepository.save(tax);
    }

    /**
     * Get all the taxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Tax> findAll(Pageable pageable) {
        log.debug("Request to get all Taxes");
        return taxRepository.findAll(pageable);
    }


    /**
     * Get one tax by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Tax> findOne(Long id) {
        log.debug("Request to get Tax : {}", id);
        return taxRepository.findById(id);
    }

    /**
     * Delete the tax by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tax : {}", id);
        taxRepository.deleteById(id);
    }
}
