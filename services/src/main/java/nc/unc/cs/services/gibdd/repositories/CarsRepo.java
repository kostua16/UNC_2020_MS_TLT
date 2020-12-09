package nc.unc.cs.services.gibdd.repositories;

import java.util.List;
import nc.unc.cs.services.gibdd.entities.Car;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("gibdd")
public interface CarsRepo extends CrudRepository<Car, String> {

    List<Car> findCarsByOwner(String owner);

    @Query("select owner, number from Car where owner = :owner")
    List<Car> findMyTestMethod(String owner);
}
