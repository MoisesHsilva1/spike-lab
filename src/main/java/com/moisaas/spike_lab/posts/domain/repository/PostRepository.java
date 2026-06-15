package com.moisaas.spike_lab.posts.domain.repository;

import com.moisaas.spike_lab.posts.domain.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {
}
