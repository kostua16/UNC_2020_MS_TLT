package nc.unc.cs.services.common.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  @SuppressWarnings("lgtm[java/spring-disabled-csrf-protection]")
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().cors().and().authorizeRequests().antMatchers("/**").permitAll();
    super.configure(http);
  }
}
