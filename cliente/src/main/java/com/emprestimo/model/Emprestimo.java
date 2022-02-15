package com.emprestimo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("emprestimo")
@Table(name = "emprestimos")
public class Emprestimo {

    private String dataSolicitacao;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "emprestimo_sequence", sequenceName = "emprestimo_sequence", allocationSize = 1)
    private String codigo;

    private StatusEmprestimo statusEmprestimo;

    private Double valor;

    private Integer parcelas;

    private Double valorParcela;

    private String dataPrimParcela;

    private String emailCliente;

    private Double rendaCliente;


}
