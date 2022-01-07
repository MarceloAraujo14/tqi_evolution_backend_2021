package com.emprestimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ClienteEmprestimoDTO {
    private String email;
    private Double renda;
}
