package io.wisoft.iotplatform.device;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ApiServerApplicationController {

  @GetMapping(value = "/")
  public String index() {
    final String url = "/swagger-ui.html";
    return "redirect:" + url;
  }

}