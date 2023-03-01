package com.ivatolm.app.humanBeing;

import com.ivatolm.app.database.ISerializable;
import com.ivatolm.app.utils.SimpleParseException;

/**
 * Data structure for Car described in the task
 *
 * @author ivatolm
 */
public class Car implements ISerializable {

    /** Name field */
    private String name;

    /** Cool field */
    private boolean cool;

    /**
     * Constructs dummy-instance of the class.
     * Used to create dummy-instances of the class that will be instantly overriden.
     * Must not be used in typical case.
     */
    public Car() {}

    /**
     * Constructs new instance from the passed agruments.
     * Extracts and casts provided arguments to target types.
     *
     * @param name valid {@code name} argument from command line
     * @param cool valid {@code cool} argument from command line
     */
    public Car(Object name, Object cool) {
        this.name = (String) name;
        this.cool = (boolean) cool;
    }

    /**
     * Implements {@code serialize} for {@code ISerializable}.
     * Serializes fields into {@code String} array.
     *
     * @return serialized object
     */
    @Override
    public String[] serialize() {
        return new String[] { "(" + this.name + "," + this.cool +  ")" };
    }

    /**
     * Implements {@code deserialize} for {@code ISerializable}.
     * Casts input values to target types. Overrides internal values with new ones.
     *
     * @param string serialized object
     * @throws SimpleParseException if input is invalid
     */
    @Override
    public void deserialize(String[] string) throws SimpleParseException {
        String value = string[0];

        String internal = value.substring(1, value.length() - 1);
        String[] data = internal.split(",");

        if (data.length != 2) {
            throw new SimpleParseException(value + " must contain 2 values.");
        }

        this.name = data[0];
        this.cool = Boolean.parseBoolean(data[1]);
    }

}
