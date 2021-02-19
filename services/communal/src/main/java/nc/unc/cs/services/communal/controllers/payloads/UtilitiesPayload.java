package nc.unc.cs.services.communal.controllers.payloads;

import lombok.Data;
import lombok.NonNull;

@Data
public class UtilitiesPayload {
    @NonNull
    private Long propertyId;
    @NonNull
    private Integer coldWater;
    @NonNull
    private Integer hotWater;
    @NonNull
    private Integer electricity;
}
