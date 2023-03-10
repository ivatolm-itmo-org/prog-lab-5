package com.ivatolm.app.parser.arguments;

/**
 * This class represents integer command line argument.
 *
 * @author ivatolm
 */
public class IntegerArgument extends Argument {

    /**
     * Constructs new instance with provided arguments.
     *
     * @param name name
     * @param check check
     * @param greeingMsg greeting message
     * @param errorMsg error message
     */
    public IntegerArgument(String name, ArgCheck check, String greeingMsg, String errorMsg) {
        super(name, check, greeingMsg, errorMsg);
    }

    /**
     * Implements {@code parse} for {@code IParsable}.
     *
     * @param value value to parse
     */
    @Override
    public void parse(String value) {
        this.value = Integer.parseInt(value);
    }

}
