package com.slap.core.repository;

import com.slap.core.domain.Resto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Resto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestoRepository extends JpaRepository<Resto,Long> {
    
}
