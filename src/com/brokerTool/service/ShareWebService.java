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

import com.brokerTool.model.Customer;
import com.brokerTool.model.Share;
import com.brokerTool.service.Response.Status;

@Stateless
@LocalBean
@Path("/share")
public class ShareWebService {

	@PersistenceContext
	private EntityManager em;

	private static Logger LOGGER = Logger.getLogger("ShareWebServiceLog");

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("{id}")
	public Share getShareById(@PathParam("id") int id) {
		LOGGER.info("Find share by id" + Integer.toString(id));
		return em.find(Share.class, id);
	}

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("all")
	public List<Share> getAllShares() {
		LOGGER.info("Getting all shares.");
		return em.createNamedQuery("Share.findAll").getResultList();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("add")
	public Response addShare(Share share) {
		LOGGER.info("Add a new share " + share.toString());
		try {
			em.persist(share);
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
	public Response updateShare(Share share) {
		LOGGER.info("Updating share " + share.toString());
		try {
			em.merge(share);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			LOGGER.info(e.getStackTrace().toString());
			return new Response(Response.Status.NOT_OK, e.getMessage());
		}
		return new Response();
	}

	@DELETE
	@Path("delete/{id}")
	public void deleteShare(@PathParam("id") int id) {
		Share share = getShareById(id);
		if (share != null) {
			LOGGER.info("Deleting share " + share.toString());
			try {
				em.remove(share);
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
				LOGGER.info(e.getStackTrace().toString());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("selectSharesByCustomerId/{id}")
	public List<Share> getAllSharesByCustomerId(@PathParam("id") int id) {
		Customer customer = em.find(Customer.class, id);
		LOGGER.info("Getting all shares for customer: " + customer.toString());
		Query query = em.createNamedQuery("Share.selectStocksByCustomer");
		query.setParameter("customer", customer);
		return query.getResultList();
	}
}
