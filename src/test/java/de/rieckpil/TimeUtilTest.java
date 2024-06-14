package de.rieckpil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeUtilTest {

  @Mock
  private TimeProvider timeProvider;

  @InjectMocks
  private TimeUtil timeUtil;

  @Test
  void shouldThrowException_whenDateIsInFuture() throws Exception {
    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.of(2020, 12, 24));
    LocalDate creationDateInTheFuture = LocalDate.now().plusDays(1);

    assertThrows(IllegalArgumentException.class, () -> {
      timeUtil.getDiffWithCreationDate(creationDateInTheFuture);
    });
  }

  @Test
  void shouldReturnToday_whenCommentWasCreatedToday() throws Exception {
    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.now());
    LocalDate today = LocalDate.now();

    String result = timeUtil.getDiffWithCreationDate(today);

    assertEquals("today", result);
  }

  @Test
  void shouldReturnMoreThanAYearAgo_whenCommentWasCreatedLastYear() throws Exception {
    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.now());
    LocalDate date = LocalDate.now().minusDays(370);

    String result = timeUtil.getDiffWithCreationDate(date);

    assertEquals("more than a year ago", result);
  }

  @Test
  void shouldReturnMoreThanAMonthAgo_whenCommentWasCreatedLastMonth() throws Exception {
    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.now());
    LocalDate date = LocalDate.now().minusDays(40);

    String result = timeUtil.getDiffWithCreationDate(date);

    assertEquals("one month ago", result);
  }

  @Test
  void shouldReturnPluralOfMonthsAgo_whenCommentWasCreatedMultipleMonthsAgo() throws Exception {
    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.now());
    LocalDate date = LocalDate.now().minusDays(70);

    String result = timeUtil.getDiffWithCreationDate(date);

    assertEquals("2 months ago", result);
  }

  @Test
  void shouldReturnOneDayAgo_whenCommentWasCreatedYesterday() throws Exception {
    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.now());
    LocalDate yesterday = LocalDate.now().minusDays(1);

    String result = timeUtil.getDiffWithCreationDate(yesterday);

    assertEquals("one day ago", result);
  }

  @Test
  void shouldReturnPluralOfDaysAgo_whenCommentWasCreatedBeforeYesterday() throws Exception {
    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.now());
    LocalDate twentyDaysAgo = LocalDate.now().minusDays(20);

    String result = timeUtil.getDiffWithCreationDate(twentyDaysAgo);

    assertEquals("20 days ago", result);
  }
}
