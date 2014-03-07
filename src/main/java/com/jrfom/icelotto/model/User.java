package com.jrfom.icelotto.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jrfom.icelotto.util.Stringer;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

@Entity
@Table(
  name = "users"/*,
  uniqueConstraints = {
    @UniqueConstraint(columnNames = {
      "gw2display_name"
    })
  }*/
)
@Embeddable
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String email;

  @Column
  private String displayName;

  @Column(
//    name = "gw2display_name",
    nullable = false
  )
  private String gw2DisplayName;

  @Column
  @JsonIgnore
  private String password;

  @Column
  private String salt;

  /**
   * This should the be value of {@link org.threeten.bp.ZoneId#OLD_IDS_POST_2005}.
   */
  @Column
  private String timeZone;

  /**
   * If set, this will be used to fomat all dates and times that the user
   * will see. It <em>must</em> be a valid format according to
   * {@link org.threeten.bp.format.DateTimeFormatter#ofPattern(String)}. The
   * default pattern that will be used if it isn't set is
   * "yyyy-MM-dd HH:mm Z".
   */
  @Column
  private String datetimeFormat;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(
    name = "user_characters",
    joinColumns = {
      @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "character_id")
    }
  )
  private Set<Character> characters;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_roles",
    joinColumns = {
      @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "role_id")
    }
  )
  private Set<Role> roles;

  protected User() {}

  public User(String gw2DisplayName) {
    this.gw2DisplayName = gw2DisplayName;
  }

  public Long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getGw2DisplayName() {
    return this.gw2DisplayName;
  }

  public void setGw2DisplayName(String gw2DisplayName) {
    this.gw2DisplayName = gw2DisplayName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return this.salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getTimeZone() {
    return this.timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public String getDatetimeFormat() {
    return this.datetimeFormat;
  }

  public void setDatetimeFormat(String datetimeFormat) {
    this.datetimeFormat = datetimeFormat;
  }

  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * Formats an {@link org.threeten.bp.Instant} to the user's desired date
   * time time format. If the format does not include both date <em>and</em>
   * time parameters, then this method will fail.
   *
   * @param dateTime The {@link org.threeten.bp.Instant} to format.
   *
   * @return A formatted date and time string.
   */
  @Transient
  public String localizeDatetime(Instant dateTime) {
    ZoneId zoneId = (this.timeZone != null) ? ZoneId.of(this.timeZone) : ZoneId.of("UTC");
    String format = (this.datetimeFormat != null) ? this.datetimeFormat : "yyyy-MM-dd HH:mm Z";

    try {
      DateTimeFormatter.ofPattern(format);
    } catch (IllegalArgumentException e) {
      format = "yyyy-MM-dd HH:mm Z";
    }

    return OffsetDateTime.ofInstant(dateTime, zoneId).toString(DateTimeFormatter.ofPattern(format));
  }

  @Override
  public String toString() {
    return Stringer.jsonString(this);
  }
}