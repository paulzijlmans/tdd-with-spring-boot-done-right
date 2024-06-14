package de.rieckpil;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TimeProvider {
  public LocalDate getCurrentDate() {
    return LocalDate.now();
  }
}
