package project.seoulTTD.repository;

import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seoulTTD.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryQuerydsl {

    List<Tuple> findFavoriteAllById(Long userid);

    List<User> findByPhoneNum(String phoneNum);

    Optional<User> findUserByLoginId(String loginId);

    User findUserById(Long id);

    void deleteFavoriteById(Long id);
}
