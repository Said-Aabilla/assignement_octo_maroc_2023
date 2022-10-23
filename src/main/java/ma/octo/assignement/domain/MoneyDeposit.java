package ma.octo.assignement.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "DEPOSIT")
public class MoneyDeposit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(precision = 16, scale = 2, nullable = false)
  private BigDecimal Montant;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateExecution;

  @Column
  private String fullNameEmetteur;

  @ManyToOne
  private Compte compteBeneficiaire;

  @Column(length = 200)
  private String motif;

  public BigDecimal getMontant() {
    return Montant;
  }

  public void setMontant(BigDecimal montant) {
    this.Montant = montant;
  }

  public Date getDateExecution() {
    return dateExecution;
  }

  public void setDateExecution(Date dateExecution) {
    this.dateExecution = dateExecution;
  }

  public Compte getCompteBeneficiaire() {
    return compteBeneficiaire;
  }

  public void setCompteBeneficiaire(Compte compteBeneficiaire) {
    this.compteBeneficiaire = compteBeneficiaire;
  }

  public String getMotif() {
    return motif;
  }

  public void setMotif(String motif) {
    this.motif = motif;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getfullNameEmetteur() {
    return fullNameEmetteur;
  }

  public void setfullNameEmetteur(String fullNameEmetteur) {
    this.fullNameEmetteur = fullNameEmetteur;
  }
}
