package com.jpmc.theater;

import java.time.LocalDate;
import java.util.List;

public record TheaterSchedule(LocalDate currentDate, List<ShowDetail> showDetails) {
}