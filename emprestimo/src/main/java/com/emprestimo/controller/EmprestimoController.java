package com.emprestimo.controller;

import com.emprestimo.dto.ClienteEmprestimoDTO;
import com.emprestimo.dto.EmprestimoAtualDTO;
import com.emprestimo.dto.EmprestimoDTO;
import com.emprestimo.feign.ClienteEmprestimo;
import com.emprestimo.model.Emprestimo;
import com.emprestimo.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final ClienteEmprestimo clienteEmprestimo;

    @PostMapping("/solicitar/{email}")
    public ResponseEntity<String> solicitar(@Valid @RequestBody EmprestimoDTO emprestimo, @PathVariable("email") String email){

        ClienteEmprestimoDTO cliente = clienteEmprestimo.getClienteData(email);

        return ResponseEntity.ok(emprestimoService.solicitar(emprestimo, cliente));
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Emprestimo>> getEmprestimo(@PathVariable String email){
        return ResponseEntity.ok(emprestimoService.listarPorEmail(email));
    }

    @PutMapping("/atualizar/{codigo}")
    public ResponseEntity<String> atualizarEmprestimo(@PathVariable String codigo, @Valid @RequestBody EmprestimoAtualDTO emprestimoAtualDTO){

        return ResponseEntity.ok(emprestimoService.atualizar(codigo, emprestimoAtualDTO));
    }

    @PutMapping("/cancelar/{codigo}")
    public ResponseEntity<String> cancelarSolicitacao(@PathVariable String codigo){
        return ResponseEntity.ok(emprestimoService.cancelar(codigo));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.ok(emprestimoService.handleException(ex));
    }

}
