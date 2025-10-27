import com.csen160.datamanager.DataManager;
import com.csen160.datamanager.helper.Sales;
import com.csen160.fileprocessor.FileProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitTests {
    @TempDir
    Path tempDir;

    private Path writeTempFile(String name, String content) throws IOException {
        Path p = tempDir.resolve(name);
        Files.write(p, content.getBytes(StandardCharsets.UTF_8));
        return p;
    }

    @Test
    void testReadSingleLine() throws Exception {
        Path f = writeTempFile("single.txt", "2020:1:100\n");
        DataManager dm = new DataManager();
        dm.readDataFromFile(f.toString());

        Map<String, Sales> data = dm.getData();
        assertEquals(1, data.size());
        Sales s = data.get("2020-1");
        assertNotNull(s);
        assertEquals(2020, s.getYear());
        assertEquals(1, s.getQuarter());
    }

    @Test
    void testReadMultipleLines() throws Exception {
        String content = "2019:1:50\n2019:2:75\n2020:4:200\n";
        Path f = writeTempFile("multiple.txt", content);
        DataManager dm = new DataManager();
        dm.readDataFromFile(f.toString());

        Map<String, Sales> data = dm.getData();
        assertEquals(3, data.size());
        assertNotNull(data.get("2019-1"));
        assertNotNull(data.get("2019-2"));
        assertNotNull(data.get("2020-4"));
        assertEquals(2019, data.get("2019-2").getYear());
        assertEquals(2, data.get("2019-2").getQuarter());
    }

    @Test
    void testDuplicateEntriesOverwrite() throws Exception {
        // same year-quarter twice; last line should overwrite
        String content = "2021:3:120\n2021:3:300\n";
        Path f = writeTempFile("duplicate.txt", content);
        DataManager dm = new DataManager();
        dm.readDataFromFile(f.toString());

        Map<String, Sales> data = dm.getData();
        assertEquals(1, data.size());
        Sales s = data.get("2021-3");
        assertNotNull(s);
        assertEquals(2021, s.getYear());
        assertEquals(3, s.getQuarter());
    }

    @Test
    void testEmptyFileProducesEmptyMap() throws Exception {
        Path f = writeTempFile("empty.txt", "");
        DataManager dm = new DataManager();
        dm.readDataFromFile(f.toString());

        Map<String, Sales> data = dm.getData();
        assertTrue(data.isEmpty());
    }

    @Test
    void testWhitespaceAndExtraSpaces() throws Exception {
        String content = " 2022 : 4 :  250 \n\t2022:2:75\n";
        Path f = writeTempFile("whitespace.txt", content);
        DataManager dm = new DataManager();
        dm.readDataFromFile(f.toString());

        Map<String, Sales> data = dm.getData();
        assertEquals(2, data.size());
        Sales s1 = data.get("2022-4");
        assertNotNull(s1);
        assertEquals(2022, s1.getYear());
        assertEquals(4, s1.getQuarter());
        Sales s2 = data.get("2022-2");
        assertNotNull(s2);
        assertEquals(2, s2.getQuarter());
    }

    @Test
    void testFileCopyCreatesExactCopy() throws Exception {
        String content = "First line\n\nThird line\n";
        Path input = writeTempFile("input.txt", content);
        Path output = tempDir.resolve("copy.txt");

        FileProcessor.fileCopy(input.toString(), output.toString());

        // Compare logical lines to avoid platform line\-ending differences
        List<String> originalLines = Files.readAllLines(input, StandardCharsets.UTF_8);
        List<String> copiedLines = Files.readAllLines(output, StandardCharsets.UTF_8);
        assertEquals(originalLines, copiedLines, "Copied file lines should match original exactly");
    }

    @Test
    void testWordCountProducesCorrectCounts() throws Exception {
        String content = "Hello world\none two three\n\nend\n";
        Path input = writeTempFile("wc_in.txt", content);
        Path output = tempDir.resolve("wc_out.txt");

        FileProcessor.wordCount(input.toString(), output.toString());

        List<String> lines = Files.readAllLines(output, StandardCharsets.UTF_8);
        assertEquals(4, lines.size());
        assertEquals("Line 0 No. of words: 2", lines.get(0));
        assertEquals("Line 1 No. of words: 3", lines.get(1));
        assertEquals("Line 2 No. of words: 0", lines.get(2));
        assertEquals("Line 3 No. of words: 1", lines.get(3));
    }

    @Test
    void testEmptyFileProducesEmptyOutputForWordCount() throws Exception {
        Path input = writeTempFile("empty.txt", "");
        Path output = tempDir.resolve("empty_out.txt");

        FileProcessor.wordCount(input.toString(), output.toString());

        String out = Files.readString(output, StandardCharsets.UTF_8);
        assertTrue(out.isEmpty(), "Word count output for empty input should be empty");
    }

    @Test
    void testMissingInputThrowsIOException() {
        Path nonExistent = tempDir.resolve("does_not_exist.txt");
        Path out = tempDir.resolve("out.txt");

        assertThrows(IOException.class, () -> FileProcessor.fileCopy(nonExistent.toString(), out.toString()));
        assertThrows(IOException.class, () -> FileProcessor.wordCount(nonExistent.toString(), out.toString()));
    }

    @Test
    void testWordCountHandlesWhitespaceAndTabs() throws Exception {
        String content = "  a\tb  c  \n\t \n\td   e\n";
        Path input = writeTempFile("ws.txt", content);
        Path output = tempDir.resolve("ws_out.txt");

        FileProcessor.wordCount(input.toString(), output.toString());

        List<String> lines = Files.readAllLines(output, StandardCharsets.UTF_8);
        assertEquals(3, lines.size());
        assertEquals("Line 0 No. of words: 3", lines.get(0)); // a b c
        assertEquals("Line 1 No. of words: 0", lines.get(1)); // whitespace only
        assertEquals("Line 2 No. of words: 2", lines.get(2)); // d e
    }
}