package br.com.nunesmaia.desafio_san_giorgio.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "billing")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double price;

}
