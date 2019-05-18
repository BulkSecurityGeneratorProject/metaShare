package com.metashare.repository;

import com.metashare.domain.Demo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Demo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemoRepository extends JpaRepository<Demo, Long>, JpaSpecificationExecutor<Demo> {

}
