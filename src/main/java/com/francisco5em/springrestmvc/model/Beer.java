/**
 * 
 */
package com.francisco5em.springrestmvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.Builder;

/**
 * Creado por Francisco E.
 */
@Builder
@Data
public class Beer {

	private UUID id;
	private Integer version;
	private String beerName;
	private BeerStyle beerStyle;
	private String upc;
	private Integer quantityOnHand;
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;
	
	
}
