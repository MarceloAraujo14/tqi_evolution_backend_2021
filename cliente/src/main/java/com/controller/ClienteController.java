package com.controller;

import com.cliente.dto.ClienteAtualDTO;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/clientes")
public class ClienteController {


    private final ClienteService clienteService;
    private final EmprestimoService emprestimoService;
    private final ModelMapper mapper;


    @PutMapping(value = "/atualizar/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@Valid  @RequestBody ClienteAtualDTO clienteDTO, @AuthenticationPrincipal Cliente user){
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        return ResponseEntity.ok(clienteService.update(cliente, user.getEmail()));
    }

    @PostMapping(value = "/emprestimo/solicitar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView solicitar(@Valid @ModelAttribute("emprestimo") EmprestimoDTO emprestimo, BindingResult erros, @AuthenticationPrincipal Cliente user){
        ModelAndView mv = new ModelAndView("form-emprestimo");
        if(erros.hasErrors()){
            mv.addObject("emprestimo", emprestimo);
            return mv;
        }
        mv.setViewName("redirect:/clientes/emprestimos");
        emprestimoService.solicitar(emprestimo, user);
        ClienteReturnDTO cliente = clienteService.findByEmail(user.getEmail());
        mv.addObject("cliente", cliente);
        return mv;

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
