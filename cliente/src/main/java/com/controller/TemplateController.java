package com.controller;

import com.cliente.dto.ClienteAtualDTO;
import com.cliente.dto.ClienteReturnDTO;
import com.cliente.model.Cliente;
import com.cliente.model.Endereco;
import com.cliente.model.TipoEndereco;
import com.emprestimo.dto.EmprestimoDTO;
import com.service.ClienteService;
import com.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/clientes")
public class TemplateController {

    private final ClienteService clienteService;
    private final EmprestimoService emprestimoService;
    private final ModelMapper mapper;

    //TODO: passar as lógicas de validação pro service.

    @GetMapping(value = "/home")
    public ModelAndView getClientByEmail(@AuthenticationPrincipal Cliente user){

        ModelAndView mv = new ModelAndView("home-cliente");
        mv.addObject("cliente", clienteService.findById(user.getEmail()));

        return mv;
    }


    @GetMapping(value = "/dados")
    public ModelAndView mostraDadosCliente(@AuthenticationPrincipal Cliente user){
        ModelAndView mv = new ModelAndView("dados-cliente");
        mv.addObject("cliente", clienteService.findById(user.getEmail()));
        mv.addObject("enderecos", clienteService.encontrarEndereco(user.getEmail()));
        return mv;
    }

    @GetMapping(value = "/atualizar")
    public String atualizarCadastro(Model model){
        model.addAttribute("tipos", TipoEndereco.values());
        model.addAttribute("enderecos", new Endereco());
        model.addAttribute("cliente", new ClienteAtualDTO());
        return "atualizar-dados";
    }

    @PostMapping(value = "/atualizar")
    public String cadastro(@Valid @ModelAttribute("cliente") ClienteAtualDTO clienteDTO,
                           BindingResult clienteErrors, @Valid @ModelAttribute("enderecos") Endereco endereco,
                           BindingResult enderecoErrors,
                           Model model, @AuthenticationPrincipal Cliente user){

        if(clienteErrors.hasErrors() || enderecoErrors.hasErrors()){
            model.addAttribute("tipos", TipoEndereco.values());
            return "atualizar-dados";
        }

        clienteDTO.setEnderecos(List.of(endereco));
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        clienteService.update(clienteDTO, cliente, user.getEmail());
        model.addAttribute("cliente", clienteService.findById(user.getEmail()));
        return "dados-cliente";
    }


    //Metodos Emprestimos

    @GetMapping(value = "/contratar")
    public ModelAndView solicitarEmprestimo(@AuthenticationPrincipal Cliente user){

        ModelAndView mv = new ModelAndView("form-emprestimo");
        mv.addObject("emprestimo", new EmprestimoDTO());
        return mv;
    }


    @PostMapping(value = "/emprestimo/solicitar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView solicitar(@Valid @ModelAttribute("emprestimo") EmprestimoDTO emprestimo, BindingResult erros, @AuthenticationPrincipal Cliente user){
        ModelAndView mv = new ModelAndView("form-emprestimo");
        if(erros.hasErrors()){
            mv.addObject("emprestimo", emprestimo);
            return mv;
        }
        mv.setViewName("redirect:/clientes/emprestimos");
        emprestimoService.solicitar(emprestimo, user, user.getEmail());
        Cliente cliente = clienteService.findById(user.getEmail());
        mv.addObject("cliente", cliente);
        return mv;

    }


    @GetMapping(value = "/emprestimos")
    public ModelAndView listarEmprestimos(@AuthenticationPrincipal Cliente user){

        ModelAndView mv = new ModelAndView("lista-emprestimos");
        mv.addObject("emprestimos", emprestimoService.listarPorEmail(user, user.getEmail()));
        return mv;
    }

    @GetMapping(value = "/detalhes/{codigo}")
    public ModelAndView detalheEmprestmo(@AuthenticationPrincipal Cliente user, @PathVariable("codigo") String codigo){
        ModelAndView mv = new ModelAndView("detalhe-emprestimo");
        if(user.getEmail().equals(emprestimoService.findById(codigo).getEmailCliente())){
            mv.addObject("emprestimos", emprestimoService.listarPorEmail(user, user.getEmail()));
            mv.addObject("emprestimo", emprestimoService.findById(codigo));
        }
        return mv;
    }

}
