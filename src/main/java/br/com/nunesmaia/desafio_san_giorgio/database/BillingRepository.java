package br.com.nunesmaia.desafio_san_giorgio.database;

import br.com.nunesmaia.desafio_san_giorgio.domain.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    Set<Billing> findByIdIn(Collection<Long> ids);


}
