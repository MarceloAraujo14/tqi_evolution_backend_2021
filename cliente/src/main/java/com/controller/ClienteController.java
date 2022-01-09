package com.controller;

import com.cliente.dto.ClienteDTO;
import com.cliente.dto.ClienteReturnDTO;
import com.cliente.model.Cliente;
import com.emprestimo.dto.EmprestimoAtualDTO;
import com.emprestimo.dto.EmprestimoDTO;
import com.emprestimo.model.Emprestimo;
import com.service.ClienteService;
import com.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/clientes")
public class ClienteController {


    private final ClienteService clienteService;
    private final EmprestimoService emprestimoService;
    private final ModelMapper mapper;


    @GetMapping(value = "/")
    public ResponseEntity<ClienteReturnDTO> getClientByEmail(@AuthenticationPrincipal ClienteReturnDTO user){

        return ResponseEntity.ok(clienteService.findByEmail(user.getEmail()));
    }

    @PutMapping(value = "/atualizar/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@Valid  @RequestBody ClienteDTO clienteDTO,  @AuthenticationPrincipal ClienteReturnDTO user){
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        return ResponseEntity.ok(clienteService.update(cliente, user.getEmail()));
    }

    @PostMapping(value = "/emprestimo/solicitar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> solicitar(@Valid @RequestBody EmprestimoDTO emprestimo,@AuthenticationPrincipal ClienteReturnDTO user){

        return ResponseEntity.ok(emprestimoService.solicitar(emprestimo, user));
    }

    @GetMapping(value = "/emprestimos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Emprestimo>> getEmprestimo( @AuthenticationPrincipal ClienteReturnDTO user){
        return ResponseEntity.ok(emprestimoService.listarPorEmail(user.getEmail()));
    }

    @PutMapping(value = "/emprestimo/atualizar/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> atualizarEmprestimo(@PathVariable String codigo, @Valid @RequestBody EmprestimoAtualDTO emprestimoAtualDTO, @AuthenticationPrincipal ClienteReturnDTO user){

        return ResponseEntity.ok(emprestimoService.atualizar(codigo, emprestimoAtualDTO, user));
    }

    @PutMapping(value = "/emprestimo/cancelar/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelarSolicitacao(@PathVariable String codigo, @AuthenticationPrincipal ClienteReturnDTO user){

        return ResponseEntity.ok(emprestimoService.cancelar(codigo, user));
    }


    //
    //
    //
    //


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.ok(clienteService.handleException(ex));
    }

    //Metodos não utilizados pela interface

    @DeleteMapping(value = "/del")
    public ResponseEntity<String> delete(){
        return ResponseEntity.ok(clienteService.delete());
    }

    @GetMapping("/list")
    public ResponseEntity<List<Cliente>> getAllClientes(){

        return ResponseEntity.ok(clienteService.findAll());
    }

}
