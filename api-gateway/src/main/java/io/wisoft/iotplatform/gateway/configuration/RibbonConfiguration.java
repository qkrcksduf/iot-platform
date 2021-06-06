package io.wisoft.iotplatform.gateway.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.context.annotation.Bean;


public class RibbonConfiguration {

  @Bean
  public IPing ribbonPing(final IClientConfig config) {
    // BOOT2: /health 에서 /actuator/health 로 변경
    return new PingUrl(false, "/actuator/health");
  }

  @Bean
  public IRule ribbonRule(final IClientConfig config) {
    return new AvailabilityFilteringRule();
  }


}
