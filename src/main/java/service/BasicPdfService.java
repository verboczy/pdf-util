package service;

import dto.Arguments;
import dto.DeletePagesArguments;
import dto.ExtractPagesArguments;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BasicPdfService implements PdfService {

    @Override
    public void removePages(final DeletePagesArguments arguments) throws IOException {
        log.info("Removing pages from document...");
        final PDDocument document = loadDocument(arguments);

        for (Integer pageNumber : arguments.getPageNumbers()) {
            --pageNumber; // It indexes from 0, but the user does not.
            log.trace("Removing page {} from document...", pageNumber);
            document.removePage(pageNumber);
            log.debug("Page {} has been removed from document.", pageNumber);
        }

        log.debug("All page has been removed from document. Saving document...");
        saveAndCloseDocument(document, arguments.getSource());
        log.info("Successfully removed all desired page from document.");
    }

    @Override
    public void extractPages(final ExtractPagesArguments arguments) throws IOException {
        final PDDocument document = loadDocument(arguments);

        for (int i = 0; i < arguments.getPages().size(); ++i) {
            final Splitter splitter = new Splitter();
            final List<PDDocument> splitDocuments = splitter.split(document);

            final Pair<Integer, Integer> pages = arguments.getPages().get(i);
            final String fileName = arguments.getDestinations().get(i);

            PDDocument mergedDocument = mergeDocuments(getDocumentsFromSplit(splitDocuments, pages.getKey(), pages.getValue()));

            saveAndCloseDocument(mergedDocument, String.format("C:\\Users\\verboczy\\Desktop\\pdfattachment\\%s", fileName));
            for (PDDocument asd : splitDocuments) {
                asd.close();
            }
        }
    }

    private List<PDDocument> getDocumentsFromSplit(List<PDDocument> splittedDocuments, int from, int to) {
        List<PDDocument> documents = new ArrayList<>();

        for (int i = from; i <= to; ++i) {
            documents.add(splittedDocuments.get(i));
        }

        return documents;
    }

    private PDDocument mergeDocuments(List<PDDocument> documents) throws IOException {
        PDFMergerUtility pdfMerger = new PDFMergerUtility();

        PDDocument document = documents.get(0);
        for (int i = 1; i < documents.size(); ++i) {
            pdfMerger.appendDocument(document, documents.get(i));
        }

        return document;
    }

    private PDDocument loadDocument(final Arguments arguments) throws IOException {
        log.debug("Loading document [{}]", arguments.getSource());
        File file = new File(arguments.getSource());
        try {
            return PDDocument.load(file);
        } catch (IOException e) {
            log.error("Could not load file: {}", arguments.getSource());
            log.trace(e.getLocalizedMessage());
            throw e;
        }
    }

    private void saveAndCloseDocument(final PDDocument document, final String fileName) throws IOException {
        document.save(fileName);
        document.close();
    }

}
