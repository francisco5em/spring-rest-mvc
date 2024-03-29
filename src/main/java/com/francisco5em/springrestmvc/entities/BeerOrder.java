/**
 * 
 */
package com.francisco5em.springrestmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

/**
 * Creado por Francisco E.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
public class BeerOrder {

	private String customerRef;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
	private UUID id;

	@Version
	private Long version;

	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp createdDate;

	@UpdateTimestamp
	private Timestamp lastModifiedDate;

	/** All arguments constructor with customer being added by setCustomer method.
	 * @param customerRef
	 * @param id
	 * @param version
	 * @param createdDate
	 * @param lastModifiedDate
	 * @param customer
	 * @param beerOrdersLines
	 */
	public BeerOrder(String customerRef, UUID id, Long version, Timestamp createdDate,
			Timestamp lastModifiedDate, Customer customer,
			Set<BeerOrderLine> beerOrdersLines, BeerOrderShipment beerOrderShipment) {
		super();
		this.customerRef = customerRef;
		this.id = id;
		this.version = version;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.setCustomer(customer);
		this.beerOrdersLines = beerOrdersLines;
		this.setBeerOrderShipment(beerOrderShipment);
	}

	public boolean isNew() {
		return this.id == null;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		customer.getBeerOrders().add(this);
	}

	@ManyToOne
	private Customer customer;

	@OneToMany(mappedBy = "beerOrder")
	private Set<BeerOrderLine> beerOrdersLines;

	@OneToOne(cascade = CascadeType.PERSIST)
	private BeerOrderShipment beerOrderShipment;
	
	public void setBeerOrderShipment(BeerOrderShipment beerOrderShipment) {
        this.beerOrderShipment = beerOrderShipment;
        beerOrderShipment.setBeerOrder(this);
    }

}
