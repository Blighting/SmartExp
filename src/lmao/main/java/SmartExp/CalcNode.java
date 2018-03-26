package SmartExp;

import Notations.NotationsEvaluator;
import org.parboiled.trees.ImmutableBinaryTreeNode;

public class CalcNode extends ImmutableBinaryTreeNode<CalcNode> {

    public final static char T_PLUS = '+';
    public final static char T_MINUS = '-';
    public final static char T_MULT = '×';
    public final static char T_DIV = '÷';
    public final static char T_POW = '^';
    public final static char T_SQRT = '√';

    private String value;
    private Character operator;

    public CalcNode(String value) {
        super(null, null);
        this.value = value;
    }

    public CalcNode(Character operator, CalcNode left, CalcNode right) {
        super(left, right);
        this.operator = operator;
    }

    public static CalcNode createFromNotation(String value, int notation) {
        NotationsEvaluator calculator = new NotationsEvaluator(notation, 10, value);
        try {
            calculator.validate();
        } catch (Exception error) {
            System.err.println("Несуществуещее число!");
            System.exit(1);
        }
        calculator.calculate();
        return new CalcNode(calculator.get());
    }

    public String toString() {
        return getValue();
    }

    public String getValue() {
        if (operator == null) {
            return value;
        } else if (operator == T_SQRT) {
            return String.valueOf(Math.sqrt(Double.parseDouble(left().getValue())));
        }
        double left = Double.parseDouble(left().getValue());
        double right = Double.parseDouble(right().getValue());
        switch (operator) {
            case T_PLUS:
                return String.valueOf(left + right);
            case T_MINUS:
                return String.valueOf(left - right);
            case T_MULT:
                return String.valueOf(left * right);
            case T_DIV:
                return String.valueOf(left / right);
            case T_POW:
                return String.valueOf(Math.pow(left, right));
            default:
                throw new IllegalStateException();
        }
    }
}
