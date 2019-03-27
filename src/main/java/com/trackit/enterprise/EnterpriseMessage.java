package com.trackit.enterprise;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Builder
public class EnterpriseMessage  {

    private String path;
    private String date;
    private List<EnterpriseDTO> enterprises;
}
