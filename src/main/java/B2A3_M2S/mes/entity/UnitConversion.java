package B2A3_M2S.mes.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "unit_conversion")
@Table(name = "unit_conversion")
@AllArgsConstructor
@NoArgsConstructor
public class UnitConversion extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromUnit;
    private String toUnit;
    private double conversionFactor;


    /**외래키**/


}
