package com.controller;

import com.cliente.dto.ClienteAtualDTO;
import com.cliente.dto.ClienteReturnDTO;
import com.cliente.model.Cliente;
import com.emprestimo.dto.EmprestimoAtualDTO;
import com.emprestimo.dto.EmprestimoDTO;
import com.emprestimo.model.Emprestimo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.ClienteService;
import com.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/clientes")
public class ClienteController {


    private final ClienteService clienteService;
    private final EmprestimoService emprestimoService;
    private final ModelMapper mapper;


    //Metodos Cliente


    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getClientByEmail(@AuthenticationPrincipal Cliente user, @PathVariable String email){
        if(Objects.equals(user.getEmail(), email)){
        return ResponseEntity.ok(clienteService.findByEmail(email).toString());}
        else {
            return ResponseEntity.ok("Usuário não encontrado.");
        }
    }


    @PutMapping(value = "/atualizar/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@Valid  @RequestBody ClienteAtualDTO clienteDTO, @AuthenticationPrincipal Cliente user, @PathVariable String email){
        if(Objects.equals(user.getEmail(), email)){
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        return ResponseEntity.ok(clienteService.update(cliente, user.getEmail()));}
        else return ResponseEntity.ok("Usuário não encontrado");
    }


    //Metodos Emprestimo

    @PostMapping(value = "/emprestimo/contratar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> solicitar(@Valid @RequestBody EmprestimoDTO emprestimo,@AuthenticationPrincipal Cliente user){

        return ResponseEntity.ok(emprestimoService.solicitar(emprestimo, user));
    }

    @GetMapping(value = "/emprestimos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Emprestimo>> getEmprestimo( @AuthenticationPrincipal Cliente user){
        return ResponseEntity.ok(emprestimoService.listarPorEmail(user.getEmail()));
    }


    @PutMapping(value = "/emprestimo/atualizar/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> atualizarEmprestimo(@PathVariable String codigo, @Valid @RequestBody EmprestimoAtualDTO emprestimoAtualDTO, @AuthenticationPrincipal Cliente user){

        return ResponseEntity.ok(emprestimoService.atualizar(codigo, emprestimoAtualDTO, user));
    }

    @PutMapping(value = "/emprestimo/cancelar/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelarSolicitacao(@PathVariable String codigo, @AuthenticationPrincipal Cliente user){

        return ResponseEntity.ok(emprestimoService.cancelar(codigo, user));
    }



}
