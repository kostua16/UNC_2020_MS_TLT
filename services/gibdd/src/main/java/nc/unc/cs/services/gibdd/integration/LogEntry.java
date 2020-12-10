package nc.unc.cs.services.gibdd.integration;

import java.util.Date;
import javax.validation.constraints.Size;

public class LogEntry {

    private long id;

    private Date created;

    @Size(min = 1, max = 10)
    private String service;

    @Size(min = 1, max = 200)
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogEntry() {
    }

    public LogEntry(
        Date created,
        @Size(min = 1, max = 10) String service,
        @Size(min = 1, max = 200) String message
    ) {
        this.created = created;
        this.service = service;
        this.message = message;
    }
}
