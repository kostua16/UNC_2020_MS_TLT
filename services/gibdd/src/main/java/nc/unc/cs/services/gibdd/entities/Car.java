package nc.unc.cs.services.gibdd.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

/**
 * Car entity.
 * Used to be used in this service as main entity.
 * Stored in database.
 *
 *
 * @since 0.1.0
 */
@Table(name = "cars")
@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Car {

  /**
   * ID for database record.
   */
  @Id
  private long id = Integer.MIN_VALUE;

  /**
   * Number of the car.
   */
  @NaturalId
  private String number = null;

  /**
   * Owner of the car. (Document reference)
   */
  private String owner = null;


}
