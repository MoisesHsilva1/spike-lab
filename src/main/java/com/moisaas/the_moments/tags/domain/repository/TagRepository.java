package com.moisaas.the_moments.tags.domain.repository;

import com.moisaas.the_moments.tags.domain.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, String> {
}
