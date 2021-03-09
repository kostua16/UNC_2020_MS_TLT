package nc.unc.cs.services.common.clients.tax;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Tax {

    @NotNull(message = "Incorrect tax ID")
    @Min(1L)
    private Long taxId;

    @NotNull(message = "Incorrect tax amount")
    @Min(1)
    private Integer taxAmount;

    @NotNull private Boolean status;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date taxPaymentDate;

    @NotNull(message = "Incorrect citizen ID")
    @Min(1L)
    private Long citizenId;

    @NotNull(message = "Incorrect service ID")
    @Min(1L)
    private Long serviceId;
}
