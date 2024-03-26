/**
 * 
 */
package com.francisco5em.springrestmvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Verifying active Profile
 * Creado por Francisco E.
 */
//@Component
@Slf4j
public class HelloBean {
	

	  HelloBean(@Value("${helloMessage}") String helloMessage) {
	    log.info(helloMessage);
	  }

}
