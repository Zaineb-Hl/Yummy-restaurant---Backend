package croco.restau.yummy.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import croco.restau.yummy.models.Chef;
import java.util.List;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long>{
    List<Chef> findBySpeciality(String speciality);
    List<Chef> findByLastNameContainingIgnoreCase(String lastName);

}
