package service;

import dto.DeletePagesArguments;
import dto.ExtractPagesArguments;

import java.io.IOException;

public interface PdfService {

    void removePages(final DeletePagesArguments arguments) throws IOException;

    void extractPages(final ExtractPagesArguments arguments) throws IOException;

}
