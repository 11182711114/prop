package prop.assignment0;

import java.io.IOException;
/**
 * @author Fredrik Larsson frla9839
 */
public class AssignmentNode implements INode {

	private Lexeme id;
	private Lexeme eqsOperator;
	private ExpressionNode expr;
	private Lexeme colon;
	
	
	public AssignmentNode(Tokenizer t) throws IOException, TokenizerException {
		id = t.current();
		t.moveNext();
		
		eqsOperator = t.current();
		t.moveNext();
		
		expr = new ExpressionNode(t);
		
		colon = t.current();
		t.moveNext();
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		//args simply store variables as ["varName", value, ...]
		for (int i = 0; i < args.length-1; i+=2) {
			if (args[i] == null) {
				args[i] = id.value();
				System.out.println("NEW ASSIGNMENT EVAL: " + id.value());
				args[i+1] = expr.evaluate(args);
				System.out.println(args[i] + " = " + args[i+1]);
				break;
			}
		}
		return args[args.length-1];
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		Util.appendTabs(builder,tabs);
		builder.append("AssignmentNode\n");
		
		if (id == null || eqsOperator == null || expr == null || colon == null)
			return;
		
		Util.appendTabs(builder,tabs+1);
		builder.append(id + "\n");
		
		Util.appendTabs(builder,tabs+1);
		builder.append(eqsOperator + "\n");
		
		expr.buildString(builder, tabs+1);
		
		Util.appendTabs(builder,tabs+1);
		builder.append(colon + "\n");
	}
}
