package patterns.backend;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationWaifuMarketDesu {
  @Bean
  public TestRestTemplate testRestTemplate() {
    return new TestRestTemplate();
  }
}
