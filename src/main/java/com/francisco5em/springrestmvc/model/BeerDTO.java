/**
 * 
 */
package com.francisco5em.springrestmvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;

/**
 * Creado por Francisco E.
 */
@Builder
@Data
public class BeerDTO {

	
	private UUID id;
	private Integer version;

	@NotBlank
	@NotNull
	@Size(max = 50)
	@Column(length = 50)
	private String beerName;

	@NotNull
	private BeerStyle beerStyle;

	@NotBlank
	@NotNull
	@Size(max = 255)
	private String upc;
	private Integer quantityOnHand;

	@NotNull
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;

}
