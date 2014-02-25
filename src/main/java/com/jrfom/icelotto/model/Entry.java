package com.jrfom.icelotto.model;

import javax.persistence.*;

import com.jrfom.icelotto.jpa.converters.InstantConverter;
import org.threeten.bp.Instant;

@Entity
@Table(name = "entries")
public class Entry {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(referencedColumnName = "id")
  private User user;

  @Column
  private Integer amount;

  @Column(columnDefinition = "INTEGER")
  @Convert(converter = InstantConverter.class)
  private Instant enteredDate;

  protected Entry() {}

  public Entry(User user, Integer amount) {
    this.user = user;
    this.amount = amount;
    this.enteredDate = Instant.now();
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getAmount() {
    return this.amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Instant getEnteredDate() {
    return enteredDate;
  }

  public void setEnteredDate(Instant enteredDate) {
    this.enteredDate = enteredDate;
  }
}