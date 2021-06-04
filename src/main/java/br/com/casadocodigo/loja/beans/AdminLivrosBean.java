package br.com.casadocodigo.loja.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.dao.AutorDao;
import br.com.casadocodigo.loja.dao.LivroDao;
import br.com.casadocodigo.loja.models.Autor;
import br.com.casadocodigo.loja.models.Livro;

// CDI anotatios
@Named
@RequestScoped
public class AdminLivrosBean {
	
	private Livro livro = new Livro();
	
	@Inject
	private LivroDao LivroDao;
	
	@Inject
	private AutorDao autorDao;
	
	private List<Integer> autoresId = new ArrayList<>();
	
	@Inject
	private FacesContext context;
		
	@Transactional
	public String salvar() {
		for (Integer autorId : autoresId) {
			livro.getAutores().add(new Autor(autorId));			
		}
		LivroDao.salvar(livro);
		System.out.println("Livro Cadastro"+ this.livro);
		this.livro = new Livro();
		this.autoresId = new ArrayList<>();
		
		// Enviado a instancia da mensagem para o proximo contexto, onde a mensagem ira durar 2 requests 
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		// Criando mensagem para o usuario
		context.addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));    
		
		return "/livros/lista?faces-redirect=true";
	}
	
	public List<Autor> getAutores(){
		return autorDao.listar();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Integer> getAutoresId() {
		return autoresId;
	}

	public void setAutoresId(List<Integer> autoresId) {
		this.autoresId = autoresId;
	}

}
