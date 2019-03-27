package com.trackit.device;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {

	@NotNull
	@NotEmpty
	private String deviceId;
	
	@Size(min=-85 , max=85 )
	private Double lat;
	
	@Size(min=-180, max=180)
	private Double lon;
	
}
