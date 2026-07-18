package com.skillhire.backend.repository;

import com.skillhire.backend.entity.WorkerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkerProfileRepository extends JpaRepository<WorkerProfile, Long> {

    Optional<WorkerProfile> findByUser_Id(Long userId);

    List<WorkerProfile> findByCategory_Slug(String categorySlug);

    @Query("""
           select w from WorkerProfile w
           where (:categorySlug is null or w.category.slug = :categorySlug)
             and (:location is null or lower(w.location) like lower(concat('%', :location, '%')))
             and (:query is null
                  or lower(w.profession) like lower(concat('%', :query, '%'))
                  or lower(w.user.name) like lower(concat('%', :query, '%')))
           """)
    List<WorkerProfile> search(
            @Param("categorySlug") String categorySlug,
            @Param("location") String location,
            @Param("query") String query);
}
