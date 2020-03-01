package main;

import dto.ExtractPagesArguments;
import javafx.util.Pair;
import service.BasicPdfService;
import service.PdfService;

import java.io.IOException;
import java.util.Arrays;

public class Application {

    public static void main(final String[] args) {
        System.out.println("Hello world!");
        PdfService basicPdfService = new BasicPdfService();
        try {
            //basicPdfService.removePages(new DeletePagesArguments("C:\\Users\\verboczy\\Desktop\\ssh.pdf", Arrays.asList(0, 1)));
            basicPdfService.extractPages(new ExtractPagesArguments("C:\\Users\\verboczy\\Desktop\\pdfattachment\\viking.pdf", Arrays.asList(new Pair<>(3, 14), new Pair<>(0, 143)), Arrays.asList("elso.pdf", "masodik2.pdf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
