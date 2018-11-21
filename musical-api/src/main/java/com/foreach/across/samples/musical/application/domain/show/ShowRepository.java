package com.foreach.across.samples.musical.application.domain.show;

import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends IdBasedEntityJpaRepository<Show> {
}
