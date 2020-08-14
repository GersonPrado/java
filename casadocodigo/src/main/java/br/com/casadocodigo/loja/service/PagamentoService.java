package br.com.casadocodigo.loja.service;

import java.io.Serializable;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.models.Compra;

@Path("/pagamento")
public class PagamentoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	CompraDao compraDao;
	
	@Inject
	PaymentGateway paymentGateway;
	
	@Context
	private ServletContext context;
	
	private static ExecutorService executor = Executors.newFixedThreadPool(50);
	
	@Inject
	private JMSContext jmsContext;
	
	@Resource(name="java:/jms/topics/CarrinhosComprasTopico")
	private Destination destination;
	
	public PagamentoService() {	}
	
	@POST
	public void pagar(@Suspended final AsyncResponse ar, @QueryParam("uuid") String uuid) {
		Compra compra = compraDao.buscaPorUuid(uuid);
		String contextPath = context.getContextPath();
		JMSProducer producer = jmsContext.createProducer();
		
		executor.submit(() -> {
			try {
					paymentGateway.pagar(compra.getTotal());
					
					producer.send(destination, compra.getUuid());
					
					URI uri = UriBuilder.fromPath("http://localhost:8089"+contextPath+"/index.xhtml")
							.queryParam("msg", "compra realizada com sucesso.")
							.build();
					Response response = Response.seeOther(uri).build();
									
					ar.resume(response);
				
			} catch (Exception e) {
				ar.resume(new WebApplicationException(e));					
			}
		});
	}
}








