/**
 * 
 */
package com.francisco5em.springrestmvc.services;


import com.opencsv.bean.CsvToBeanBuilder;
import com.francisco5em.springrestmvc.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.springframework.stereotype.Service;



/**
 * Creado por Francisco E.
 */
@Service
public class BeerCsvServiceImpl implements BeerCsvService {

	@Override
	public List<BeerCSVRecord> convertCSV(File csvFile) {
		// TODO Auto-generated method stub
		try {
            List<BeerCSVRecord> beerCSVRecords = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                    .withType(BeerCSVRecord.class)
                    .build().parse();
            return beerCSVRecords;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
	}

}
