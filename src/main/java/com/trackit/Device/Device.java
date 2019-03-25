package com.trackit.Device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="device_data",type="device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id

    @JsonProperty("device_id")

    private String deviceId;

    @JsonProperty("at_time")

    private String atTime;

    @JsonProperty("mil_status")

    private Integer milStatus;

    @JsonProperty("fuel_system_status")

    private Integer fuelSystemStatus;

    @JsonProperty("engine_load")

    private Integer engineLoad;

    @JsonProperty("engine_coolant_temperature")

    private Integer engineCoolantTemperature;

    @JsonProperty("short_term_fuel_trim_bank_1")

    private Integer shortTermFuelTrimBank1;

    @JsonProperty("long_term_fuel_trim_bank_1")

    private Integer longTermFuelTrimBank1;

    @JsonProperty("intake_manifold_pressure")

    private Integer intakeManifoldPressure;

    @JsonProperty("engine_rpm")

    private Integer engineRpm;

    @JsonProperty("vehicle_speed")

    private Integer vehiculeSpeed;

    @JsonProperty("timing_advance")

    private Integer timingAdvance;

    @JsonProperty("intake_air_temperature")

    private Integer intakeAirTemperature;

    @JsonProperty("maf_air_flow_rate")

    private Integer mafAirFlowRate;

    @JsonProperty("throttle")

    private Integer throttle;

    @JsonProperty("o2_v_6")

    private Integer o2V6;

    @JsonProperty("o2_t_6")

    private Integer o2T6;

    @JsonProperty("distance_traveled_with_mil_on")

    private Integer distanceTraveledWithMilOn;

    @JsonProperty("commanded_evaporative_purge")

    private Integer commandedEvaporativePurge;

    @JsonProperty("fuel_tank_level_input")

    private Integer fuelTankLevelInput;

    @JsonProperty("warm_ups_since_codes_cleared")

    private Integer warmUpsSinceCodesCleared;

    @JsonProperty("distance_traveled_since_codes_cleared")

    private Integer distanceTraveledSinceCodesCleared;

    @JsonProperty("evaporator_system_pressure")

    private Integer evaporatorSystemPressure;

    @JsonProperty("absolute_barometric_pressure")

    private Integer absoluteBarometricPressure;

    @JsonProperty("o2_f_a_e_r_c_1_fuel")

    private Integer o2FAERC1Fuel;

    @JsonProperty("catalyst_temperature_bank1_sensor1")

    private Integer catalystTemperatureBank1Sensor1;

    @JsonProperty("control_module_voltage")

    private Integer controlModuleVoltage;

    @JsonProperty("absolute_load_value")

    private Integer absoluteLoadValue;

    @JsonProperty("fuel_air_commanded_equiv_ratio")

    private Integer fuelAirCommandedEquivRatio;

    @JsonProperty("relative_throttle_position")

    private Integer relativeThrottlePosition;

    @JsonProperty("ambient_air_temperature")

    private Integer ambientAirTemperature;

    @JsonProperty("absolute_throttle_b")

    private Integer absoluteThrottleB;

    @JsonProperty("accelerator_pedal_position_d")

    private Integer acceleratorPedalPositionD;

    @JsonProperty("accelerator_pedal_position_e")

    private Integer acceleratorPedalPositionE;

    @JsonProperty("fuel_type")

    private Integer fuelType;

    @JsonProperty("fuel_rail_absolute_pressure")

    private Integer fuelRailAbsolutePressure;


    private Float lat;

    @JsonProperty("long")

    private Float lon;

}
