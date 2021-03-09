package nc.unc.cs.services.communal.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UtilityBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long utilityBillId;

    @NotNull(message = "Date is null")
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    @NotNull
    @Column(nullable = false)
    private Boolean isPaid;

    @NotNull(message = "Incorrect data")
    @Min(value = 1, message = "Incorrect data size")
    @Column(nullable = false, updatable = false)
    private Integer coldWater;

    @NotNull(message = "Incorrect data")
    @Min(value = 1, message = "Incorrect data size")
    @Column(nullable = false, updatable = false)
    private Integer hotWater;

    @NotNull(message = "Incorrect data")
    @Min(value = 1, message = "Incorrect data size")
    @Column(nullable = false, updatable = false)
    private Integer electricity;

    @NotNull(message = "Incorrect data")
    @Min(value = 1, message = "Incorrect data size")
    @Column(nullable = false, updatable = false)
    private Integer coldWaterAmount;

    @NotNull(message = "Incorrect data")
    @Min(value = 1, message = "Incorrect data size")
    @Column(nullable = false, updatable = false)
    private Integer hotWaterAmount;

    @NotNull(message = "Incorrect data")
    @Min(value = 1, message = "Incorrect data size")
    @Column(nullable = false, updatable = false)
    private Integer electricityAmount;

    @NotNull(message = "Incorrect data")
    @Min(value = 1, message = "Incorrect data size")
    @Column(nullable = false, updatable = false)
    private Integer utilityAmount;

    @NotNull(message = "Incorrect payment request ID")
    @Min(1L)
    @Column(nullable = false, updatable = false)
    private Long paymentRequestId;

    @NotNull(message = "Incorrect property ID")
    @Min(1L)
    @Column(nullable = false, updatable = false)
    private Long propertyId;

    @NotNull(message = "Incorrect citizen ID")
    @Min(1L)
    @Column(nullable = false, updatable = false)
    private Long citizenId;
}
