package com.brokerTool.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

/**
 * The persistent class for the broker database table.
 * 
 */
@XmlRootElement
@Entity
@NamedQuery(name = "Broker.findAll", query = "SELECT b FROM Broker b")
public class Broker implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer brokerId;
	private String address;
	private String brokername;
	private List<Customer> customers;

	public Broker() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "broker_id")
	public Integer getBrokerId() {
		return this.brokerId;
	}

	public void setBrokerId(Integer brokerId) {
		this.brokerId = brokerId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBrokername() {
		return this.brokername;
	}

	public void setBrokername(String brokername) {
		this.brokername = brokername;
	}

	// bi-directional many-to-one association to Customer
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "broker")
	@XmlTransient
	public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Customer addCustomer(Customer customer) {
		getCustomers().add(customer);
		customer.setBroker(this);

		return customer;
	}

	public Customer removeCustomer(Customer customer) {
		getCustomers().remove(customer);
		customer.setBroker(null);

		return customer;
	}

	@Override
	public String toString() {
		return "Broker [brokerId=" + brokerId + ", address=" + address
				+ ", brokername=" + brokername + ", customers=" + customers
				+ "]";
	}
}