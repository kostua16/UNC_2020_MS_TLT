package nc.unc.cs.services.bank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentRequestId;

    @NonNull
    @Column(nullable = true)
    private Long serviceId;

    @NonNull
    @Column(nullable = true)
    private Long citizenId;

    @NonNull
    @Column(nullable = true)
    private Boolean status;

    @NonNull
    @Column(nullable = true)
    private Integer amount;

    private Long taxId;
}
