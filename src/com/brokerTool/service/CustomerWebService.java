package com.brokerTool.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.brokerTool.model.Broker;
import com.brokerTool.model.Customer;
import com.brokerTool.service.Response.Status;

@Stateless
@LocalBean
@Path("/customer")
public class CustomerWebService {

	@PersistenceContext
	private EntityManager em;

	private static Logger LOGGER = Logger.getLogger("CustomerWebServiceLog");

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Rest service for BrokrTool is working...";
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("{id}")
	public Customer getCustomerById(@PathParam("id") int id) {
		return em.find(Customer.class, id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("add")
	public Response addCustomer(Customer customer) {
		LOGGER.info("Add customer " + customer.toString());
		try {
			em.persist(customer);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			LOGGER.info(e.getStackTrace().toString());
			return new Response(Status.NOT_OK, e.getMessage());
		}
		return new Response();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("update")
	public Response updateCustomer(Customer customer) {
		LOGGER.info("Updating customer " + customer.toString());
		try {
			em.merge(customer);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			LOGGER.info(e.getStackTrace().toString());
			return new Response(Response.Status.NOT_OK, e.getMessage());
		}
		return new Response();
	}

	@DELETE
	//@Produces(MediaType.APPLICATION_XML)
	@Path("delete/{id}")
	public void deleteCustomer(@PathParam("id") int id) {
		Customer customer = getCustomerById(id);
		if (customer != null) {
			LOGGER.info("Deleting customer " + customer.toString());
			try {
				em.remove(customer);
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
				LOGGER.info(e.getStackTrace().toString());
				//return new Response(Response.Status.NOT_OK, e.getMessage());
			}
			//return new Response();
		} else {
			//return new Response(Response.Status.NOT_OK, "Invalid id for user.");
		}
	}

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("all")
	public List<Customer> getAllCustomers() {
		LOGGER.info("Getting all customers.");
		return em.createNamedQuery("Customer.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("selectCustomerByBrokerId/{id}")
	public List<Customer> getAllCustomersByBroker(@PathParam("id") int id) {
		Broker broker = em.find(Broker.class, id);
		LOGGER.info("Getting all customers for broker: " + broker.toString());
		Query query = em.createNamedQuery("Customer.selectCustomersByBroker");
		query.setParameter("broker", broker);
		return query.getResultList();
	}

}
