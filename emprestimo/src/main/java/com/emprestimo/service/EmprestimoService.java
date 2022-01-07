package com.emprestimo.service;

import com.emprestimo.dto.ClienteEmprestimoDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    private final ModelMapper modelMapper;

    private final ZonedDateTime dataHora = ZonedDateTime.now();

    private final String dataHoraForm = dataHora.format(DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z"));

    public String cadastrar(EmprestimoDTO emprestimoDTO, ClienteEmprestimoDTO cliente) {
        Emprestimo emprestimo = modelMapper.map(emprestimoDTO, Emprestimo.class);
        if (isPrimParcelaValid(emprestimo.getDataPrimParcela())) {
            emprestimo.setDataSolicitacao(dataHoraForm);
            emprestimo.setDataPrimParcela(dataHoraForm);
            emprestimo.setStatusEmprestimo(StatusEmprestimo.SOLICITADO);
            emprestimo.setEmailCliente(cliente.getEmail());
            emprestimo.setRendaCliente(cliente.getRenda());
            emprestimo.setValorParcela(emprestimo.getValor() / emprestimo.getParcelas());
            emprestimoRepository.save(emprestimo);
            return "Empréstimo solicitado com sucesso";
        } else {
            return "primParcela: A data da primeira parcela deve ser até 3 meses da data atual.";
        }


    }

    public List<Emprestimo> findByEmail(String email) {

        return emprestimoRepository.findByEmailCliente(email);
    }

    public String cancelar(Integer codigo) {
        emprestimoRepository.deleteByCodigo(codigo);
        return "Solicitação de empréstimo cancelada";
    }

    private boolean isPrimParcelaValid(String data) {
        LocalDate dataPrimeiraParcela = LocalDate.parse(data);
        LocalDate dataAtual = LocalDate.now();
        return !dataPrimeiraParcela.isAfter(dataAtual.plusMonths(3));
    }



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
