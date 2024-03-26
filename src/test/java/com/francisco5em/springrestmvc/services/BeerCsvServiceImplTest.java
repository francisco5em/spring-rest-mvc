/**
 * 
 */
package com.francisco5em.springrestmvc.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.francisco5em.springrestmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Creado por Francisco E.
 */
class BeerCsvServiceImplTest {
	
	BeerCsvService beercsvS = new BeerCsvServiceImpl();

	/**
	 * Test method for {@link com.francisco5em.springrestmvc.services.BeerCsvServiceImpl#convertCSV(java.io.File)}.
	 */
	 @Test
	    void convertCSV() throws FileNotFoundException {

	        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

	        List<BeerCSVRecord> recs = beercsvS.convertCSV(file);

	        //System.out.println(recs.size());

	        assertThat(recs.size()).isGreaterThan(0);
	    }

}
