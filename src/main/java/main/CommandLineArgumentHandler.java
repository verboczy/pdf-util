package main;

import dto.Arguments;
import dto.DeletePagesArguments;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.util.*;


@Slf4j
public class CommandLineArgumentHandler {

    private Options options;

    public CommandLineArgumentHandler() {
        options = new Options();

        options.addOption("doc", "document", true, "The source document can be given here.");

        final Option deleteOption = new Option("d", "delete", true, "Deletes the given pages.");
        deleteOption.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(deleteOption);
        options.addOption("e", "extract", true, "Extracts the given pages.");

        options.addOption("h", "help", false, "prints this help");

        log.trace("Options created.");
    }

    Arguments parse(final String[] args) {
        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                log.trace("Print help");
                printHelp(options);
                return null;
            }

            if (!cmd.hasOption("doc")) {
                printHelp(options);
                return null;
            }

            final String doc = cmd.getOptionValue("doc");

            if (cmd.hasOption("d")) {
                final String[] pagesFromArgument = cmd.getOptionValues("d");
                final List<Integer> pagesToDelete = getPagesFromArgument(pagesFromArgument);
                return new DeletePagesArguments(doc, pagesToDelete);
            }

//            if (cmd.hasOption("e")) {
//
//            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    List<Integer> getPagesFromArgument(String[] pages) {
        List<Integer> result = new ArrayList<>();
        try {
            for (String page : pages) {
                if (page.contains("-")) {
                    final String[] morePageNumbers = page.split("-");
                    int lowerBound = Integer.parseInt(morePageNumbers[0]);
                    int upperBound = Integer.parseInt(morePageNumbers[1]);
                    for (int i = upperBound; i >= lowerBound; i--) {
                        result.add(i);
                    }
                } else {
                    result.add(Integer.parseInt(page));
                }
            }
        } catch (NumberFormatException n) {
            log.error("There was an invalid character inside the arguments.");
            log.trace(n.getMessage());
            return Collections.emptyList();
        }

        // Remove duplicates
        Set<Integer> integerSet = new HashSet<>(result);
        result = new ArrayList<>(integerSet);
        // Order the pages descending
        result.sort((a, b) -> Integer.compare(b, a));
        return result;
    }

    private void printHelp(final Options options) {
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("pdfattachment", options);
    }
}
