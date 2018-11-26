package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.modules.hibernate.jpa.repositories.CommonJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MusicalRepository extends CommonJpaRepository<Musical, UUID>
{
}
