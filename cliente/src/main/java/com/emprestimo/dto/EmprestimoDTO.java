package com.emprestimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @com.validation.constraints.Data
    private String dataPrimParcela;


}
