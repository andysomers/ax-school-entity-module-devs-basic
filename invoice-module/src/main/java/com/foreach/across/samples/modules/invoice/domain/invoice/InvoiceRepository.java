package com.foreach.across.samples.modules.invoice.domain.invoice;

import com.foreach.across.core.annotations.Exposed;
import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Exposed
public interface InvoiceRepository extends IdBasedEntityJpaRepository<Invoice>
{
}
