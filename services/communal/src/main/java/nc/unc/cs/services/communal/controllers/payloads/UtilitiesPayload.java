package nc.unc.cs.services.communal.controllers.payloads;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UtilitiesPayload {
    @NotNull(message = "Incorrect property ID")
    @Min(1L)
    private Long propertyId;

    @NotNull(message = "Incorrect data")
    @Min(1)
    private Integer coldWater;

    @NotNull(message = "Incorrect data")
    @Min(1)
    private Integer hotWater;

    @NotNull(message = "Incorrect data")
    @Min(1)
    private Integer electricity;
}
