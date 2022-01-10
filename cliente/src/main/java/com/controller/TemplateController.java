package com.controller;

import com.cliente.dto.ClienteAtualDTO;
import com.cliente.model.Cliente;
import com.cliente.model.Endereco;
import com.cliente.model.TipoEndereco;
import com.emprestimo.dto.EmprestimoDTO;
import com.service.ClienteService;
import com.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping(value = "/atualizar")
    public String atualizarCadastro(Model model){
        model.addAttribute("tipos", TipoEndereco.values());
        model.addAttribute("enderecos", new Endereco());
        model.addAttribute("cliente", new ClienteAtualDTO());
        return "atualizar-dados";
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
        mv.addObject("emprestimos", emprestimoService.listarPorEmail(user.getEmail()));
        return mv;
    }

    @GetMapping(value = "/detalhes/{codigo}")
    public ModelAndView detalheEmprestmo(@AuthenticationPrincipal Cliente user, @PathVariable("codigo") String codigo){
        ModelAndView mv = new ModelAndView("detalhe-emprestimo");
        if(user.getEmail().equals(emprestimoService.findById(codigo).getEmailCliente())){
            mv.addObject("emprestimos", emprestimoService.listarPorEmail(user.getEmail()));
            mv.addObject("emprestimo", emprestimoService.findById(codigo));
        }
        return mv;
    }



}
