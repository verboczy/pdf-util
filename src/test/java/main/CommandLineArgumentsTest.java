package main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandLineArgumentsTest {

    private static CommandLineArgumentHandler commandLineArgumentHandler;

    @BeforeAll
    static void init() {
        commandLineArgumentHandler = new CommandLineArgumentHandler();
    }

    private static Stream<Arguments> getPagesFromArgumentParams() {
        return Stream.of(
                Arguments.of("Only one page", new String[]{"10"}, Collections.singletonList(10)),
                Arguments.of("More pages, no interval", new String[]{"0", "5"}, Arrays.asList(5, 0)),
                Arguments.of("More page, with interval", new String[]{"0-6", "14"}, Arrays.asList(14, 6, 5, 4, 3, 2, 1, 0)),
                Arguments.of("Duplicates", new String[]{"0-6", "0-7"}, Arrays.asList(7, 6, 5, 4, 3, 2, 1, 0)),
                Arguments.of("Pages not in order, more interval", new String[]{"20-21", "14-16", "2", "32", "17-19"}, Arrays.asList(32, 21, 20, 19, 18, 17, 16, 15, 14, 2)),
                Arguments.of("Invalid character", new String[]{"0", "1", "2-32", "a", "5"}, Collections.emptyList())
        );
    }

    @ParameterizedTest(name = "index - {0}")
    @MethodSource("getPagesFromArgumentParams")
    void getPagesFromArgument(String name, String[] pages, List<Integer> expectedPages) {
        // Given

        // When
        final List<Integer> actualPages = commandLineArgumentHandler.getPagesFromArgument(pages);

        // Then
        assertEquals(expectedPages, actualPages);
    }
}
