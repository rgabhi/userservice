package learning.userservice.repositories;

import learning.userservice.models.User;
import learning.userservice.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    void deleteAllByUser_Id(Long id);

}
