package nc.unc.cs.services.bank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Transaction {

    @Builder
    public Transaction(
        final Long transactionId,
        final Integer amount,
        final Long paymentRequestId,
        final Long citizenId
    ) {
        this.transactionId = transactionId;
        this.creationDate = new Date();
        this.amount = amount;
        this.paymentRequestId = paymentRequestId;
        this.citizenId = citizenId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    @NotNull
    private Date creationDate;

    @NotNull
    @Min(value = 1, message = "Incorrect payment amount")
    @Column(nullable = false, updatable = false)
    private Integer amount;

    @NotNull
    @Min(value = 1L, message = "Incorrect payment request ID")
    @Column(nullable = false, updatable = false)
    private Long paymentRequestId;

    @NotNull
    @Min(value = 1L, message = "Incorrect citizen ID")
    @Column(nullable = false, updatable = false)
    private Long citizenId;
}
