package prop.assignment0;

import java.io.IOException;

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
//		if (t.current().token() != Token.EOF) {
//			expr = new ExpressionNode(t);
//		}

	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		Object termValue = term.evaluate(args);
		Double termValueAsDouble = null;

		if (termValue instanceof String)
			termValueAsDouble = Util.findVariableValue((String) termValue, args);
		else
			termValueAsDouble = (Double) termValue;

		if (plusOrMinus != null) {
			Object exprValue = expr.evaluate(args);
			Double exprValueAsDouble = null;
			
			if (exprValue instanceof String)
				exprValueAsDouble = Util.findVariableValue((String) exprValue, args);
			else
				exprValueAsDouble = (Double) exprValue;

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
//		Lexeme termValue = (Lexeme) term.evaluate(args);
//
//		if (termValue.token() == Token.IDENT) {
//			for (int i = 0; i < args.length-2; i+=2) {
//				if (args[i] == termValue)
//					termValue = (Lexeme) args[i+1];
//			}
//		}
//		
//		if (plusOrMinus != null) {
//			Lexeme exprValue = (Lexeme) expr.evaluate(args);
//			
//			if (exprValue.token() == Token.IDENT) {
//				for (int i = 0; i < args.length-2; i+=2) {
//					if (args[i] == exprValue)
//						exprValue = (Lexeme) args[i+1];
//				}
//			}
//			
//			if (plusOrMinus.token() == Token.ADD_OP) {
//				Double termValueDouble = Double.parseDouble(
//						(String) termValue.value());
//				Double exprValueDouble = Double.parseDouble(
//						(String) exprValue.value());
//				System.out.println("OP: " + termValueDouble + "+" + exprValueDouble);
//				return new Lexeme(
//						(Double.toString(
//							termValueDouble
//							+ 
//							exprValueDouble)),
//						Token.INT_LIT);
//			}
//			Double termValueDouble = Double.parseDouble(
//					(String) termValue.value());
//			Double exprValueDouble = Double.parseDouble(
//					(String) exprValue.value());
//			System.out.println("OP: " + termValueDouble + "-" + exprValueDouble);
//			return new Lexeme(
//					(Double.toString(
//						termValueDouble 
//						- 
//						exprValueDouble)), 
//					Token.INT_LIT);
//		}
//		return termValue;
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
