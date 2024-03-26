/**
 * 
 */
package com.francisco5em.springrestmvc.services;

import java.io.File;
import java.util.List;

import com.francisco5em.springrestmvc.model.BeerCSVRecord;

/**
 * Creado por Francisco E.
 */
public interface BeerCsvService {
	
	List<BeerCSVRecord> convertCSV(File csvFile);

}
