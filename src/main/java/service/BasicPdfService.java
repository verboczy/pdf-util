package service;

import dto.DeletePagesArguments;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class BasicPdfService implements PdfService {


    public void removePages(DeletePagesArguments arguments) {
        File file = new File(arguments.getSource());
        try {
            final PDDocument document = PDDocument.load(file);
            System.out.println(document.getNumberOfPages());
            document.removePage(0);
            document.save(arguments.getSource());
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
