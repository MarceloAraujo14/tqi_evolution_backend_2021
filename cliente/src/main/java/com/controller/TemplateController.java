package com.controller;

import com.cliente.model.Cliente;
import com.emprestimo.dto.EmprestimoDTO;
import com.service.ClienteService;
import com.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/clientes")
public class TemplateController {

    private final ClienteService clienteService;
    private final EmprestimoService emprestimoService;

    @GetMapping(value = "/home")
    public ModelAndView getClientByEmail(@AuthenticationPrincipal Cliente user){

        ModelAndView mv = new ModelAndView("home-cliente");
        mv.addObject("cliente", clienteService.findByEmail(user.getEmail()));

        return mv;
    }

    @GetMapping(value = "/dados")
    public ModelAndView mostraDadosCliente(@AuthenticationPrincipal Cliente user){
        ModelAndView mv = new ModelAndView("dados-cliente");
        mv.addObject("cliente", clienteService.findByEmail(user.getEmail()));
        mv.addObject("enderecos", clienteService.findByEmail(user.getEmail()).getEnderecos().get(0));
        return mv;
    }

    @GetMapping(value = "/contratar")
    public ModelAndView solicitarEmprestimo(@AuthenticationPrincipal Cliente user){

        ModelAndView mv = new ModelAndView("form-emprestimo");
        mv.addObject("emprestimo", new EmprestimoDTO());
        return mv;
    }

    @GetMapping(value = "/emprestimos")
    public ModelAndView listarEmprestimos(@AuthenticationPrincipal Cliente user){

        ModelAndView mv = new ModelAndView("lista-emprestimos");
        System.out.println(emprestimoService.listarPorEmail(user.getEmail()));
        mv.addObject("emprestimos", emprestimoService.listarPorEmail(user.getEmail()));
        return mv;
    }




}
