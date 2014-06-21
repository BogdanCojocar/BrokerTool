package com.brokerTool.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

/**
 * The persistent class for the stock database table.
 * 
 */
@XmlRootElement
@Entity
@NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;
	private String symbol;
	private String sector;
	private String stockname;
	private List<Share> shares;

	public Stock() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSector() {
		return this.sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getStockname() {
		return this.stockname;
	}

	public void setStockname(String stockname) {
		this.stockname = stockname;
	}

	// bi-directional many-to-one association to Share
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
	@XmlTransient
	public List<Share> getShares() {
		return this.shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

	public Share addShare(Share share) {
		getShares().add(share);
		share.setStock(this);

		return share;
	}

	public Share removeShare(Share share) {
		getShares().remove(share);
		share.setStock(null);

		return share;
	}

	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", sector=" + sector
				+ ", stockname=" + stockname + ", shares=" + shares + "]";
	}
}