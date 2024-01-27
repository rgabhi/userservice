package learning.userservice.repositories;

import learning.userservice.models.UserName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNameRepository extends JpaRepository<UserName, Long> {

}
