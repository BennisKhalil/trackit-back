package com.trackit.driver;

import com.trackit.car.Car;
import com.trackit.enterprise.Enterprise;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDate birthDay;

    private LocalDate employedDate;

    @OneToOne(mappedBy = "driver", fetch = FetchType.EAGER)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

}
