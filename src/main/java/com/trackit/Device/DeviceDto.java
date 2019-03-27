package com.trackit.Device;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDto {

    private String deviceId;
    private Double lat;
    private Double lon;


}
