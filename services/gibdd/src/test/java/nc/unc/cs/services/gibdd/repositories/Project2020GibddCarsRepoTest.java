// package nc.unc.cs.services.gibdd.repositories;
//
// import java.util.List;
// import java.util.Optional;
// import nc.unc.cs.services.gibdd.entities.Car;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.params.ParameterizedTest;
// import org.junit.jupiter.params.provider.CsvSource;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@DataJpaTest
// class Project2020GibddCarsRepoTest {
//
//    @Autowired
//    private CarsRepo repo;
//
//    @ParameterizedTest(
//        name = "testSave({0}, {1}) = OK"
//    )
//    @CsvSource({
//        "111, user111",
//        "112, user112",
//    })
//    void testSaveAndFindById(String number, String owner) {
//        final Car car = new Car(number, owner);
//        this.repo.save(car);
//        final Optional<Car> saved = this.repo.findById(number);
//        Assertions.assertTrue(saved.isPresent());
//        Assertions.assertEquals(car, saved.get());
//    }
//
//    @ParameterizedTest(
//        name = "testSave({0}, {1}) = OK"
//    )
//    @CsvSource({
//        "111, user111",
//        "112, user112",
//    })
//    void testSaveAndFindByOwner(String number, String owner) {
//        this.repo.save(new Car(number, owner));
//        this.repo.save(new Car(number + 1, owner));
//        this.repo.save(new Car(number + 2, owner + 1));
//        this.repo.save(new Car(number + 3, owner));
//        final List<Car> list = this.repo.findCarsByOwner(owner);
//        Assertions.assertEquals(3, list.size());
//        for (Car found : list) {
//            Assertions.assertEquals(owner, found.getOwner());
//        }
//    }
//
//}
