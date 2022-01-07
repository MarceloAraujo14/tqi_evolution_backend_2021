package com.emprestimo.feign;

import com.emprestimo.dto.ClienteEmprestimoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente", url = "http://localhost:8080")
public interface ClienteEmprestimo {

    @GetMapping("/clientes/{email}")
    ClienteEmprestimoDTO getClienteData(@PathVariable String email);
}
