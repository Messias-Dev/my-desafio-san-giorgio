package br.com.nunesmaia.desafio_san_giorgio.database.repository;

import br.com.nunesmaia.desafio_san_giorgio.domain.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findByIdIn(Collection<Long> ids);


}
