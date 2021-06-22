package br.com.casadocodigo.loja.beans;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.dao.LivroDao;
import br.com.casadocodigo.loja.models.Livro;

@Model
public class LivroDetalheBean {

	@Inject
	private LivroDao dao;
	
	private Livro livro;
	
	private Integer id;
	
	public void carregarDetalhe() {
		this.livro = dao.buscarPorId(id);
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
