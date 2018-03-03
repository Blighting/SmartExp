package SmartExp;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.Var;

@BuildParseTree
class CalculatorParser extends BaseParser<CalcNode> {

    Rule InputLine() {
        return Sequence(Expression(), EOI);
    }

    Rule Expression() {
        Var<Character> operator = new Var<Character>();
        return Sequence(
                Multiplication(),
                ZeroOrMore(
                        FirstOf(CalcNode.T_PLUS, CalcNode.T_MINUS),
                        operator.set(matchedChar()),
                        Multiplication(),
                        push(new CalcNode(operator.get(), pop(1), pop()))
                )
        );
    }

    Rule Multiplication() {
        Var<Character> operator = new Var<Character>();
        return Sequence(
                Power(),
                ZeroOrMore(
                        FirstOf(CalcNode.T_MULT, CalcNode.T_DIV),
                        operator.set(matchedChar()),
                        Power(),
                        push(new CalcNode(operator.get(), pop(1), pop()))
                )
        );
    }

    Rule Power() {
        return Sequence(
                Atom(),
                Optional(
                        '^',
                        Number(),
                        push(new CalcNode(CalcNode.T_POW, pop(1), pop()))
                )
        );
    }



    Rule SQRT() {
        return Sequence(
                "√",
                Parentheses(),
                push(new CalcNode(CalcNode.T_SQRT, pop(), null))
        );
    }

    Rule Parentheses() {
        return Sequence(
                '(',
                Expression(),
                ')'
        );
    }

    Rule Notation() {
        return Sequence(
                Number(),
                Optional(
                        '@',
                        NotationNumber(),
                        push(CalcNode.createFromNotation(pop(1).getValue(), Integer.parseInt(pop().getValue())))
                )
        );
    }

    Rule NotationNumber() {
        return Sequence(
                OneOrMore(CharRange('0', '9')),
                push(new CalcNode(match()))
        );
    }
    Rule Atom() {
        return FirstOf(Pi(), SQRT(), Notation(), Parentheses());
    }

    Rule Pi() {
        return Sequence(
                "π",
                push(new CalcNode(String.valueOf(Math.PI)))
        );
    }

    Rule Number() {
        return Sequence(
                Sequence(
                        Optional('-'),
                        OneOrMore(Digit()),
                        Optional('.', OneOrMore(Digit()))
                ),
                push(new CalcNode(match()))
        );
    }

    Rule Digit() {
        return OneOrMore(
                FirstOf(
                        CharRange('0', '9'),
                        CharRange('A', 'Z')
                )
        );
    }
}
