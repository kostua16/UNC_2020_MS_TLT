package nc.unc.cs.services.common.clients.tax;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Tax {

    private Long taxId;

    @NonNull
    private Integer taxAmount;

    @NonNull
    private Boolean status;

    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;

    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date taxPaymentDate;

    @NonNull
    private Long citizenId;

    @NonNull
    private Long serviceId;
}
