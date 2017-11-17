package prop.assignment0;

import java.io.IOException;

/**
 * @author Fredrik Larsson frla9839
 */
public class Parser implements IParser {

	private Tokenizer tk = null;
	
	public Parser() {
	}
	
	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		tk = new Tokenizer();
		tk.open(fileName);
		tk.moveNext();		
	}

	@Override
	public INode parse() throws IOException, TokenizerException, ParserException {
		if (tk == null) 
			throw new IOException("No open file");
	
		BlockNode bn = null;
		try {
			bn = new BlockNode(tk);
		} catch (TokenizerException e) {
			throw e;
		} catch (ParserException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return bn;
	}

	@Override
	public void close() throws IOException {
		tk.close();		
	}
	
}
