package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println();
        filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> amountsOfCaloriesPerDay = new HashMap<>();
        for (UserMeal userMeal : meals) {
            amountsOfCaloriesPerDay.merge(toLocalDate(userMeal), userMeal.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            if (toLocalTime(userMeal).compareTo(startTime) >= 0 && toLocalTime(userMeal).compareTo(endTime) < 0) {
                mealsWithExcess.add(convertUserMealToNewUserMealWithExcess(userMeal, amountsOfCaloriesPerDay.get(toLocalDate(userMeal)) > caloriesPerDay));
            }
        }
        return mealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> amountsOfCaloriesPerDay = meals.stream().collect(Collectors.toMap(UserMealsUtil::toLocalDate, UserMeal::getCalories, Integer::sum));
        return meals.stream().filter(userMeal -> toLocalTime(userMeal).compareTo(startTime) >= 0 && toLocalTime(userMeal).compareTo(endTime) < 0).
                map(userMeal -> convertUserMealToNewUserMealWithExcess(userMeal, amountsOfCaloriesPerDay.get(toLocalDate(userMeal)) > caloriesPerDay)).
                collect(Collectors.toList());
    }

    private static LocalTime toLocalTime(UserMeal userMeal) {
        return userMeal.getDateTime().toLocalTime();
    }

    private static LocalDate toLocalDate(UserMeal userMeal) {
        return userMeal.getDateTime().toLocalDate();
    }

    private static UserMealWithExcess convertUserMealToNewUserMealWithExcess(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }
}