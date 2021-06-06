package io.wisoft.iotplatform.actuating.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.iotplatform.actuating.domain.Device;
import io.wisoft.iotplatform.actuating.domain.Device.DeviceStatus;
import io.wisoft.iotplatform.actuating.eventsourcing.fixture.Fixtures;
import io.wisoft.iotplatform.actuating.service.DeviceService;
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

import static io.wisoft.iotplatform.actuating.service.dto.DeviceDto.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.hamcrest.core.Is.is;
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
class DeviceControllerTest {

  private static final String API_V1_BASE_URI = "/devices/";

  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired private DeviceService deviceService;
  @Autowired private ObjectMapper objectMapper;

  
  private DeviceRegister registerDeviceDto;
  private MockMvc mockMvc;

  @BeforeAll
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    
    this.registerDeviceDto = Fixtures.registerDeviceDto();
    this.registerDeviceDto.setCncId(2L);

//    final String url = "/api/v1/actuatings";
//    stubFor(WireMock.post(urlEqualTo(url)).willReturn(aResponse().withStatus(200)
//        .withHeader("Content-Type", "application/json; charset=utf-8")
//        .withBody("14")));
  }

  @Test
  public void register() throws Exception {
    // Given & When
    final ResultActions resultActions = this.mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerDeviceDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.name", is("testDevice")));
  }

  @Test
  public void registerBadRequest() throws Exception {
    // Given
    this.registerDeviceDto.setName(" ");
    this.registerDeviceDto.setIpAddress(" ");
    this.registerDeviceDto.setIpAddress(" ");
    this.registerDeviceDto.setLocation("N501");
    this.registerDeviceDto.setCncId(2L);

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerDeviceDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("DeviceController-E001")));
  }

  @Test
  public void registerDuplicatedName() throws Exception {
    // Given
    this.mockMvc.perform(post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(this.registerDeviceDto)));

    final DeviceRegister newRegisterDeviceDto = Fixtures.registerDeviceDto();
    newRegisterDeviceDto.setCncId(2L);

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterDeviceDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("DeviceController-X001")));
  }

  @Test
  public void registerDuplicatedIpAddress() throws Exception {
    // Given
    this.mockMvc.perform(post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(this.registerDeviceDto)));

    final DeviceRegister newRegisterDeviceDto = Fixtures.registerDeviceDto();
    newRegisterDeviceDto.setName("testDevice2");
    newRegisterDeviceDto.setCncId(2L);

    // When
    final ResultActions resultActions = this.mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterDeviceDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("DeviceController-X001")));
  }

  @Test
  public void update() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    final DeviceUpdate updateDto = new DeviceUpdate();
    updateDto.setName("deviceName");
    updateDto.setLocation("location");
    updateDto.setBattery(50);
    updateDto.setIpAddress("123.123.123.123");
    updateDto.setProtocol(device.getProtocol());
    updateDto.setSleep(device.isSleep());
    updateDto.setDescription("블라블라블라");
    updateDto.setStatus(device.getStatus());
    updateDto.setCncId(2L);

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + device.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.location", is("location")));
  }

  @Test
  public void updateBadRequest() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    final DeviceUpdate updateDto = new DeviceUpdate();
    updateDto.setName(" ");
    updateDto.setLocation(" ");
    updateDto.setCncId(2L);

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + device.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("DeviceController-E002")));
  }

  @Test
  public void initialize() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    final DeviceInitialize initializeDto = new DeviceInitialize();
    initializeDto.setIpAddress("123.123.123.123");
    initializeDto.setStatus(DeviceStatus.ACTIVE);

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + device.getId() + "/initialization")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(initializeDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.status", is("ACTIVE")));
  }

  @Test
  public void initializeBadRequest() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    final DeviceInitialize initializeDto = new DeviceInitialize();
    initializeDto.setIpAddress(" ");
    initializeDto.setStatus(DeviceStatus.ACTIVE);

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + device.getId() + "/initialization")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(initializeDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("DeviceController-E003")));
  }

  @Test
  public void remove() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    // When
    final ResultActions resultActions = mockMvc.perform(
        delete(API_V1_BASE_URI + device.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isNoContent());
  }

  @Test
  public void removeBadRequest() throws Exception {
    // Given
    final String id = "1000";

    // When
    final ResultActions resultActions = mockMvc.perform(delete(API_V1_BASE_URI + id));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void getDevice() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI + device.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testDevice")));
  }

  @Test
  public void getDeviceByName() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    // When
    final String uri = API_V1_BASE_URI + "?name=" + device.getName();
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testDevice")));
  }

  @Test
  public void getDeviceByNameNotFound() throws Exception {
    // Given
    this.registerDeviceDto.setLocation("N5-503");
    this.registerDeviceDto.setName("test-device");
    deviceService.register(this.registerDeviceDto);
    final String deviceName = "testDevice2";

    // When
    final String uri = API_V1_BASE_URI + "?name=" + deviceName;
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("DeviceController-X002")));
  }

  @Test
  public void getDeviceBySerial() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    // When
    final String uri = API_V1_BASE_URI + "search/bySerial?serial=" + device.getSerial();
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testDevice")));
  }

  @Test
  public void getDeviceBySerialNotFound() throws Exception {
    // Given
    deviceService.register(this.registerDeviceDto);
    final String deviceSerial = "testSn2";

    // When
    final String uri = API_V1_BASE_URI + "search/bySerial?serial=" + deviceSerial;
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("DeviceController-X002")));
  }

  @Test
  public void getSensorsById() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    // When
    final String uri = API_V1_BASE_URI + device.getId() + "/sensors";
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void getActuatorsById() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    // When
    final String uri = API_V1_BASE_URI + device.getId() + "/actuators";
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void getDevices() throws Exception {
    // Given
    deviceService.register(this.registerDeviceDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
  }
  
}