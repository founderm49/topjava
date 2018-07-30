package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user=:userId")
    int delete(@Param("id,userId") int id, int userId);

    @Transactional
    @Modifying
    @Query("SELECT ALL FROM Meal m WHERE m.user=:userId")
    List<Meal> getAll(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("SELECT ALL FROM Meal m WHERE m.user=:userId AND m.dateTime>=:startDate AND m.dateTime<=:endDate")
    List<Meal> getBetween(@Param("startDate, endDate, userId") LocalDateTime startDate, LocalDateTime endDate, int userId);
}
