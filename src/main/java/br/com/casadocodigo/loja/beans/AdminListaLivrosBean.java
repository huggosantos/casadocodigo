package br.com.casadocodigo.loja.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.dao.LivroDao;
import br.com.casadocodigo.loja.models.Livro;

@Model
public class AdminListaLivrosBean {

    @Inject
    private LivroDao LivroDao;

    private List<Livro> livros = new ArrayList<>();

    public List<Livro> getLivros() {
        this.livros = LivroDao.listar();

        return livros;
    }

}