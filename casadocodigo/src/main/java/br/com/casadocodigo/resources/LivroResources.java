package br.com.casadocodigo.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.Livro;

@Path("livros")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class LivroResources {
	
	@Inject
	private LivroDao livroDao;
	
	@GET
	@Path("lancamentos")
	@Wrapped(element="livros")
	public List<Livro>ultimosLancamentosJson(){
		return livroDao.ultimosLancamentos();		
	}
}
