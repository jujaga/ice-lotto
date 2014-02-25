package com.jrfom.icelotto.jpa.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.threeten.bp.Instant;

/**
 * Provides a JPA 2.1 {@link javax.persistence.Converter} so that
 * {@link org.threeten.bp.Instant}s can be persisted to a data store.
 */
@Converter(autoApply = true)
public class InstantConverter implements AttributeConverter<Instant, Long> {
  @Override
  public Long convertToDatabaseColumn(Instant attribute) {
    return attribute.getEpochSecond();
  }

  @Override
  public Instant convertToEntityAttribute(Long dbData) {
    return (dbData == null) ? null : Instant.ofEpochSecond(dbData);
  }
}