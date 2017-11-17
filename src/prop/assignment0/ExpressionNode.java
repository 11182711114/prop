package prop.assignment0;

import java.io.IOException;
/**
 * @author Fredrik Larsson frla9839
 */
public class ExpressionNode implements INode{

	private TermNode term;
	private Lexeme plusOrMinus;
	private ExpressionNode expr;
	
	
	public ExpressionNode(Tokenizer t) throws IOException, TokenizerException {
		term = new TermNode(t);
		
		if (t.current().token() == Token.ADD_OP || t.current().token() == Token.SUB_OP) {
			plusOrMinus = t.current();
			t.moveNext();
			
			expr = new ExpressionNode(t);
		}

	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		Object exprValue = null;
		Double exprValueAsDouble = null;
		if (plusOrMinus != null) {
			exprValue = expr.evaluate(args);
			
			if (exprValue instanceof String)
				exprValueAsDouble = Util.findVariableValue((String) exprValue, args);
			else
				exprValueAsDouble = (Double) exprValue;
		}			
		
		Object termValue = term.evaluate(args);
		Double termValueAsDouble = null;

		if (termValue instanceof String)
			termValueAsDouble = Util.findVariableValue((String) termValue, args);
		else
			termValueAsDouble = (Double) termValue;

		// exprValueAsDouble != null -> we are actually doing an operation
		if (exprValueAsDouble != null) {
			//Addition
			if (plusOrMinus.token() == Token.ADD_OP) {
				System.out.println("OP: " + termValueAsDouble + "+" + exprValueAsDouble + " = " + (termValueAsDouble+exprValueAsDouble) );
				return new Double(termValueAsDouble+exprValueAsDouble);
			}
			//Subtraction
			System.out.println("OP: " + termValueAsDouble + "-" + exprValueAsDouble + " = " + (termValueAsDouble-exprValueAsDouble) );
			return new Double(termValueAsDouble-exprValueAsDouble);
		}

		return termValue;
	}
	
	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Util.appendTabs(builder,tabs);
		builder.append("ExpressionNode\n");
		
		term.buildString(builder, tabs+1);
		
		if (plusOrMinus == null && expr == null)
			return;
		
		Util.appendTabs(builder,tabs+1);
		builder.append(plusOrMinus + "\n");
		
		expr.buildString(builder, tabs+1);
		
	}
	
	@Override
	public String toString() {
		return term.toString() + (plusOrMinus != null ? (plusOrMinus.value() + expr.toString()):"");
	}
}
