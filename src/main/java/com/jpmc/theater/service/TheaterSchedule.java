package com.jpmc.theater.service;

import java.time.LocalDate;
import java.util.List;

public record TheaterSchedule(LocalDate currentDate, List<TheaterShow> theaterShows) {
}