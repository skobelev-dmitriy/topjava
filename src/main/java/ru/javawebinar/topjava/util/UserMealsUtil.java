package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 30,20,0), "Ужин", 520),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1,20,0), "Ужин", 410)
        );
        long start = System.currentTimeMillis();
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        filteredWithExceeded.forEach(meal->System.out.println(meal));
        long finish = System.currentTimeMillis();
        long timeResult = finish - start;
        System.out.println(timeResult);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds=mealList.stream()
                    .filter(curMeal->TimeUtil.isBetween(curMeal.getLocalTime(),startTime,endTime))
                    .map(curMeal->new UserMealWithExceed(curMeal,mealList.stream()
                            .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)))
                            .get(curMeal.getLocalDate())>caloriesPerDay))
                    .collect(Collectors.toList());

        return userMealWithExceeds;
    }
}
