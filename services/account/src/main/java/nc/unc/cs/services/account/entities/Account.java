package nc.unc.cs.services.account.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long citizenId;

  @NotBlank
  @Size(min = 5, max = 20, message = "Incorrect username size!")
  @Column(nullable = false, updatable = false, unique = true)
  private String username;

  @NotBlank
  @Size(min = 5, message = "Incorrect password size!")
  @Column(nullable = false)
  private String password;

  @NotNull
  @Column(nullable = false)
  private Boolean isActive;

  @NotNull
  @Column(nullable = false)
  private Roles role;

  @Builder
  public Account(final Long citizenId, final String username, final String password) {
    this.citizenId = citizenId;
    this.username = username.trim();
    this.password = password.trim();
    this.isActive = false;
    this.role = Roles.ROLE_USER;
  }

  public void setUsername(final String username) {
    this.username = username.trim();
  }

  public void setPassword(final String password) {
    this.password = password.trim();
  }
}
