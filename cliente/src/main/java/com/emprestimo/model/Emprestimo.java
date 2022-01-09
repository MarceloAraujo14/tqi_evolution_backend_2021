package com.emprestimo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("emprestimo")
@Document(indexName = "emprestimos")
public class Emprestimo {

    private String dataSolicitacao;

    @Id
    private String codigo;

    private StatusEmprestimo statusEmprestimo;

    private Double valor;

    private Integer parcelas;

    private Double valorParcela;

    private String dataPrimParcela;

    private String emailCliente;

    private Double rendaCliente;


}
