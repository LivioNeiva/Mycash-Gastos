package org.mycash.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "lancamento")
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ds_lancamento", length = 50)
	@NotBlank(message = "Campo Descrição deve ser preenchido")
	private String descricao;
	
	@Column(name = "dt_lancamento")
	@NotNull(message = "Campo Data é obrigatório")
	private LocalDate data;
	
	// Precision: quantidade total de dígitos no número
	// Scale: quantidade de dígitos à direita do ponto decimal
	// Exemplo: 123456.78
	@Column(name = "vl_lancamento", precision = 8, scale = 2)
	@Min(value = 0, message = "Campo Valor não pode ser negativo")
	@NotNull(message = "Campo valor é obrigatório")
	private Double valor;
	
	@Enumerated(EnumType.STRING) //grava no banco de dados em forma de string
	//@Column(nullable = false)
	@NotNull(message = "Campo tipo-Enum é obrigatório")
	//@Pattern(regexp = "DESPESA|RECEITA", message = "presica ser um texto")
	private LancamentoTipo tipo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LancamentoTipo getTipo() {
		return tipo;
	}

	public void setTipo(LancamentoTipo tipo) {
		this.tipo = tipo;
	}

}
