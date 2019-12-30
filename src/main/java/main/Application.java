package main;

import dto.DeletePagesArguments;
import service.BasicPdfService;

import java.util.Arrays;

public class Application {

    public static void main(final String[] args) {
        System.out.println("Hello world!");
        BasicPdfService basicPdfService = new BasicPdfService();
        basicPdfService.removePages(new DeletePagesArguments("C:\\Users\\verboczy\\Desktop\\ssh.pdf", Arrays.asList(0, 1)));
    }

}
