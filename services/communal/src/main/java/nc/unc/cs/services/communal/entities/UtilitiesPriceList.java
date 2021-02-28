package nc.unc.cs.services.communal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UtilitiesPriceList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long utilitiesPriceListId;

  @NonNull
  @Column(nullable = false, unique = true, updatable = false, length = 40)
  private String region;

  @NonNull @Column(nullable = false) private Integer coldWaterPrice;

  @NonNull @Column(nullable = false) private Integer hotWaterPrice;

  @NonNull @Column(nullable = false) private Integer electricityPrice;

  public void setRegion(String region) {
    this.region = region.trim().toUpperCase();
  }
}
