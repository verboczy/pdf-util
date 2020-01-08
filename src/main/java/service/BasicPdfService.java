package service;

import dto.Arguments;
import dto.DeletePagesArguments;
import dto.ExtractPagesArguments;
import javafx.util.Pair;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicPdfService implements PdfService {

    @Override
    public void removePages(final DeletePagesArguments arguments) throws IOException {
        final PDDocument document = loadDocument(arguments);

        document.removePage(0);

        saveAndCloseDocument(document, arguments.getSource());
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
        File file = new File(arguments.getSource());
        return PDDocument.load(file);
    }

    private void saveAndCloseDocument(final PDDocument document, final String fileName) throws IOException {
        document.save(fileName);
        document.close();
    }

}
