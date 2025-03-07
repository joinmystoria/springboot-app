package mystoria.springboot_app.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {

    @Test
    void testGettersAndSetters() {
        Name name = new Name();
        name.setId(1L);
        name.setFirstName("John");
        name.setLastName("Doe");

        assertThat(name.getId()).isEqualTo(1L);
        assertThat(name.getFirstName()).isEqualTo("John");
        assertThat(name.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testJsonSerialization() throws Exception {
        Name name = new Name();
        name.setId(1L);
        name.setFirstName("John");
        name.setLastName("Doe");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(name);

        assertThat(json).contains("\"first_name\":\"John\"");
        assertThat(json).contains("\"last_name\":\"Doe\"");
    }

    @Test
    void testJsonDeserialization() throws Exception {
        String json = "{\"id\":1,\"first_name\":\"John\",\"last_name\":\"Doe\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        Name name = objectMapper.readValue(json, Name.class);

        assertThat(name.getId()).isEqualTo(1L);
        assertThat(name.getFirstName()).isEqualTo("John");
        assertThat(name.getLastName()).isEqualTo("Doe");
    }

}