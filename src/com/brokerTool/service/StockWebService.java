package com.brokerTool.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.brokerTool.model.Stock;
import com.brokerTool.service.Response.Status;

@Stateless
@LocalBean
@Path("/stock")
public class StockWebService {

	@PersistenceContext
	private EntityManager em;

	private static Logger LOGGER = Logger.getLogger("StockWebServiceLog");

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("all")
	public List<Stock> getAllStocks() {
		LOGGER.info("Getting all stocks.");
		return em.createNamedQuery("Stock.findAll").getResultList();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("add")
	public Response addStock(Stock stock) {
		LOGGER.info("Add stock " + stock.toString());
		try {
			em.persist(stock);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			LOGGER.info(e.getStackTrace().toString());
			return new Response(Status.NOT_OK, e.getMessage());
		}
		return new Response();
	}
}
