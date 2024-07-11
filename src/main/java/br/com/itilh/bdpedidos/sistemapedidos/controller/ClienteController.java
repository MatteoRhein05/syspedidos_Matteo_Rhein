package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;

import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ClienteController {

    public final ClienteRepository repositorio;

    public ClienteController (ClienteRepository repositorio){
        this.repositorio = repositorio;

    }

    @GetMapping("/clientes")
    public List<Cliente> getClientes() {
        return (List<Cliente>) repositorio.findAll();
    }
    
    @GetMapping("/cliente/{id}")
    public Cliente getCliente(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id)
        .orElseThrow(()-> new Exception("Id não encontrado.")) ;
    }
    
    @PostMapping("/cliente")
    public Cliente postCliente(@RequestBody Cliente entity) throws Exception {
        try {
            return repositorio.save(entity);
        } catch (Exception e) {
            throw new Exception("Não foi possível criar o cliente." + e.getMessage());
        }

    }

    @PutMapping("/cliente/{id}")
    public Cliente putCliente(@PathVariable BigInteger id, @RequestBody Cliente entity) throws Exception {
        try {
            return repositorio.save(entity);
            
        } catch (Exception e) {
            throw new Exception("Não foi possível alterar o cliente" + e.getMessage());
        }
        
    }

    
    @DeleteMapping("/cliente/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {

        Optional<Cliente> estadoAmazenado = repositorio.findById(id);
        if(estadoAmazenado.isPresent()){
            repositorio.delete(estadoAmazenado.get());
            return "Excluído";
        }
        throw new Exception("Id não econtrado para a exclusão");
    }    

}

