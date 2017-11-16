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
		Lexeme factorValue = (Lexeme) factor.evaluate(args);
		
		if (factorValue.token() == Token.IDENT) {
			for (int i = 0; i < args.length-2; i+=2) {
				if (((String) args[i]).equals((String) factorValue.value()))
					factorValue = (Lexeme) args[i+1];
			}
		}
			
			
		if (multOrDivOperator != null && factorValue.token() == Token.INT_LIT) {
			Lexeme termValue = (Lexeme) term.evaluate(args);
			if (termValue.token() == Token.IDENT) {
				for (int i = 0; i < args.length-1; i+=2) {
					if (args[i] == termValue)
						termValue = (Lexeme) args[i+1];
				}
			}
			
			
			if (multOrDivOperator.token() == Token.MULT_OP) {
				Double factorValueDouble = Double.parseDouble(
						(String) factorValue.value());
				Double termValueDouble = Double.parseDouble(
						(String) termValue.value());
				System.out.println("OP: " + factorValueDouble + "*" + termValueDouble);
				return new Lexeme(
						(Double.toString(
							factorValueDouble 
							* 
							termValueDouble)), 
						Token.INT_LIT);
			}
			Double factorValueDouble = Double.parseDouble(
					(String) factorValue.value());
			Double termValueDouble = Double.parseDouble(
					(String) termValue.value());
			System.out.println("OP: " + factorValueDouble + "/" + termValueDouble);
			return new Lexeme(
					(Double.toString(
						factorValueDouble 
						/ 
						termValueDouble)), 
					Token.INT_LIT);
		}
		
		return factorValue;
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Util.appendTabs(builder, tabs);
		builder.append("TermNode\n");
		
		factor.buildString(builder, tabs+1);
		
		if(multOrDivOperator == null && term == null)
			return;
		
		Util.appendTabs(builder, tabs+1);
		builder.append(multOrDivOperator+"\n");
		
		term.buildString(builder, tabs+1);
	}

}
