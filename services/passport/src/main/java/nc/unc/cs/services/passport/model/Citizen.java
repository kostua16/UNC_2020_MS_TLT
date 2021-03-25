package nc.unc.cs.services.passport.model;

import lombok.Data;

import java.util.Date;

@Data
public class Citizen {
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String registration;
    private Long citizenId;
}
