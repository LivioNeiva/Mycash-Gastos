package org.mycash.service;

import org.mycash.domain.Lancamento;

import org.mycash.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repo;

    public Page<Lancamento> pageTodos(Pageable pageable){
        return repo.findAll(pageable);
    }
    /*
    public List<Lancamento> todos(){
        return repo.findAll();
    }
    */
    public Lancamento apenasUm(Integer id){
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Lancamento criar(Lancamento lancamento){

        return repo.save(lancamento);
    }

    public Lancamento atualizar(
                    @PathVariable Integer id,
                    @RequestBody Lancamento novoLancamento){
        return repo.findById(id)
                .map(lancamento -> {
                    lancamento.setDescricao(novoLancamento.getDescricao());
                    lancamento.setValor(novoLancamento.getValor());
                    lancamento.setTipo(novoLancamento.getTipo());
                    lancamento.setData(novoLancamento.getData());

                    return repo.save(lancamento);
                })
                .orElseThrow(() -> new EntityNotFoundException());
     }

    public void excluir(Integer id){
        repo.deleteById(id);
     }


}
