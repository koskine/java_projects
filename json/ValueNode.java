/*
    Author: Iiro Koskinen H299947
*/

public class ValueNode extends Node {
    private double number;
    private boolean numberBool = false;
    private boolean valBoolean;
    private boolean booleanBool = false;
    private String valString;


    public ValueNode(double value) {
        number = value;
        numberBool = true;
    }

    public ValueNode(boolean value) {
        valBoolean = value;
        booleanBool = true;
    }

    public ValueNode(String value) {
        valString = value;
    }

    public boolean isNumber() {
        return numberBool;
    }

    public boolean isBoolean() {
        return booleanBool;
    }

    public boolean isString() {
        if (valString != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isNull() {
        if (!isNumber() && !isBoolean() && valString == null) {
            return true;
        }
        else {
            return false;
        }
    }

    public double getNumber() {
        return this.number;
    }

    public boolean getBoolean() {
        return this.valBoolean;
    }

    public String getString() {
        return this.valString;
    }
}

    

