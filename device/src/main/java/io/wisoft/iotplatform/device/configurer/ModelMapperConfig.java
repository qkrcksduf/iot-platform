package io.wisoft.iotplatform.device.configurer;

import io.wisoft.iotplatform.device.domain.Actuator;
import io.wisoft.iotplatform.device.domain.Sensor;
import io.wisoft.iotplatform.device.service.dto.ActuatorDto.ActuatorResponse;
import io.wisoft.iotplatform.device.service.dto.SensorDto.SensorResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    modelMapper.addMappings(new PropertyMap<Sensor, SensorResponse>() {
      protected void configure() {
        map().setDeviceId(source.getDevice().getId());
      }
    });

    modelMapper.addMappings(new PropertyMap<Actuator, ActuatorResponse>() {
      protected void configure() {
        map().setDeviceId(source.getDevice().getId());
      }
    });

    return modelMapper;
  }

}
