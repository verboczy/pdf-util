package main;

import dto.Arguments;
import dto.DeletePagesArguments;
import dto.ExtractPagesArguments;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BasicPdfService;
import service.PdfService;

import java.io.IOException;
import java.util.Arrays;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(final String[] args) {
        log.info("Application started...");
        CommandLineArgumentHandler commandLineArgumentHandler = new CommandLineArgumentHandler();
        final Arguments arguments = commandLineArgumentHandler.parse(args);
        if (arguments instanceof DeletePagesArguments) {
            log.debug("Delete pages arguments");
        } else if (arguments instanceof ExtractPagesArguments) {
            log.debug("Extract pages arguments");
        } else {
            log.warn("Unknown arguments");
        }
        PdfService basicPdfService = new BasicPdfService();
        try {
            //basicPdfService.removePages(new DeletePagesArguments("C:\\Users\\verboczy\\Desktop\\ssh.pdf", Arrays.asList(0, 1)));
            basicPdfService.extractPages(new ExtractPagesArguments("C:\\Users\\verboczy\\Desktop\\pdfattachment\\viking.pdf", Arrays.asList(new Pair<>(3, 14), new Pair<>(0, 143)), Arrays.asList("elso.pdf", "masodik2.pdf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
