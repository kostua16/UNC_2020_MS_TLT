package nc.unc.cs.services.common.clients.logging;

import java.util.Date;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

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
