package br.com.casadocodigo.loja.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import br.com.casadocodigo.loja.models.Pagamento;

public class PaymentGateway implements Serializable {
	private static final long serialVersionUID = 1L;

	public String pagar(BigDecimal total) {
		
		Client client = ClientBuilder.newClient();
		Pagamento pagamento = new Pagamento(total);
		
		String target = "http://book-payment.herokuapp.com/payment";
		Entity<Pagamento> json = Entity.json(pagamento);
		String response = null;
		try {			
			response = client.target(target)
					.request()
					.post(json,String.class); 
		} catch (WebApplicationException e) {
			System.out.println(e.getMessage());
		}
		return response;
	}
}
