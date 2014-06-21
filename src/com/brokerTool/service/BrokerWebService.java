package com.brokerTool.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.brokerTool.model.Broker;

@Stateless
@LocalBean
@Path("/broker")
public class BrokerWebService {
	
	@PersistenceContext
	private EntityManager em;

	private static Logger LOGGER = Logger.getLogger("BrokerWebServiceLog");
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("{id}")
	public Broker getCustomerById(@PathParam("id") int id) {
		return em.find(Broker.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("all")
	public List<Broker> getAllBrokers() {
		LOGGER.info("Getting all brokers.");
		return em.createNamedQuery("Broker.findAll").getResultList();
	}
}
