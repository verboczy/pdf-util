package main;

import dto.Arguments;
import dto.DeletePagesArguments;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.util.Arrays;

@Slf4j
public class CommandLineArgumentHandler {

    private Options createOptions() {
        final Options options = new Options();

        options.addOption("doc", "document", true, "The source document can be given here.");

        options.addOption("d", "delete", true, "Deletes the given pages.");
        options.addOption("e", "extract", true, "Extracts the given pages.");

        options.addOption("h", "help", false, "prints this help");

        return options;
    }

    public Arguments parse(final String[] args) {
        final Options options = createOptions();

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

            // TODO - validate here!

            if (cmd.hasOption("d")) {
                final String[] pagesToDelete = cmd.getOptionValues("d");
                // TODO - convert args to integer list
                DeletePagesArguments dpg = new DeletePagesArguments(doc, Arrays.asList(Integer.parseInt(pagesToDelete[0])));
                return dpg;
            }

            if (cmd.hasOption("e")) {

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void printHelp(final Options options) {
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("pdfattachment", options);
    }
}
