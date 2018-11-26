package com.foreach.across.samples.musical.application.domain.show;

import com.foreach.across.modules.hibernate.jpa.repositories.CommonJpaRepository;
import com.foreach.across.samples.musical.application.domain.musical.Musical;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShowRepository extends CommonJpaRepository<Show, UUID>
{
	public Show findOneByIdAndMusical( UUID id, Musical musical );
}
