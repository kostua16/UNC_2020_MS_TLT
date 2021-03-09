package nc.unc.cs.services.logging.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "logs")
@Data
public class LogEntry {

  @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

  private Date created;

  @Size(min = 1, max = 10) private String service;

  @Size(min = 1, max = 4000) private String message;
}
