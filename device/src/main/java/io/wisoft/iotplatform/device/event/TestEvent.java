package io.wisoft.iotplatform.device.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class TestEvent {

    private UUID id;

}
