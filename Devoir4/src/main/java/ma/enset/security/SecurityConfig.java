package ma.enset.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//classe de configuration
@Configuration
//activation de security web
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password("{noop}1234").roles("USER");
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}1234").roles("USER","ADMIN");
        auth.inMemoryAuthentication()
                .withUser("user2").password("{noop}1234").roles("USER");

                }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       //specifier les droits d'acces
       // form personalise : http.formLogin().loginPage("/login");
        //form par defaut
        http.formLogin();
        //Droit d'acces
        //toute requetes necessite une authentification
        http.authorizeRequests().anyRequest().authenticated();

    }
}
