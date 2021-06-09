package org.mycash.web.api;

import java.util.List;


import javax.validation.Valid;

import org.mycash.domain.Lancamento;

import org.mycash.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lancamento")
public class LancamentoController {

	@Autowired
	private LancamentoService service;

	//paginação: http://localhost:8081/mycash/api/lancamento?page=0&size=8
	//o link acima, numero da pagina é 0, quantidades de itens na pagina sao 8
	@GetMapping
	public Page<Lancamento> pageTodos(Pageable pageable){

		return service.pageTodos(pageable);
	}
	/*
	//obs ou o metodo de paginação ou metodo comum
	@GetMapping
	List<Lancamento> todos() {

		return service.todos();
	}
	*/
	//@Valid chama a classe CustomGlobalExceptionHandler com metodo ResponseEntityExceptionHandler
	@PostMapping
	Lancamento criar(@Valid @RequestBody Lancamento lancamento) { //@Valid executa a validação antes metodo ser chamado, faz validação do obj esta chegando

		return service.criar(lancamento);
	}
	
	@GetMapping("/{id}")
	Lancamento apenasUm(@PathVariable Integer id) {
		return service.apenasUm(id);
	}
	
	@PutMapping("/{id}")
	Lancamento atualizar(
			@PathVariable Integer id, 
			@RequestBody Lancamento novoLancamento) {
	
		return service.atualizar(id, novoLancamento);
	}
	
	@DeleteMapping("/{id}")
	void excluir(@PathVariable Integer id) {

		service.excluir(id);
	}
	
}
