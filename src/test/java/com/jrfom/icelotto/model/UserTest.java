package com.jrfom.icelotto.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

import static org.junit.Assert.assertTrue;

public class UserTest {
  private static final Logger log = LoggerFactory.getLogger(UserTest.class);

  // Fri, 07 Mar 2014 14:08:00 GMT
  private Instant testDateTime = Instant.ofEpochSecond(1394201280);

  @Test
  public void inSmallPoolTest() {
    log.info("Running User.isInSmallPoolForDrawing(Drawing) test");
    User user = new User();
    user.setId(1l);

    Drawing drawing = new Drawing();
    drawing.setId(1l);

    PrizeTier prizeTier = new PrizeTier();

    drawing.addEntry(new Entry(user, prizeTier, 1));
    drawing.addEntry(new Entry(user, prizeTier, 2));

    assertTrue(user.isInSmallPoolForDrawing(drawing));
  }

  @Test
  public void inLargePoolTest() {
    log.info("Running User.isInLargePoolForDrawing(Drawing) test");
    User user = new User();
    user.setId(1l);

    Drawing drawing = new Drawing();
    drawing.setId(1l);

    PrizeTier prizeTier = new PrizeTier();

    drawing.addEntry(new Entry(user, prizeTier, 1));
    drawing.addEntry(new Entry(user, prizeTier, 2));
    drawing.addEntry(new Entry(user, prizeTier, 10));

    assertTrue(user.isInLargePoolForDrawing(drawing));
  }

  @Test
  public void formatDatetimeWithNoTimezoneOrFormat() {
    log.info("Running User datetime formatting with no tz or format specified test");
    User user = new User();
    String formattedDatetime = user.localizeDatetime(this.testDateTime);
    LocalDateTime localDateTime = LocalDateTime.parse(
      formattedDatetime,
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm Z")
    );

    assertTrue(localDateTime.getMonthValue() == 3);
    assertTrue(localDateTime.getDayOfMonth() == 7);
    assertTrue(localDateTime.getYear() == 2014);
    assertTrue(localDateTime.getHour() == 14);
    assertTrue(localDateTime.getMinute() == 8);
  }

  @Test
  public void formatDatetimeWithTimezone() {
    log.info("Running User datetime formatting with tz and no format specified test");
    User user = new User();
    user.setTimeZone("-05:00");
    String formattedDatetime = user.localizeDatetime(this.testDateTime);
    LocalDateTime localDateTime = LocalDateTime.parse(
      formattedDatetime,
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm Z")
    );

    assertTrue(localDateTime.getMonthValue() == 3);
    assertTrue(localDateTime.getDayOfMonth() == 7);
    assertTrue(localDateTime.getYear() == 2014);
    assertTrue(localDateTime.getHour() == 9);
    assertTrue(localDateTime.getMinute() == 8);
  }

  @Test
  public void formatDatetimeWithFormatAndNoTimezone() {
    log.info("Running User datetime formatting with format and no tz specified test");
    User user = new User();
    user.setDatetimeFormat("yyyy-MM-dd HH:mm");
    String formattedDatetime = user.localizeDatetime(this.testDateTime);
    LocalDateTime localDateTime = LocalDateTime.parse(
      formattedDatetime,
      DateTimeFormatter.ofPattern(user.getDatetimeFormat())
    );

    assertTrue(localDateTime.getMonthValue() == 3);
    assertTrue(localDateTime.getDayOfMonth() == 7);
    assertTrue(localDateTime.getYear() == 2014);
    assertTrue(localDateTime.getHour() == 14);
    assertTrue(localDateTime.getMinute() == 8);
  }

  @Test
  public void formatDatetimeWithFormatAndTimezone() {
    log.info("Running User datetime formatting with format and tz specified test");
    User user = new User();
    user.setTimeZone("-05:00");
    user.setDatetimeFormat("yyyy-MM-dd HH:mm");
    String formattedDatetime = user.localizeDatetime(this.testDateTime);
    LocalDateTime localDateTime = LocalDateTime.parse(
      formattedDatetime,
      DateTimeFormatter.ofPattern(user.getDatetimeFormat())
    );

    assertTrue(localDateTime.getMonthValue() == 3);
    assertTrue(localDateTime.getDayOfMonth() == 7);
    assertTrue(localDateTime.getYear() == 2014);
    assertTrue(localDateTime.getHour() == 9);
    assertTrue(localDateTime.getMinute() == 8);
  }

  @Test(expected = DateTimeParseException.class)
  public void formatDatetimeWithOnlyDateAsFormat() {
    log.info("Running User datetime formatting with only date as format test");
    User user = new User();
    user.setTimeZone("-05:00");
    user.setDatetimeFormat("yyyy-MM-dd");
    String formattedDatetime = user.localizeDatetime(this.testDateTime);
    LocalDateTime localDateTime = LocalDateTime.parse(
      formattedDatetime,
      DateTimeFormatter.ofPattern(user.getDatetimeFormat())
    );
  }
}