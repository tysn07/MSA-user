package group.microserviceuser.user.repository;

import org.example.share.config.global.entity.user.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
@Repository
@EntityScan(basePackageClasses = User.class)
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
