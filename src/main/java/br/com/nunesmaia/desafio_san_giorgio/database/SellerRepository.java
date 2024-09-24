package br.com.nunesmaia.desafio_san_giorgio.database;

import br.com.nunesmaia.desafio_san_giorgio.domain.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
}
