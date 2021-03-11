package nc.unc.cs.services.gibdd.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.hibernate.annotations.NaturalId;

/**
 * DTO for Car entity.
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

  @Id
  private long id = Integer.MIN_VALUE;

  @NaturalId
  private String number = null;

  private String owner = null;


}
