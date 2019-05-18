package com.metashare.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.metashare.domain.Demo;
import com.metashare.domain.*; // for static metamodels
import com.metashare.repository.DemoRepository;
import com.metashare.service.dto.DemoCriteria;

/**
 * Service for executing complex queries for {@link Demo} entities in the database.
 * The main input is a {@link DemoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Demo} or a {@link Page} of {@link Demo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DemoQueryService extends QueryService<Demo> {

    private final Logger log = LoggerFactory.getLogger(DemoQueryService.class);

    private final DemoRepository demoRepository;

    public DemoQueryService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    /**
     * Return a {@link List} of {@link Demo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Demo> findByCriteria(DemoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Demo> specification = createSpecification(criteria);
        return demoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Demo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Demo> findByCriteria(DemoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Demo> specification = createSpecification(criteria);
        return demoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DemoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Demo> specification = createSpecification(criteria);
        return demoRepository.count(specification);
    }

    /**
     * Function to convert DemoCriteria to a {@link Specification}.
     */
    private Specification<Demo> createSpecification(DemoCriteria criteria) {
        Specification<Demo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Demo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Demo_.name));
            }
            if (criteria.getContant() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContant(), Demo_.contant));
            }
        }
        return specification;
    }
}
