package com.jwt.demo.userandrole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeReposistory extends JpaRepository<HomeEntity, Long> {

	HomeEntity findByEmailId(String email);

}
