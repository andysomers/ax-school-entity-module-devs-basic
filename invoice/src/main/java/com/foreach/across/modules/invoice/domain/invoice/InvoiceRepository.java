package com.foreach.across.modules.invoice.domain.invoice;

import com.foreach.across.modules.hibernate.jpa.repositories.IdBasedEntityJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends IdBasedEntityJpaRepository<Invoice> {
}
