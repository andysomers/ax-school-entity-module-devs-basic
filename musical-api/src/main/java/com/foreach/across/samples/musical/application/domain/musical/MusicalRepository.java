package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicalRepository extends IdBasedEntityJpaRepository<Musical> {
}
