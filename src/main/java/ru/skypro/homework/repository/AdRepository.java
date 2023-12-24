package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
    Optional<Ad> findById (@Nullable Integer id);

    @Override
    void deleteById(@Nullable Integer id);
}
