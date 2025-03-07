package mystoria.springboot_app.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mystoria.springboot_app.Model.Name;
import mystoria.springboot_app.Service.NameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NameController.class)
@ExtendWith(MockitoExtension.class)
class NameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NameService nameService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllNames() throws Exception {
        List<Name> names = Arrays.asList(
                new Name(1L, "John", "Doe"),
                new Name(2L, "Jane", "Doe")
        );
        when(nameService.getAllNames()).thenReturn(names);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/names")) // Update endpoint to match your controller
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].first_name").value("John"))
                .andExpect(jsonPath("$[1].first_name").value("Jane"));
    }

    @Test
    void testCreateName() throws Exception {
        Name name = new Name(1L, "John", "Doe");
        when(nameService.createName(any(Name.class))).thenReturn(name);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/add") // Update to correct POST endpoint
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(name)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("John"));
    }

    @Test
    void testUpdateName() throws Exception {
        Name updatedName = new Name(1L, "John", "Smith");
        when(nameService.updateName(eq(1L), any(Name.class))).thenReturn(updatedName);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedName)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.last_name").value("Smith"));
    }

    @Test
    void testDeleteName() throws Exception {
        when(nameService.deleteName(1L)).thenReturn("Deletion successful");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deletion successful"));
    }

    @Test
    void testDeleteNameNotFound() throws Exception {
        when(nameService.deleteName(99L)).thenReturn("Name not found");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Name not found"));
    }

}