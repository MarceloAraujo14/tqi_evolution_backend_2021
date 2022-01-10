package com.emprestimo.dto;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("emprestimo")
@Document(indexName = "emprestimos")
public class EmprestimoAtualDTO {

    private Integer codigo;

    private String dataPrimParcela;


}
