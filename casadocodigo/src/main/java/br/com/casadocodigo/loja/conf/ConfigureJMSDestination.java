package br.com.casadocodigo.loja.conf;

import javax.ejb.Singleton;
import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(
		name="java:/jms/topics/CarrinhosComprasTopico",
		interfaceName="javax.jms.Topic")

@Singleton
public class ConfigureJMSDestination {

}
