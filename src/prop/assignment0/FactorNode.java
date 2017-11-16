package prop.assignment0;

import java.io.IOException;

public class FactorNode implements INode {

	private Lexeme intLex;
	private Lexeme id;
	
	private ExpressionNode expr;
	private Lexeme leftBrace, rightBrace;
	
	
	public FactorNode(Tokenizer t) throws IOException, TokenizerException {
		if (t.current().token() == Token.INT_LIT) {
			intLex = t.current();
			t.moveNext();
		} else if (t.current().token() == Token.IDENT) {
			id = t.current();
			t.moveNext();
		} else {
			leftBrace = t.current();
			t.moveNext();
			expr = new ExpressionNode(t);
			rightBrace = t.current();
			t.moveNext();
		}
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		if (intLex != null) {
			return intLex;
		} else if (id != null) {
			return id;
		} else {
			return expr.evaluate(args);
		}
			
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Util.appendTabs(builder, tabs);
		builder.append("FactorNode\n");
		Util.appendTabs(builder, tabs+1);
		if (intLex != null) 
			builder.append(intLex + ".0\n");
		else if (id != null)
			builder.append(id + "\n");
		else {
			builder.append(leftBrace + "\n");
			expr.buildString(builder, tabs+1);
			Util.appendTabs(builder, tabs+1);
			builder.append(rightBrace + "\n");
		}
	}

	@Override
	public String toString() {
		if (intLex != null)
			return intLex.toString();
		else if (id != null)
			return id.toString();
		else if (leftBrace != null && rightBrace != null)
			return leftBrace.toString() + rightBrace.toString();
		else if (leftBrace != null)
			return leftBrace.toString();	
		else
			return super.toString();
	}
	
}
