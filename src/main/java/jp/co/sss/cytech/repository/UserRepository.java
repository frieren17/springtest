package jp.co.sss.cytech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}