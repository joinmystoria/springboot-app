package mystoria.springboot_app.Repository;

import mystoria.springboot_app.Model.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface NameRepository extends JpaRepository<Name, Long> {
}
