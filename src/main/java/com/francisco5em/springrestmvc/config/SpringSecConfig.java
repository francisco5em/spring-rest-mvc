/**
 * 
 */
package com.francisco5em.springrestmvc.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Creado por Francisco E.
 */
@Configuration
//@EnableWebSecurity
public class SpringSecConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpS) throws Exception {

//        httpS.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
//                  .csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
//                    @Override
//                    public void customize(CsrfConfigurer<HttpSecurity> t) {
//                        
//                        t.ignoringRequestMatchers("/api/**");
//
//                    }
//                })
//                .httpBasic(Customizer.withDefaults())
//                ;
//                
//        /*
//         requestMatchers("/api/**") */
//         /*
//        httpS.authorizeHttpRequests()
//        .anyRequest().authenticated()
//        .and().httpBasic(Customizer.withDefaults())
//        .csrf().ignoringRequestMatchers("/api/**");*/
//        
//        // .ignoringRequestMatchers("/api/**")
//
//        // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS))
//
//        return httpS.build();

        httpS.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/**")
                .authenticated().anyRequest().permitAll()

        ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return httpS.build();
    }

}
