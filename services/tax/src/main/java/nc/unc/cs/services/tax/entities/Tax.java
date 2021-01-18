package nc.unc.cs.services.tax.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data // for POJO
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // стороний класс для генерации id
    private Long taxId;

    @NonNull
    private Integer taxAmount;

    @NonNull
    private Boolean status;

    //    @Column(updatable = false)
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

    public Tax(
        final Integer taxAmount,
        final Long citizenId,
        final Long serviceId
    ) {
        this.taxAmount = taxAmount;
        this.status = false;
        this.creationDate = new Date(); // заглушка
        this.taxPaymentDate = null;
        this.citizenId = citizenId;
        this.serviceId = serviceId;
    }

}
