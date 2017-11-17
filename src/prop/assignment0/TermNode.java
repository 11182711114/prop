package prop.assignment0;

import java.io.IOException;

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
		
		//If factorValue is String args array is checked if it contains the variable which will return null if it is not present
		if (factorValue instanceof String)
			factorValueAsDouble = Util.findVariableValue((String) factorValue, args);
		else
			factorValueAsDouble = (Double) factorValue;

		if (multOrDivOperator != null) {
			Object termValue = term.evaluate(args);
			Double termValueAsDouble = null;
			
			if (termValue instanceof String)
				termValueAsDouble = Util.findVariableValue((String) termValue, args);
			else
				termValueAsDouble = (Double) termValue;

			//Multiplication
			if (multOrDivOperator.token() == Token.MULT_OP)	{
				System.out.println("OP: " + factorValueAsDouble + "*" + termValueAsDouble + " = " + factorValueAsDouble*termValueAsDouble );
				return new Double(factorValueAsDouble*termValueAsDouble);
			}
			//Division
			System.out.println("OP: " + factorValueAsDouble + "/" + termValueAsDouble + " = " + factorValueAsDouble/termValueAsDouble );
			return new Double(factorValueAsDouble/termValueAsDouble);
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

}
