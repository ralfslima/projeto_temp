package br.com.backproduto.backproduto.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.backproduto.backproduto.modelo.PessoaModelo;
import br.com.backproduto.backproduto.servico.PessoaServico;
import br.com.backproduto.backproduto.util.Mensagem;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class PessoaControle {

    @Autowired
    private PessoaServico pessoaServico;
    @Autowired
    private Mensagem mensagem;

  
    @GetMapping("/pessoa")
    public ResponseEntity<Iterable<PessoaModelo>> listarTodasPessoas() {
        Iterable<PessoaModelo> pessoas = pessoaServico.listarTodasPessoas();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @GetMapping("/pessoa/{id}")
    public ResponseEntity<PessoaModelo> buscarPessoaPorId(@PathVariable Long codigo) {
        PessoaModelo pessoaOptional = pessoaServico.buscarPessoaPorId(codigo);
        //return new ResponseEntity<>(pessoaOptional, HttpStatus.OK);
        return new ResponseEntity<>(pessoaOptional, HttpStatus.OK);
    }

    @PostMapping("/pessoa")
    public ResponseEntity<PessoaModelo> cadastraPressoa(@RequestBody PessoaModelo pessoaModelo) {
        try {
            return pessoaServico.cadastrarPessoa(pessoaModelo);
            //PessoaModelo cadastrarPessoa = pessoaServico.cadastrarPessoa(pessoaModelo);
            //return new ResponseEntity<>(cadastrarPessoa, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/pessoa/{id}")
    public ResponseEntity<Void> removerPessoa(@PathVariable Long codigo) {
        PessoaModelo existePessoa = pessoaServico.buscarPessoaPorId(codigo);

        // Aqui seria interessante você validar um contador e não um objeto.
        // Se o contador for igual a zero, quer dizer que não foi encontrado determinado código
        if(existePessoa.getNome().equals("")){
            pessoaServico.removerPessoa(codigo);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // O método isPresent() não existe.
        // if (existePessoa.isPresent()) {
        //     pessoaServico.removerPessoa(codigo);
        //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // } else {
        //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // }


    }
    
}
