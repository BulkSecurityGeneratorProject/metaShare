package com.metashare.service.impl;

import com.metashare.service.GoodsService;
import com.metashare.domain.Goods;
import com.metashare.repository.GoodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Goods}.
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    private final Logger log = LoggerFactory.getLogger(GoodsServiceImpl.class);

    private final GoodsRepository goodsRepository;

    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    /**
     * Save a goods.
     *
     * @param goods the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Goods save(Goods goods) {
        log.debug("Request to save Goods : {}", goods);
        return goodsRepository.save(goods);
    }

    /**
     * Get all the goods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Goods> findAll(Pageable pageable) {
        log.debug("Request to get all Goods");
        return goodsRepository.findAll(pageable);
    }


    /**
     * Get one goods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Goods> findOne(Long id) {
        log.debug("Request to get Goods : {}", id);
        return goodsRepository.findById(id);
    }

    /**
     * Delete the goods by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Goods : {}", id);
        goodsRepository.deleteById(id);
    }
}
