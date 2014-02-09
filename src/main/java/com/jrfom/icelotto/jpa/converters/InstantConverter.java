package com.jrfom.icelotto.jpa.converters;

import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.threeten.bp.Instant;

/**
 * Provides a JPA 2.1 {@link javax.persistence.Converter} so that
 * {@link org.threeten.bp.Instant}s can be persisted to a data store.
 */
@Converter(autoApply = true)
public class InstantConverter implements AttributeConverter<Instant, Date> {
  @Override
  public Date convertToDatabaseColumn(Instant attribute) {
    return new Date(attribute.toEpochMilli());
  }

  @Override
  public Instant convertToEntityAttribute(Date dbData) {
    return Instant.ofEpochMilli(dbData.getTime());
  }
}