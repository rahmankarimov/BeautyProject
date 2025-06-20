package com.beautyProject.beautyProject.repository;




import com.beautyProject.beautyProject.model.entity.SkinType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinTypeRepository extends JpaRepository<SkinType, Long> {
    SkinType findByName(String name);
}