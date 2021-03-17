package nc.unc.cs.services.common.clients.logging;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class LogEntry {

  private long id;

  private Date created;

  @Size(min = 1, max = 10)
  private String service;

  @Size(min = 1, max = 4000)
  private String message;
}
