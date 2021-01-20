package nc.unc.cs.services.communal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    @NonNull
    @Column(nullable = false, length = 40)
    private String state;

    @NonNull
    @Column(nullable = false, length = 40)
    private String city;

    @NonNull
    @Column(nullable = false, length = 40)
    private String street;

    @NonNull
    @Column(nullable = false, length = 10)
    private String house;

    @NonNull
    @Column(nullable = false, length = 10)
    private String apartment;

    @NonNull
    @Column(nullable = false)
    private Boolean isActive;

    @NonNull
    @Column(nullable = false)
    private Long citizenId;
}
