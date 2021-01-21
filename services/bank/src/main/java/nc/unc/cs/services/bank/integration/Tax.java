package nc.unc.cs.services.bank.integration;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;

@Data
public class Tax {

    private Long taxId;

    @NonNull
    private Integer taxAmount;

    @NonNull
    private Boolean status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date taxPaymentDate;

    @NonNull
    private Long citizenId;

    @NonNull
    private Long serviceId;
}
