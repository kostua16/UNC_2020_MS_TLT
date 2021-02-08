package nc.unc.cs.services.pension.service;
import nc.unc.cs.services.pension.model.Pension;
import nc.unc.cs.services.pension.repository.PensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PensionService {
    @Autowired
    private PensionRepository pensionRepository;
    public ResponseEntity<Pension> createPension(Pension pens){
       this.pensionRepository.save(pens);
       return ResponseEntity.ok(pens);
    }
}

