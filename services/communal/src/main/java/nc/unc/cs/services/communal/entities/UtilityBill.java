package nc.unc.cs.services.communal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// Все цены на коммунальные услуги в копейках
public class UtilityBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long utilityBillId;

    @NonNull
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    @NonNull
    @Column(nullable = false)
    private Boolean isPaid;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Integer coldWater;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Integer hotWater;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Integer electricity;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Integer coldWaterAmount;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Integer hotWaterAmount;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Integer electricityAmount;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Long propertyId;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Long citizenId;
}
