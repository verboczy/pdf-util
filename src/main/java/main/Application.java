package main;

import dto.Arguments;
import dto.DeletePagesArguments;
import dto.ExtractPagesArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BasicPdfService;
import service.PdfService;

import java.io.IOException;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(final String[] args) {
        log.info("Application started...");

        PdfService basicPdfService = new BasicPdfService();

        CommandLineArgumentHandler commandLineArgumentHandler = new CommandLineArgumentHandler();
        final Arguments arguments = commandLineArgumentHandler.parse(args);

        if (arguments instanceof DeletePagesArguments) {
            log.debug("Delete pages arguments");
            try {
                basicPdfService.removePages((DeletePagesArguments) arguments);
            } catch (IOException e) {
                log.error("An error occurred during removing pages from document.");
            }
        } else if (arguments instanceof ExtractPagesArguments) {
            log.debug("Extract pages arguments");
        } else {
            log.warn("Unknown arguments");
        }
    }
}