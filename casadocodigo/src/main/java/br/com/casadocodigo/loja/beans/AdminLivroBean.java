package br.com.casadocodigo.loja.beans;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AutorDao;
import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Autor;
import br.com.casadocodigo.loja.models.Livro;
@Named
@RequestScoped
public class AdminLivroBean {
	private Livro livro	= new Livro();
	
	@Inject
	private LivroDao livroDao;

	@Inject
	private AutorDao autorDao;
	
	@Inject
	private FacesContext context;

	private Part capaLivro; 
	
	@Transactional
	public String salvar() throws IOException{
		livroDao.salvar(this.livro);
		FileSaver fileSaver = new FileSaver();
		livro.setCapaPath(fileSaver.write(capaLivro,"images"));
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Livro Cadastrado com Sucesso !"));
		return "/livros/lista?faces-redirect=true";

	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public AutorDao getAutorDao() {
		return autorDao;
	}

	public void setAutorDao(AutorDao autorDao) {
		this.autorDao = autorDao;
	}

	public List<Autor> getAutores() {
		return autorDao.listaAutores();
	}

	public LivroDao getLivroDao() {
		return livroDao;
	}
	
	public void setLivroDao(LivroDao livroDao) {
		this.livroDao = livroDao;
	}
	public Part getCapaLivro() {
		return capaLivro;
	}
	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}
	
}
