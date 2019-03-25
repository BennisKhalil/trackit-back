package com.trackit.Device;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDto {

    private String deviceId;
    private Float lat;
    private Float lon;


}
