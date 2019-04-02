package com.trackit.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CarMessage {
    private String path;
    private String date;
    private List<CarDTO> cars;
}
