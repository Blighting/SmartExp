package Notations;

public class NotationsEvaluator {
    private final static char T_DOT = '.';
    private final static char T_MINUS = '-';
    private final static int ACCURACY = 100;

    private int from;
    private int into;
    private String number;

    public NotationsEvaluator(int from, int into, String number) {
        this.from = from;
        this.into = into;
        this.number = number.toLowerCase();
    }

    public NotationsEvaluator(int into, String number) {
        this.from = 10;
        this.into = into;
        this.number = number.toLowerCase();
    }

    public String get() {
        return number.toUpperCase();
    }

    public NotationsEvaluator calculate() {
        boolean negative = isNegative();
        if (negative) {
            number = number.substring(1);
        }
        if (isFractional()) {
            number = String.format(
                    "%s%c%s",
                    convertInteger(number.substring(0, number.indexOf(T_DOT))),
                    T_DOT,
                    convertFractional(number.substring(number.indexOf(T_DOT) + 1, number.length()))
            );
        } else {
            number = convertInteger(number);
        }
        if (negative) {
            number = String.format("%c%s", T_MINUS, number);
        }
        return this;
    }

    public NotationsEvaluator validate() throws Exception {
        for (int i = 0; i < number.length(); i++) {
            if (!isSpecialChar(number.charAt(i)) && toInt(number.charAt(i)) >= from) {
                throw new Exception("Not a valid number given!");
            }
        }
        return this;
    }

    private boolean isSpecialChar(char sym) {
        return sym == T_MINUS || sym == T_DOT;
    }

    private boolean isFractional() {
        return number.indexOf(T_DOT) != -1;
    }

    private boolean isNegative() {
        return number.charAt(0) == T_MINUS;
    }

    private String convertInteger(String num) {
        int decimal = 0;
        for (int i = num.length() - 1, j = 0; i >= 0; i--, j++) {
            decimal += toInt(num.charAt(i)) * Math.pow(from, j);
        }
        StringBuilder result = new StringBuilder();
        do {
            result.insert(0, toSym(decimal % into));
            decimal = (int) Math.floor(decimal / into);
        } while (decimal != 0);
        return result.toString();
    }

    private String convertFractional(String num) {
        double decimal = 0;
        for (int i = 0, j = -1; i < num.length(); i++, j--) {
            decimal += toInt(num.charAt(i)) * Math.pow(from, j);
        }
        if (decimal == 0) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ACCURACY && decimal != 0; i++) {
            decimal *= into;
            result.append(toSym((int) Math.floor(decimal)));
            decimal = decimal - Math.floor(decimal);
        }
        return result.toString();
    }

    private int toInt(char sym) {
        for (int i = 10; i <= 36; i++) {
            if (Integer.toString(i, 36).equals(Character.toString(sym))) {
                return i;
            }
        }
        return Character.getNumericValue(sym);
    }

    private String toSym(int num) {
        return Integer.toString(num, into);
    }
}
