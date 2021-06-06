package io.wisoft.iotplatform.fixture;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.wisoft.iotplatform.model.service.dto.ModelDto.*;
import static io.wisoft.iotplatform.model.service.dto.ModelTypeDto.*;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fixtures {


  public static ModelTypeRegister registerModelTypeDto() {
    return new ModelTypeRegister().builder()
                                  .name("testModelType")
                                  .description("테스트 모델 타입입니다.")
                                  .build();
  }

  public static ModelRegister registerModelDto() {
    return new ModelRegister().builder().name("testModel").description("테스트 모델입니다.").build();
  }



}
