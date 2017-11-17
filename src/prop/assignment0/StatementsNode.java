package prop.assignment0;

import java.io.IOException;
/**
 * @author Fredrik Larsson frla9839
 */
public class StatementsNode implements INode {
	
	private AssignmentNode assign;
	private StatementsNode stmts;
	
	public StatementsNode(Tokenizer t) throws IOException, TokenizerException {
		if (t.current().token() != Token.RIGHT_CURLY) {			
			assign = new AssignmentNode(t);
			stmts = new StatementsNode(t);
		}
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		if (assign != null) {
			assign.evaluate(args);
			stmts.evaluate(args);
			return null;
		}
		return null;
	}
	
	public int nrAssignments() {
		if (stmts == null)
			return 0;
		return 1 + stmts.nrAssignments();
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		for (int i = 0; i<tabs; i++) builder.append("\t");
		builder.append("StatementsNode\n");
		if (assign != null)
			assign.buildString(builder, tabs+1);
		if (stmts != null)
			stmts.buildString(builder, tabs+1);
	}

}
