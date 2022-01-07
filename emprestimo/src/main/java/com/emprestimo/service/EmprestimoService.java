package com.emprestimo.service;

import com.emprestimo.dto.ClienteEmprestimoDTO;
import com.emprestimo.dto.EmprestimoAtualDTO;
import com.emprestimo.dto.EmprestimoDTO;
import com.emprestimo.model.Emprestimo;
import com.emprestimo.model.StatusEmprestimo;
import com.emprestimo.repository.EmprestimoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    private final ModelMapper modelMapper;

    private final ZonedDateTime data = ZonedDateTime.now();

    private final String dataForm = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public String solicitar(EmprestimoDTO emprestimoDTO, ClienteEmprestimoDTO cliente) {
        Emprestimo emprestimo = modelMapper.map(emprestimoDTO, Emprestimo.class);
        if (isPrimParcelaValid(emprestimo.getDataPrimParcela(), dataForm)) {
            emprestimo.setDataSolicitacao(dataForm);
            emprestimo.setDataPrimParcela(emprestimoDTO.getDataPrimParcela());
            emprestimo.setStatusEmprestimo(StatusEmprestimo.SOLICITADO);
            emprestimo.setEmailCliente(cliente.getEmail());
            emprestimo.setRendaCliente(cliente.getRenda());
            emprestimo.setValorParcela(emprestimo.getValor() / emprestimo.getParcelas());
            emprestimoRepository.save(emprestimo);
            return "Empréstimo solicitado com sucesso";
        } else {
            return "primParcela: A data da primeira parcela deve ser até 3 meses da data de solicitação.";
        }

    }

    public List<Emprestimo> listarPorEmail(String email) {
//        List<Emprestimo> emprestimoList =new ArrayList<>();
//        for (Emprestimo emprestimo: emprestimoRepository.findByEmailCliente(email)
//        ){ if (emprestimo.getStatusEmprestimo().equals(StatusEmprestimo.CANCELADO)){
//
//        }else {
//            emprestimoList.add(emprestimo);
//        }
//
//        }
        return emprestimoRepository.findByEmailCliente(email)/*emprestimoList*/;
    }

    public String atualizar(String codigo, EmprestimoAtualDTO emprestimoAtualDTO) {
        Emprestimo emprestimo = modelMapper.map(emprestimoRepository.findById(codigo).get(), Emprestimo.class);
        if (isPrimParcelaValid(emprestimo.getDataPrimParcela(), emprestimo.getDataSolicitacao())) {
            emprestimo.setDataPrimParcela(emprestimoAtualDTO.getDataPrimParcela());
            emprestimoRepository.save(emprestimo);
            return "Solicitação de empréstimo atualizada";
        }else {
            return "primParcela: A data da primeira parcela deve ser até 3 meses da data de solicitação.";
        }

    }

    public String cancelar(String codigo) {
        if(emprestimoRepository.findById(codigo).isPresent()){
        Emprestimo emprestimo = modelMapper.map(emprestimoRepository.findById(codigo).get(), Emprestimo.class);
        emprestimo.setStatusEmprestimo(StatusEmprestimo.CANCELADO);
        emprestimoRepository.save(emprestimo);
        return "Solicitação de empréstimo cancelada";
        }else
            return "Empréstimo não encontrado";
    }

    private boolean isPrimParcelaValid(String data, String dataSolicitacao) {
        LocalDate dataPrimeiraParcela = LocalDate.parse(data);
        LocalDate dataAtual = LocalDate.parse(dataSolicitacao);
        return !dataPrimeiraParcela.isAfter(dataAtual.plusMonths(3));
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
