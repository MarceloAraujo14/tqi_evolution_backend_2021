package com.controller;

import com.cliente.dto.ClienteAtualDTO;
import com.cliente.model.Cliente;
import com.emprestimo.dto.EmprestimoAtualDTO;
import com.emprestimo.dto.EmprestimoDTO;
import com.service.ClienteService;
import com.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/clientes")
public class ClienteController {


    private final ClienteService clienteService;
    private final EmprestimoService emprestimoService;


    //Metodos Cliente


    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getClientByEmail(@AuthenticationPrincipal Cliente user, @PathVariable String email){
        return clienteService.findById(user, email);
    }


    @PutMapping(value = "/atualizar/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@Valid  @RequestBody ClienteAtualDTO clienteDTO, @AuthenticationPrincipal Cliente user, @PathVariable String email){
        return clienteService.update(clienteDTO, user, email);
    }


    //Metodos Emprestimo //TODO: passar as lógicas de validação pro service.

    @PostMapping(value = "/emprestimo/contratar/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity solicitar(@Valid @RequestBody EmprestimoDTO emprestimo,@AuthenticationPrincipal Cliente user, @PathVariable String email){
        return emprestimoService.solicitar(emprestimo,user, email);
    }


    @GetMapping(value = "/emprestimos/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarEmprestimos( @AuthenticationPrincipal Cliente user, @PathVariable String email){
        return emprestimoService.listarPorEmail(user, email);

    }

    @GetMapping(value = "/emprestimos/detalhes/{codigo}{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity detalheEmprestimo( @AuthenticationPrincipal Cliente user, @PathVariable("codigo") String codigo, @PathVariable("email") String email){
        return emprestimoService.findById(user, email, codigo);
    }


    @PutMapping(value = "/emprestimo/atualizar/{codigo}/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity atualizarEmprestimo(@PathVariable String codigo,
                                                      @Valid @RequestBody EmprestimoAtualDTO emprestimoAtualDTO,
                                                      @AuthenticationPrincipal Cliente user, @PathVariable String email){
        return emprestimoService.atualizar(codigo, emprestimoAtualDTO, user, email);

    }

    @PutMapping(value = "/emprestimo/cancelar/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelarSolicitacao(@PathVariable String codigo, @AuthenticationPrincipal Cliente user){
        return ResponseEntity.ok(emprestimoService.cancelar(codigo, user));
    }



}
