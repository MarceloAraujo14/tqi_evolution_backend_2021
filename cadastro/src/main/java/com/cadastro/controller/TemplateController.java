package com.cadastro.controller;
import com.cadastro.dto.ClienteCadastroDTO;
import com.cadastro.model.Cliente;
import com.cadastro.model.Endereco;
import com.cadastro.model.TipoEndereco;
import com.cadastro.service.CadastroService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class TemplateController {

    private final CadastroService cadastroService;
    private final ModelMapper mapper;


    @GetMapping(value = "/novo")
    public String paginaCadastro(Model model){
        model.addAttribute("tipos", TipoEndereco.values());
        model.addAttribute("enderecos", new Endereco());
        model.addAttribute("cliente", new ClienteCadastroDTO());
        return "form-cadastro";
    }

    @PostMapping(value = "/novo")
    public String cadastro(@Valid @ModelAttribute("cliente") ClienteCadastroDTO clienteDTO, BindingResult clienteErrors, @Valid @ModelAttribute("enderecos") Endereco endereco, BindingResult enderecoErrors, Model model){

        if(clienteErrors.hasErrors() || enderecoErrors.hasErrors()){
            model.addAttribute("tipos", TipoEndereco.values());
            return "form-cadastro";
        }
        model.addAttribute("mensagem", "Cadastro bem sucedido");
        clienteDTO.setEnderecos(List.of(endereco));
        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        cadastroService.cadastro(cliente);
        return "home";
    }

}
