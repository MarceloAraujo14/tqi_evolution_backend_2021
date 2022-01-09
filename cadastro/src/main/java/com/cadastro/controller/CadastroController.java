package com.cadastro.controller;

import com.cadastro.dto.ClienteCadastroDTO;
import com.cadastro.model.Cliente;
import com.cadastro.model.Endereco;
import com.cadastro.model.TipoEndereco;
import com.cadastro.service.CadastroService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Controller
@AllArgsConstructor
public class CadastroController {

    private final CadastroService cadastroService;
    private final ModelMapper mapper;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping(value = "/novocadastro")
    public String paginaCadastro(Model model){
        model.addAttribute("tipos", TipoEndereco.values());
        model.addAttribute("enderecos", new Endereco());
        model.addAttribute("cliente", new ClienteCadastroDTO());
        return "cadastro";
    }

    @PostMapping(value = "/cadastrar")
    public String cadastro(@Valid ClienteCadastroDTO clienteDTO, @Valid Endereco endereco, BindingResult errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute("cliente", clienteDTO);
        }

        clienteDTO.setEnderecos(List.of(endereco));
        System.out.println(clienteDTO);


        Cliente cliente = mapper.map(clienteDTO, Cliente.class);
        cadastroService.cadastro(cliente);
        return "home";
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.ok(cadastroService.handleException(ex));
    }

    //Metodos não utilizados pela interface


}
