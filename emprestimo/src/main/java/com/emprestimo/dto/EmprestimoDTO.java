package com.emprestimo.dto;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("emprestimo")
@Document(indexName = "emprestimos")
public class EmprestimoDTO {

    @NotNull
    @Digits(integer = 50, fraction = 2)
    private Double valor;

    @NotNull
    @Digits(integer = 2, fraction = 0)
    @Max(value = 60, message = "O limite de parcelas é 60.")
    @Min(value = 1, message = "O mínimo de parcelas é 1.")
    private Integer parcelas;

    @Digits(integer = 50, fraction = 2)
    private Double valorParcela;

    @NotBlank
    private String dataPrimParcela;

    private String emailCliente;

    private BigDecimal rendaCliente;

}
