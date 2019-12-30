package dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeletePagesArguments extends Arguments {
    private final List<Integer> pageNumbers;

    public DeletePagesArguments(String source, List<Integer> pageNumbers) {
        super(source);
        this.pageNumbers = pageNumbers;
    }
}
