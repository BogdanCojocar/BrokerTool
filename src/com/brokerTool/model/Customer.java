package com.brokerTool.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import java.util.List;

/**
 * The persistent class for the customer database table.
 * 
 */
@XmlRootElement
@Entity
@NamedQueries({
		@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
		@NamedQuery(name = "Customer.selectCustomersByBroker", query = "Select c FROM Customer c WHERE c.broker = :broker") })
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer customerId;
	private String account;
	private String address;
	private String fullname;
	private Broker broker;
	private List<Share> shares;

	public Customer() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	// bi-directional many-to-one association to Broker
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "broker_id")
	@XmlInverseReference(mappedBy = "customer")
	public Broker getBroker() {
		return this.broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	// bi-directional many-to-one association to Share
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	@XmlTransient
	public List<Share> getShares() {
		return this.shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

	public Share addShare(Share share) {
		getShares().add(share);
		share.setCustomer(this);

		return share;
	}

	public Share removeShare(Share share) {
		getShares().remove(share);
		share.setCustomer(null);

		return share;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", account=" + account
				+ ", address=" + address + ", fullname=" + fullname
				+ ", broker=" + broker + ", shares=" + shares + "]";
	}
}