package mystoria.springboot_app.Controller;

import mystoria.springboot_app.Model.Name;
import mystoria.springboot_app.Service.NameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class NameController {

    private final NameService nameService;

    @GetMapping("/names")
    public ResponseEntity<List<Name>> getAllNames() {
        return ResponseEntity.ok(nameService.getAllNames());
    }

    @PostMapping("/add")
    public ResponseEntity<Name> createName(@RequestBody Name name) {
        return ResponseEntity.ok(nameService.createName(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Name> updateName(@PathVariable Long id, @RequestBody Name updatedName) {
        Name updated = nameService.updateName(id, updatedName);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteName(@PathVariable Long id) {
        String result = nameService.deleteName(id);
        if (result.equals("Deletion successful")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

}