package com.validation;

import com.emprestimo.model.Emprestimo;
import com.repository.EmprestimoRepository;
import com.service.EmprestimoService;
import com.validation.constraints.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class DataValidation implements ConstraintValidator<Data, String> {

    private final ZonedDateTime data = ZonedDateTime.now();

    private final String dataForm = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    @Override
    public void initialize(Data constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {

        return (isPrimParcelaValid(data, dataForm));
    }

    private boolean isPrimParcelaValid(String data, String dataSolicitacao) {
        LocalDate dataPrimeiraParcela = LocalDate.parse(data);
        LocalDate dataAtual = LocalDate.parse(dataSolicitacao);
        return !dataPrimeiraParcela.isAfter(dataAtual.plusMonths(3));
    }

}
