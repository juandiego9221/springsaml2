package pe.com.jdmm21.springsecurity.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import java.util.Arrays;

//SAML
//@EnableWebSecurity
//@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true)
//LDAP
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);

    //SAML
//    @Value("${security.saml2.metadata-url}")
//    String metadataUrl;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication().userDnPatterns("uid={0},ou=people")
                .userSearchBase("ou=people")
                .userSearchFilter("uid={0}")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("uniqueMember={0}")
                .contextSource(contextSource())
                .passwordCompare()
                .passwordAttribute("userPassword");
//        auth.inMemoryAuthentication().withUser("admin")
//                .password("{noop}admin")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{noop}user")
//                .credentialsExpired(true)
//                .accountExpired(true)
//                .accountLocked(true)
//                .roles("USER");

    }


    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        DefaultSpringSecurityContextSource contextSource = new
                DefaultSpringSecurityContextSource(
                Arrays.asList("ldap://localhost:8389/"), "dc=packtpub,dc=com");
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admins").hasRole("ADMINS")
                .antMatchers("/users").hasRole("USERS")
                .anyRequest().fullyAuthenticated()
                .and()
                .httpBasic();
//        http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER")
//                .and().httpBasic();
        //SAML
//        http.authorizeRequests().antMatchers("/saml/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .apply(saml())
//                .serviceProvider()
//                .keyStore()
//                .storeFilePath("saml/keystore.jks")
//                .password("12345678")
//                .keyname("spring")
//                .keyPassword("12345678")
//                .and()
//                .protocol("https")
//                .hostname("localhost:8443")
//                .basePath("/")
//                .and()
//                .identityProvider()
//                .metadataFilePath(metadataUrl)
//                .and();

    }
}
