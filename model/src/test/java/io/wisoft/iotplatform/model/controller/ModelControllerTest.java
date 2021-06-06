package io.wisoft.iotplatform.model.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.iotplatform.model.domain.Model;
import io.wisoft.iotplatform.model.service.ModelService;
import io.wisoft.iotplatform.model.domain.ModelType;
import io.wisoft.iotplatform.model.service.ModelTypeService;
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

import static io.wisoft.iotplatform.fixture.Fixtures.registerModelDto;
import static io.wisoft.iotplatform.fixture.Fixtures.registerModelTypeDto;
import static io.wisoft.iotplatform.model.service.dto.ModelDto.*;
import static io.wisoft.iotplatform.model.service.dto.ModelTypeDto.*;
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
class ModelControllerTest {
  private static final String API_V1_BASE_URI = "/models/";

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private ModelService modelService;

  @Autowired
  private ModelTypeService modelTypeService;

  @Autowired
  private ObjectMapper objectMapper;

  private ModelType modelType;
  private ModelRegister registerModelDto;
  private MockMvc mockMvc;

  @BeforeAll
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    final ModelTypeRegister registerModelTypeDto = registerModelTypeDto();
    this.registerModelDto = registerModelDto();

    this.modelType = modelTypeService.register(registerModelTypeDto);
    this.registerModelDto.setModelTypeId(this.modelType.getId());
  }

  @Test
  public void register() throws Exception {
    // Given & When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerModelDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.name", is("testModel")));
  }

  @Test
  public void registerBadRequest() throws Exception {
    // Given
    this.registerModelDto.setName(" ");

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerModelDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("ModelController-E001")));
  }

  @Test
  public void registerDuplicatedName() throws Exception {
    // Given
    mockMvc.perform(post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(
            this.registerModelDto)));

    final ModelRegister newRegisterModelDto = registerModelDto();
    newRegisterModelDto.setModelTypeId(this.modelType.getId());

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterModelDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("ModelController-X001")));
  }

  @Test
  public void update() throws Exception {
    // Given
    final Model model = modelService.register(this.registerModelDto);

    final ModelUpdate updateDto = new ModelUpdate();
    updateDto.setName("modelName");
    updateDto.setModelTypeId(this.modelType.getId());
    updateDto.setDescription("블라블라블라");

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + model.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("modelName")));
  }

  @Test
  public void updateBadRequest() throws Exception {
    // Given
    final Model model = modelService.register(this.registerModelDto);

    final ModelUpdate updateDto = new ModelUpdate();
    updateDto.setName(" ");
    updateDto.setModelTypeId(this.modelType.getId());

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + model.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("ModelController-E002")));
  }

  @Test
  public void remove() throws Exception {
    // Given
    final Model model = modelService.register(this.registerModelDto);

    // When
    final ResultActions resultActions = mockMvc.perform(delete(API_V1_BASE_URI + model.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isNoContent());
  }

  @Test
  public void removeBadRequest() throws Exception {
    // Given
    final String modelId = "1000";

    // When
    final ResultActions resultActions = mockMvc.perform(delete(API_V1_BASE_URI + modelId));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void getModel() throws Exception {
    // Given
    this.registerModelDto.setName("testModel");
    final Model model = modelService.register(this.registerModelDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI + model.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testModel")));
  }

  @Test
  public void getModelByName() throws Exception {
    // Given
    final Model model = modelService.register(this.registerModelDto);

    // When
    final String uri = API_V1_BASE_URI + "?name=" + model.getName();
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testModel")));
  }

  @Test
  public void getModelByNameNotFound() throws Exception {
    // Given
    modelService.register(this.registerModelDto);
    final String modelName = "testModel10";

    // When
    final String uri = API_V1_BASE_URI + "?name=" + modelName;
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("ModelController-X002")));
  }

  @Test
  public void getModels() throws Exception {
    // Given
    modelService.register(this.registerModelDto);

    // When
    final ResultActions result = mockMvc.perform(get(API_V1_BASE_URI));

    // Then
    result.andDo(print());
    result.andExpect(status().isOk());
  }
}