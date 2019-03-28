package com.trackit.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DriverMessage {
    private String path;
    private String date;
    private List<DriverDTO> drivers;
}
