package mystoria.springboot_app.Service;

import mystoria.springboot_app.Model.Name;
import mystoria.springboot_app.Repository.NameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NameServiceTest {

    @Mock
    private NameRepository nameRepository;

    @InjectMocks
    private NameService nameService;

    private Name testName;

    @BeforeEach
    void setUp() {
        testName = new Name();
        testName.setId(1L);
        testName.setFirstName("John");
        testName.setLastName("Doe");
    }

    @Test
    void testGetAllNames() {
        List<Name> names = Arrays.asList(testName);
        when(nameRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(names);

        List<Name> result = nameService.getAllNames();

        assertThat(result).isNotNull().hasSize(1);
        verify(nameRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void testCreateName() {
        when(nameRepository.save(any(Name.class))).thenReturn(testName);

        Name createdName = nameService.createName(testName);

        assertThat(createdName).isNotNull();
        assertThat(createdName.getFirstName()).isEqualTo("John");
        verify(nameRepository, times(1)).save(any(Name.class));
    }

    @Test
    void testUpdateName_Success() {
        when(nameRepository.findById(1L)).thenReturn(Optional.of(testName));
        when(nameRepository.save(any(Name.class))).thenReturn(testName);

        Name updatedName = new Name();
        updatedName.setFirstName("Jane");
        updatedName.setLastName("Smith");

        Name result = nameService.updateName(1L, updatedName);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Jane");
        verify(nameRepository, times(1)).findById(1L);
        verify(nameRepository, times(1)).save(any(Name.class));
    }

    @Test
    void testUpdateName_NotFound() {
        when(nameRepository.findById(1L)).thenReturn(Optional.empty());

        Name updatedName = new Name();
        updatedName.setFirstName("Jane");
        updatedName.setLastName("Smith");

        Name result = nameService.updateName(1L, updatedName);

        assertThat(result).isNull();
        verify(nameRepository, times(1)).findById(1L);
        verify(nameRepository, never()).save(any(Name.class));
    }

    @Test
    void testDeleteName_Success() {
        when(nameRepository.existsById(1L)).thenReturn(true);
        doNothing().when(nameRepository).deleteById(1L);

        String result = nameService.deleteName(1L);

        assertThat(result).isEqualTo("Deletion successful");
        verify(nameRepository, times(1)).existsById(1L);
        verify(nameRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteName_NotFound() {
        when(nameRepository.existsById(1L)).thenReturn(false);

        String result = nameService.deleteName(1L);

        assertThat(result).isEqualTo("Name not found");
        verify(nameRepository, times(1)).existsById(1L);
        verify(nameRepository, never()).deleteById(anyLong());
    }
}
