package com.brokerTool.model;

import java.io.Serializable;

import javax.persistence.*;
//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import java.sql.Timestamp;

/**
 * The persistent class for the shares database table.
 * 
 */
@XmlRootElement
@Entity
@Table(name = "shares")
@NamedQueries({
		@NamedQuery(name = "Share.findAll", query = "SELECT s FROM Share s"),
		@NamedQuery(name = "Share.selectStocksByCustomer", query = "Select s FROM Share s WHERE s.customer = :customer") })
public class Share implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer sharesId;
	private Timestamp purchasedate;
	private double purchaseprice;
	private Integer quantity;
	private Customer customer;
	private Stock stock;

	public Share() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shares_id")
	public Integer getSharesId() {
		return this.sharesId;
	}

	public void setSharesId(Integer sharesId) {
		this.sharesId = sharesId;
	}

	@XmlJavaTypeAdapter(TimestampAdapter.class)
	public Timestamp getPurchasedate() {
		return this.purchasedate;
	}

	public void setPurchasedate(Timestamp purchasedate) {
		this.purchasedate = purchasedate;
	}

	public double getPurchaseprice() {
		return this.purchaseprice;
	}

	public void setPurchaseprice(double purchaseprice) {
		this.purchaseprice = purchaseprice;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	// bi-directional many-to-one association to Customer
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@XmlInverseReference(mappedBy = "share")
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	// bi-directional many-to-one association to Stock
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symbol")
	@XmlInverseReference(mappedBy = "share")
	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Share [sharesId=" + sharesId + ", purchasedate=" + purchasedate
				+ ", purchaseprice=" + purchaseprice + ", quantity=" + quantity
				+ ", customer=" + customer + ", stock=" + stock + "]";
	}

}