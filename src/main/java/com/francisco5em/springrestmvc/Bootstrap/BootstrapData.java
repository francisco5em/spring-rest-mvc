/**
 * 
 */
package com.francisco5em.springrestmvc.Bootstrap;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.francisco5em.springrestmvc.entities.*;
import com.francisco5em.springrestmvc.model.BeerCSVRecord;
import com.francisco5em.springrestmvc.model.BeerDTO;
import com.francisco5em.springrestmvc.model.BeerStyle;
import com.francisco5em.springrestmvc.repositories.BeerRepository;
import com.francisco5em.springrestmvc.repositories.CustomerRepository;
import com.francisco5em.springrestmvc.services.BeerCsvService;

import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.List;

/**
 * Creado por Francisco E.
 */
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;
	private final BeerCsvService beerCSVS;
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		
		loadBeerDefaultData();
		loadCSVDatos();
		loadCustomerData();

	}

	private void loadCSVDatos() throws FileNotFoundException {
		if(beerRepository.count()<10) {
			File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
			List<BeerCSVRecord> lista= beerCSVS.convertCSV(file);
			lista.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                                .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                                .beerStyle(beerStyle)
                                .price(BigDecimal.TEN)
                                .upc(beerCSVRecord.getRow().toString())
                                .quantityOnHand(beerCSVRecord.getCount())
                        .build());
            });
		}
		
	}

	private void loadBeerDefaultData() {
		if (beerRepository.count() == 0) {
			Beer beer1 = Beer.builder().id(UUID.randomUUID()).version(1)
					.beerName("Sonora Especial").beerStyle(BeerStyle.WHEAT).upc("1151533")
					.price(new BigDecimal("40.00")).quantityOnHand(122)
					.createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now())
					.build();

			Beer beer2 = Beer.builder().id(UUID.randomUUID()).version(1)
					.beerName("Bud-Heavy").beerStyle(BeerStyle.PALE_ALE).upc("12322")
					.price(new BigDecimal("11.99")).quantityOnHand(392)
					.createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now())
					.build();

			Beer beer3 = Beer.builder().id(UUID.randomUUID()).version(1)
					.beerName("White Tower").beerStyle(BeerStyle.IPA).upc("1252356")
					.price(new BigDecimal("13.99")).quantityOnHand(144)
					.createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now())
					.build();

			beerRepository.save(beer1);
			beerRepository.save(beer2);
			beerRepository.save(beer3);

		}

	}


    private void loadCustomerData() {

        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer 1")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer 2")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer 3")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }

    }
}
