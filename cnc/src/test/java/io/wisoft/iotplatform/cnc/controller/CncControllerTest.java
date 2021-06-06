package io.wisoft.iotplatform.cnc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.iotplatform.cnc.domain.Cnc;
import io.wisoft.iotplatform.cnc.domain.CncStatus;
import io.wisoft.iotplatform.cnc.service.CncService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static io.wisoft.iotplatform.cnc.fixtures.Fixtures.registerCncDto;
import static io.wisoft.iotplatform.cnc.service.dto.CncDto.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CncControllerTest {

  private static final String API_V1_BASE_URI = "/cncs/";

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private CncService cncService;

  @Autowired
  private ObjectMapper objectMapper;

  private CncRegister registerCncDto;
  private MockMvc mockMvc;

  @BeforeAll
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    this.registerCncDto = registerCncDto();
    this.registerCncDto.setGroupId(UUID.randomUUID());

//    this.registerCncDto = CncRegister.builder()
//        .serial("C000003")
//        .name("testCnC")
//        .location("Test cnc")
//        .status(CncStatus.ACTIVE)
//        .ipAddress("192.168.10.254")
//        .description("Test용 cnc입니다.")
//        .groupId(2L).build();
  }

  @Test
  public void register() throws Exception {
    // Given & When
    final ResultActions resultActions = this.mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerCncDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.name", is("testCnC")));
  }

  @Test
  public void registerBadRequest() throws Exception {
    // Given
    this.registerCncDto.setIpAddress(" ");
    this.registerCncDto.setLocation("N501");

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerCncDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("CncController-E001")));
  }

  @Test
  public void registerDuplicatedName() throws Exception {
    // Given
    this.mockMvc.perform(post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(
            this.registerCncDto)));

    final CncRegister newRegisterCncDto = registerCncDto();
    newRegisterCncDto.setGroupId(this.registerCncDto.getGroupId());

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterCncDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("CncController-X001")));
  }

  @Test
  public void registerDuplicatedIpAddress() throws Exception {
    // Given
    this.mockMvc.perform(post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(
            this.registerCncDto)));

    final CncRegister newRegisterCncDto = registerCncDto();
    newRegisterCncDto.setName("testCnCSn2111");
    newRegisterCncDto.setGroupId(this.registerCncDto.getGroupId());

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterCncDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("CncController-X001")));
  }

  @Test
  public void update() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    final CncUpdate updateDto = new CncUpdate();
    updateDto.setName("CnC Name");
    updateDto.setIpAddress("123.123.123.123");
    updateDto.setLocation("location");
    updateDto.setDescription("블라블라블라");
    updateDto.setStatus(cnc.getStatus());

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        put(API_V1_BASE_URI + cnc.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.location", is("location")));
  }

  @Test
  public void updateBadRequest() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    final CncUpdate updateDto = new CncUpdate();
    updateDto.setName(" ");
    updateDto.setLocation(" ");

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        put(API_V1_BASE_URI + cnc.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("CncController-E002")));
  }

  @Test
  public void initialize() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    final CncInitialize dto = new CncInitialize();
    dto.setIpAddress("123.123.123.123");
    dto.setStatus(CncStatus.ACTIVE);

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        put(API_V1_BASE_URI + cnc.getId() + "/initialization")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.status", is("ACTIVE")));
  }

  @Test
  public void initializeBadRequest() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    final CncInitialize dto = new CncInitialize();
    dto.setIpAddress(" ");
    dto.setStatus(CncStatus.ACTIVE);

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        put(API_V1_BASE_URI + cnc.getId() + "/initialization")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("CncController-E003")));
  }

  @Test
  public void removeCnc() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    // When
    final ResultActions resultActions = mockMvc.perform(delete(API_V1_BASE_URI + cnc.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isNoContent());
  }

  @Test
  public void removeBadRequest() throws Exception {
    // Given
    final String cncId = "1000";

    // When
    final ResultActions resultActions = mockMvc.perform(delete(API_V1_BASE_URI + cncId));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void getCnc() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI + cnc.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testCnC")));
  }

  @Test
  public void getCncByName() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    // When
    final String uri = API_V1_BASE_URI + "?name=" + cnc.getName();
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testCnC")));
  }

  @Test
  public void getCncByNameNotFound() throws Exception {
    // Given
    cncService.register(this.registerCncDto);
    final String cncName = "testCnC2";

    // When
    final String uri = API_V1_BASE_URI + "?name=" + cncName;
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("CncController-X002")));
  }

  @Test
  public void getCncBySerial() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    // When
    final String uri = API_V1_BASE_URI + "search/bySerial?serial=" + cnc.getSerial();
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testCnC")));
  }

  @Test
  public void getCncBySerialNotFound() throws Exception {
    // Given
    cncService.register(this.registerCncDto);
    final String cncSerial = "testCncSn2";

    // When
    final String uri = API_V1_BASE_URI + "search/bySerial?serial=" + cncSerial;
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("CncController-X002")));
  }

  @Test
  public void getCncDevices() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    // When
    final String uri = API_V1_BASE_URI + cnc.getId() + "/devices";
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
  }

  @Test
  public void getCncs() throws Exception {
    // Given
    cncService.register(this.registerCncDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
  }

}