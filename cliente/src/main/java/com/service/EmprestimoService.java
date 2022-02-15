package com.service;

import com.cliente.model.Cliente;
import com.emprestimo.dto.EmprestimoAtualDTO;
import com.emprestimo.dto.EmprestimoDTO;
import com.emprestimo.model.Emprestimo;
import com.emprestimo.model.StatusEmprestimo;
import com.repository.EmprestimoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    private final ModelMapper modelMapper;

    private final ZonedDateTime data = ZonedDateTime.now();

    private final String dataForm = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public Emprestimo findById(String codigo) {
        return emprestimoRepository.findById(codigo).get();
    }

    public ResponseEntity findById(Cliente user, String email, String codigo) {
        if(Objects.equals(user.getEmail(), email)){
            Emprestimo emprestimo = modelMapper.map(emprestimoRepository.findById(codigo).get(), Emprestimo.class);
            return ResponseEntity.ok(emprestimo);}

        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"Usuário não tem permissão para realizar essa requisição\"");
    }

    public ResponseEntity solicitar(EmprestimoDTO emprestimoDTO, Cliente user, String email) {

        if(Objects.equals(user.getEmail(), email)){
            Emprestimo emprestimo = modelMapper.map(emprestimoDTO, Emprestimo.class);

            emprestimo.setEmailCliente(user.getEmail());
            emprestimo.setRendaCliente(user.getRenda());
            emprestimo.setDataSolicitacao(dataForm);
            emprestimo.setDataPrimParcela(emprestimoDTO.getDataPrimParcela());
            emprestimo.setStatusEmprestimo(StatusEmprestimo.SOLICITADO);
            emprestimo.setValorParcela(valorParcela(emprestimo.getValor(), emprestimo.getParcelas()));
            emprestimoRepository.save(emprestimo);
            return ResponseEntity.ok("{\"Empréstimo solicitado com sucesso\"}");}

        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"Usuário não tem permissão para realizar essa requisição\"");
    }


    public ResponseEntity listarPorEmail(Cliente user, String email) {
        if(Objects.equals(user.getEmail(), email)){
            return ResponseEntity.ok(emprestimoRepository.findById(user.getEmail()));}
        else return ResponseEntity.ok(new ArrayList<>());
    }

    public ResponseEntity atualizar(String codigo, EmprestimoAtualDTO emprestimoAtualDTO, Cliente user, String email) {

        if(emprestimoRepository.existsById(codigo)){
        Emprestimo emprestimo = modelMapper.map(emprestimoRepository.findById(codigo).get(), Emprestimo.class);

        if(Objects.equals(user.getEmail(), email))
        {
            if(!emprestimo.getEmailCliente().equals(user.getEmail())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"Solicitação negada.\"}");
            }

            if (isPrimParcelaValid(emprestimo.getDataPrimParcela(), emprestimo.getDataSolicitacao())) {
            emprestimo.setEmailCliente(user.getEmail());
            emprestimo.setDataPrimParcela(emprestimoAtualDTO.getDataPrimParcela());
            emprestimoRepository.save(emprestimo);
            return ResponseEntity.ok()
                    .body("{\"Solicitação de empréstimo atualizada\"}");
            }
            else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"primParcela\": \"A data da primeira parcela deve ser até 3 meses da data de solicitação.\"}");
            }
        }
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"Usuário não tem permissão para realizar essa requisição\"");
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Empréstimo não encontrado\"}");
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




}


