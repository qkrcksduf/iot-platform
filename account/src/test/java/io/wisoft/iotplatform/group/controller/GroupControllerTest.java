package io.wisoft.iotplatform.group.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.service.GroupService;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupUpdate;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupRegister;
import org.hamcrest.CoreMatchers;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static io.wisoft.iotplatform.fixtures.Fixtures.registerGroupDto;
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
class GroupControllerTest {

  private static final String API_V1_BASE_URI = "/groups/";

  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private GroupService groupService;
  @Autowired
  private ObjectMapper objectMapper;

  private RestTemplate restTemplate;

  private GroupRegister registerGroupDto;
  private MockMvc mockMvc;

  public GroupControllerTest() {
    this.restTemplate = new RestTemplate();
  }

  @BeforeAll
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    this.registerGroupDto = registerGroupDto();
  }

  @Test
  public void register() throws Exception {
    // Given & When
    final ResultActions resultActions;
    resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerGroupDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.name", is("wisoft_test")));
  }

  @Test
  public void registerBadRequest() throws Exception {
    // Given
    this.registerGroupDto.setName(" ");

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerGroupDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", CoreMatchers.is("GroupController-E001")));
  }

  @Test
  public void registerDuplicatedName() throws Exception {
    // Given
    mockMvc.perform(post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(
            this.registerGroupDto)));

    final GroupRegister newRegisterGroupDto = registerGroupDto();

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterGroupDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", CoreMatchers.is("GroupController-X001")));
  }

  @Test
  public void update() throws Exception {
    // Given
    final Group group = groupService.register(this.registerGroupDto);

    final GroupUpdate updateDto = new GroupUpdate();
    updateDto.setName("deviceName");
    updateDto.setDescription("블라블라블라");

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + group.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", CoreMatchers.is("deviceName")));
  }

  @Test
  public void updateBadRequest() throws Exception {
    // Given
    final Group group = groupService.register(this.registerGroupDto);

    final GroupUpdate updateDto = new GroupUpdate();
    updateDto.setName(" ");

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + group.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", CoreMatchers.is("GroupController-E002")));
  }

  @Test
  public void remove() throws Exception {
    // Given
    final Group group = groupService.register(this.registerGroupDto);

    // When
    final ResultActions resultActions = mockMvc.perform(delete(API_V1_BASE_URI + group.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isNoContent());
  }

  @Test
  public void removeBadRequest() throws Exception {
    // Given
    final String groupId = "1000";

    // When
    final ResultActions resultActions = mockMvc.perform(delete(API_V1_BASE_URI + groupId));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void getGroup() throws Exception {
    // Given
    final Group group = groupService.register(this.registerGroupDto);

    // When
    final ResultActions result = mockMvc.perform(get(API_V1_BASE_URI + group.getId()));

    // Then
    result.andDo(print());
    result.andExpect(status().isOk());
  }

  @Test
  public void getGroupCncs() throws Exception {
    // Given
    final String groupId = "1";

    // When
    final String requestAddress = API_V1_BASE_URI + groupId + "/cncs/";
    final ResultActions resultActions = mockMvc.perform(get(requestAddress));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.cnc[0].name", is("CNC NO1")));
  }

  @Test
  public void getGroups() throws Exception {
    // Given
    groupService.register(this.registerGroupDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
  }

}