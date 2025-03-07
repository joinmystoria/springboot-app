package mystoria.springboot_app.Service;

import mystoria.springboot_app.Model.Name;
import mystoria.springboot_app.Repository.NameRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@Component
public class NameService {

    private final NameRepository nameRepository;

    // Get All Names
    public List<Name> getAllNames() {
        return nameRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    // Create Name
    public Name createName(Name name) {
        return nameRepository.save(name);
    }

    // Update Name
    public Name updateName(Long id, Name updatedName) {
        Optional<Name> existingName = nameRepository.findById(id);
        if (existingName.isPresent()) {
            Name name = existingName.get();
            name.setFirstName(updatedName.getFirstName());
            name.setLastName(updatedName.getLastName());
            return nameRepository.save(name);
        }
        return null;
    }

    public String deleteName(Long id) {
        if (nameRepository.existsById(id)) {
            nameRepository.deleteById(id);
            return "Deletion successful";
        } else {
            return "Name not found";
        }
    }

}