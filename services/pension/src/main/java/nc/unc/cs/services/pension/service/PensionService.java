package nc.unc.cs.services.pension.service;
import nc.unc.cs.services.pension.model.Pension;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PensionService {
    public ResponseEntity<Pension> CreatePension(Pension pens){
        Pension pension = new Pension();
        pension.setAmountOfPension(pens.getAmountOfPension());
        pension.setDomesticId(pens.getDomesticId());
        pension.setCitizenId(pens.getCitizenId());
        return ResponseEntity.ok(pension);
        }
}

