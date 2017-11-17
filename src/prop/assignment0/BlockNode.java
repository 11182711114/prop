package prop.assignment0;

public class BlockNode implements INode {

	private Lexeme leftCurly, rightCurly;
	private StatementsNode stmt;
	
	public BlockNode(Tokenizer t) throws Exception {
		leftCurly = t.current();
		t.moveNext();
		
		if(leftCurly.token() != Token.LEFT_CURLY)
			throw new Exception("NO OPENING LEFT CURLY");
		
		stmt = new StatementsNode(t);
		
		if (t.current().token() != Token.EOF) {
			rightCurly = t.current();
		}
		if (rightCurly.token() != Token.RIGHT_CURLY)
			throw new Exception("NO FINISHING RIGHT CURLY");
		
	}
	
	
	@Override
	public Object evaluate(Object[] args) throws Exception {
		// FIXME BAD BAD BAD but I dont know how to make it work without knowing the amount of identifiers in the program before evaluating. I suppose I can count the number of assignment nodes...
		Object[] currentValues = new Object[6];
		stmt.evaluate(currentValues);
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < currentValues.length; i+=2) {
			builder.append(currentValues[i] + " = ");
			builder.append(currentValues[i+1] + "\n");
		}
		return builder.toString();
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Util.appendTabs(builder,tabs);
		builder.append("BlockNode\n");
		
		Util.appendTabs(builder,tabs);
		builder.append(leftCurly + "\n");
		
		stmt.buildString(builder, tabs+1);
		
		Util.appendTabs(builder,tabs);
		builder.append(rightCurly + "\n");
	}
}
