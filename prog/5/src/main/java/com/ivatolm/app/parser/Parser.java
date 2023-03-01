package com.ivatolm.app.parser;

import java.util.LinkedList;

import com.ivatolm.app.parser.arguments.ArgCheck;
import com.ivatolm.app.parser.arguments.Argument;
import com.ivatolm.app.utils.SimpleParseException;

/**
 * Class for parsing commands.
 *
 * @author ivatolm
 */
public class Parser {

    /** Do we parse command or its arguments? */
    private boolean waitingArgs = false;


    /** Partially parsed command */
    private Command cmd;

    /** Partially parsed arguments */
    private LinkedList<Argument> args;


    /** Parsed command */
    private Command result;

    /**
     * Parsing input string containing command or its arguments.
     * If required input is abscent, then {@code SimpleParseException} is thrown.
     *
     * TODO: Detailed explanation of the process.
     *
     * @param input string containing command or its arguments
     * @return true if command parsing completed, else false
     * @throws SimpleParseException if error occures
     */
    public boolean parse(String input) throws SimpleParseException {
        // Splitting received input
        String strippedInput = input.strip();

        LinkedList<String> inputArgs = new LinkedList<>();
        boolean escaping = false;
        for (int i = 0; i < strippedInput.length(); i++) {
            if (strippedInput.charAt(i) == '\\') {
                escaping = true;
                continue;
            }

            if (strippedInput.charAt(i) == ' ' && !escaping) {
                inputArgs.add("");
            } else {
                if (inputArgs.size() == 0) {
                    inputArgs.add("");
                }

                inputArgs.set(inputArgs.size() - 1, inputArgs.getLast() + strippedInput.charAt(i));
            }

            escaping = false;
        }

        if (inputArgs.size() == 0 && !this.waitingArgs) {
            this.cmd = Command.NOOP;
            this.args = new LinkedList<>();

            return true;
        }

        // Allowing empty args
        if (strippedInput.length() == 0) {
            inputArgs.add(null);
        }

        // New command
        if (!this.waitingArgs) {
            // Resetting data from previous cmd
            this.cmd = null;
            this.args = new LinkedList<>();

            // Checking if given command exists
            String cmdLabel = inputArgs.get(0);
            for (Command cmd : Command.values()) {
                if (cmdLabel.equals(cmd.name().toLowerCase())) {
                    this.cmd = cmd;
                    break;
                }
            }

            if (this.cmd == null) {
                throw new SimpleParseException("Provided command doesn't exist.");
            }

            // Removing command label from splitted input
            inputArgs.pop();

            // Setting flag for argument-parsing
            this.waitingArgs = true;
        }

        if (this.waitingArgs) {
            // Validating arguments
            int argCnt = Math.min(this.cmd.getArgsCount() - this.args.size(),
                                  inputArgs.size());

            for (int i = 0; i < argCnt; i++) {
                String inputArg = inputArgs.get(i);
                int argId = this.args.size();

                Argument arg = this.cmd.getArgument(argId);

                ArgCheck f = arg.getCheck();
                if (f.check(inputArg)) {
                    arg.parse(inputArg);
                    this.args.add(arg);
                } else {
                    System.err.println(arg.getErrorMsg());
                    return false;
                }
            }

            if (this.cmd.getArgsCount() - this.args.size() == 0) {
                // Setting flag for new command
                this.waitingArgs = false;

                // Create command
                this.result = this.cmd;
                this.result.setArgs(this.args);

                return true;
            }
        }

        return false;
    }

    /**
     * @return parsed command or null, if command was not parsed yet
     */
    public Command getResult() {
        return this.result;
    }

    /**
     * @return parsed command or partially parsed command
     */
    public Command getCurrentCommand() {
        return this.cmd;
    }

    /**
     * @return number of parsed arguments
     */
    public int getCurrentArgumentsCnt() {
        return this.args.size();
    }

}
