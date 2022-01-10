package com.service;

import com.cliente.dto.ClienteReturnDTO;
import com.cliente.model.Cliente;
import com.emprestimo.dto.EmprestimoAtualDTO;
import com.emprestimo.dto.EmprestimoDTO;
import com.emprestimo.model.Emprestimo;
import com.emprestimo.model.StatusEmprestimo;
import com.netflix.servo.monitor.DoubleGauge;
import com.repository.EmprestimoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    private final ModelMapper modelMapper;

    private final ZonedDateTime data = ZonedDateTime.now();

    private final String dataForm = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public String solicitar(EmprestimoDTO emprestimoDTO, Cliente cliente) {
        Emprestimo emprestimo = modelMapper.map(emprestimoDTO, Emprestimo.class);

            emprestimo.setEmailCliente(cliente.getEmail());
            emprestimo.setRendaCliente(cliente.getRenda());
            emprestimo.setDataSolicitacao(dataForm);
            emprestimo.setDataPrimParcela(emprestimoDTO.getDataPrimParcela());
            emprestimo.setStatusEmprestimo(StatusEmprestimo.SOLICITADO);
            emprestimo.setValorParcela(valorParcela(emprestimo.getValor(), emprestimo.getParcelas()));
            emprestimoRepository.save(emprestimo);
            return "{\"Empréstimo solicitado com sucesso\"}";


    }

    public List<Emprestimo> listarPorEmail(String email) {
        return emprestimoRepository.findByEmailCliente(email);
    }

    public String atualizar(String codigo, EmprestimoAtualDTO emprestimoAtualDTO, Cliente cliente) {
        Emprestimo emprestimo = modelMapper.map(emprestimoRepository.findById(codigo).get(), Emprestimo.class);
        if(emprestimo.getEmailCliente().equals(cliente.getEmail())){
            return "{\"Solicitação negada.\"}";
        }
        if (isPrimParcelaValid(emprestimo.getDataPrimParcela(), emprestimo.getDataSolicitacao())) {
            emprestimo.setEmailCliente(cliente.getEmail());
            emprestimo.setDataPrimParcela(emprestimoAtualDTO.getDataPrimParcela());
            emprestimoRepository.save(emprestimo);
            return "{\"Solicitação de empréstimo atualizada\"}";
        }else {
            return "{\"primParcela\": \"A data da primeira parcela deve ser até 3 meses da data de solicitação.\"}";
        }

    }

    public String cancelar(String codigo, Cliente cliente) {
        Optional<Emprestimo> emprestimoCancelar = emprestimoRepository.findById(codigo);
        if(emprestimoCancelar.isPresent() && emprestimoCancelar.get().getEmailCliente().equals(cliente.getEmail())){
        Emprestimo emprestimo = modelMapper.map(emprestimoRepository.findById(codigo).get(), Emprestimo.class);
        emprestimo.setStatusEmprestimo(StatusEmprestimo.CANCELADO);
        emprestimoRepository.save(emprestimo);
        return "{\"Solicitação de empréstimo cancelada.\"}";
        }else
            return "\"Empréstimo não encontrado.\"}";
    }

    private boolean isPrimParcelaValid(String data, String dataSolicitacao) {
        LocalDate dataPrimeiraParcela = LocalDate.parse(data);
        LocalDate dataAtual = LocalDate.parse(dataSolicitacao);
        return !dataPrimeiraParcela.isAfter(dataAtual.plusMonths(3));
    }

    private double valorParcela(double valor, double parcelas){
        return Double.parseDouble(String.format("%.2f",(valor / parcelas)).replace(",","."));
    }

    //
    //
    //
    //





    public Map<String, String> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
