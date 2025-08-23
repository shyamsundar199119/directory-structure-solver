package eu.klavenessdigital.directory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void uploadCsvFile() throws Exception {
        // Load test CSV file
        byte[] fileContent = Files.readAllBytes(Path.of("src/test/resources/directory-structure.csv"));
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "directory-structure.csv",
                MediaType.TEXT_PLAIN_VALUE,
                fileContent
        );

        // Upload the file into DirectoryController cache
        mockMvc.perform(multipart("/api/directory/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded and parsed successfully!"));
    }

    @Test
    void rendersTreeMatchesExpectedOutput() throws Exception {
        String expectedOutput = Files.readString(Path.of("src/test/resources/tree.txt"));

        String actualOutput = mockMvc.perform(get("/api/directory/tree"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(expectedOutput.trim(), actualOutput.trim());
    }

    @Test
    void filtersTopSecretMatchesExpectedOutput() throws Exception {
        String expected = Files.readString(Path.of("src/test/resources/top-secret.txt"));

        String actual = mockMvc.perform(get("/api/directory/files/top-secret")
                        .param("classifications", "TOP SECRET"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void filtersSecretAndTopSecretMatchesExpectedOutput() throws Exception {
        String expected = Files.readString(Path.of("src/test/resources/secret-or-top-secret.txt"));

        String actual = mockMvc.perform(get("/api/directory/files/secret-or-top-secret")
                        .param("classifications", "SECRET")
                        .param("classifications", "Top secret"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void filtersFolderMatchesExpectedOutput() throws Exception {
        String expected = Files.readString(Path.of("src/test/resources/non-public-folder11.txt"));

        String actual = mockMvc.perform(get("/api/directory/files/non-public")
                        .param("folderName", "folder11"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(expected.trim(), actual.trim());
    }
}

