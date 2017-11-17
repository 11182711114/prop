package prop.assignment0;

import java.io.IOException;
/**
 * @author Fredrik Larsson frla9839
 */
public class TermNode implements INode {

	private FactorNode factor;
	private Lexeme multOrDivOperator;
	private TermNode term;

	public TermNode(Tokenizer t) throws IOException, TokenizerException {
		factor = new FactorNode(t);

		if (t.current().token() == Token.MULT_OP || t.current().token() == Token.DIV_OP) {
			multOrDivOperator = t.current();
			t.moveNext();

			term = new TermNode(t);
		}
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {		
		Object factorValue = factor.evaluate(args);
		Double factorValueAsDouble = null;

		if (factorValue instanceof String)
			factorValueAsDouble = Util.findVariableValue((String) factorValue, args);
		else
			factorValueAsDouble = (Double) factorValue;

		// exprValueAsDouble != null -> we are actually doing an operation
		if (multOrDivOperator != null) {
//			Object termValue = term.evaluate(args);
			Double termValueAsDouble = null;
			
			TermNode nextTerm = term;
			while(nextTerm.multOrDivOperator != null) {
				Object s = nextTerm.term.evaluate(args);
				if (s instanceof String)
					termValueAsDouble = Util.findVariableValue((String) s, args);
				else
					termValueAsDouble = (Double) s;
				
				//Multiplication
				if (multOrDivOperator.token() == Token.MULT_OP) {
					System.out.println("OP: " + factorValueAsDouble + "*" + factorValueAsDouble + " = " + (factorValueAsDouble*termValueAsDouble) );
					factorValueAsDouble *= termValueAsDouble;
				}
				//Division
				System.out.println("OP: " + factorValueAsDouble + "/" + termValueAsDouble + " = " + (factorValueAsDouble/termValueAsDouble) );
				factorValueAsDouble /= termValueAsDouble;
			}			
		}

		return factorValue;
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Util.appendTabs(builder, tabs);
		builder.append("TermNode\n");

		factor.buildString(builder, tabs + 1);

		if (multOrDivOperator == null && term == null)
			return;

		Util.appendTabs(builder, tabs + 1);
		builder.append(multOrDivOperator + "\n");

		term.buildString(builder, tabs + 1);
	}
	
	@Override
	public String toString() {
		return factor.toString() + multOrDivOperator != null ?  (multOrDivOperator.value() + term.toString()) : "";
	}

	
	public FactorNode getFactor() {
		return factor;
	}
}
