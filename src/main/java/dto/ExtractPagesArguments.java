package dto;

import javafx.util.Pair;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExtractPagesArguments extends Arguments {
    private final List<Pair<Integer, Integer>> pages;
    private final List<String> destinations;

    public ExtractPagesArguments(final String source, final List<Pair<Integer, Integer>> pages, final List<String> destinations) {
        super(source);
        this.pages = pages;
        this.destinations = destinations;
    }
}
