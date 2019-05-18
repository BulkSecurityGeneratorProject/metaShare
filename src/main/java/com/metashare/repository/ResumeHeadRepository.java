package com.metashare.repository;

import com.metashare.domain.ResumeHead;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResumeHead entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeHeadRepository extends JpaRepository<ResumeHead, Long> {

}
