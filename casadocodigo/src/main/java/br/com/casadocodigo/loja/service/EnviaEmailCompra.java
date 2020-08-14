package br.com.casadocodigo.loja.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.infra.MailSender;
import br.com.casadocodigo.loja.models.Compra;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName="destinationLookup",
				propertyValue="java:/jms/topics/CarrinhosComprasTopico"),
		@ActivationConfigProperty(
				propertyName="destinationType",
				propertyValue="javax.jms.Topic")				
})
public class EnviaEmailCompra implements MessageListener{

	@Inject
	private MailSender mailSender;
	
	@Inject
	private CompraDao compraDao;
	
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;		
			Compra compra;
			compra = compraDao.buscaPorUuid(textMessage.getText());
			String messageBody = "Pedido: " + compra.getUuid() + " realizado com sucesso.";
			mailSender.send("gersontampo@gmail.com",compra.getUsuario().getEmail(),"Casa do Livro",messageBody);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
