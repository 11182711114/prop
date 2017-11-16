package prop.assignment0;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer implements ITokenizer {

	private Scanner scanner;
	private Lexeme current = null;
	private Lexeme next = null;
	
	private static String intPattern;
	private static String idPattern;
	private static Map<Character,Token> symbols;

	public Tokenizer() {
		intPattern = "[0-9]+";
		idPattern = "[a-z]+";
		
		symbols = new HashMap<>();
		symbols.put('+', Token.ADD_OP);
		symbols.put('-', Token.SUB_OP);
		symbols.put('*', Token.MULT_OP);
		symbols.put('/', Token.DIV_OP);
		symbols.put('=', Token.ASSIGN_OP);
		symbols.put('(', Token.LEFT_PAREN);
		symbols.put(')', Token.RIGHT_PAREN);
		symbols.put(';', Token.SEMICOLON);
		symbols.put('{', Token.LEFT_CURLY);
		symbols.put('}', Token.RIGHT_CURLY);
		symbols.put(Scanner.EOF, Token.EOF);
	}
	
	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		scanner = new Scanner();
		scanner.open(fileName);
		scanner.moveNext();
		this.moveNext();
	}

	//Modified from the SeminarOne Tokenizer
	private Lexeme extractLexeme() throws IOException, TokenizerException {
		consumeWhiteSpaces();

		Character ch = scanner.current();
		StringBuilder strBuilder = new StringBuilder();
		
		if (ch == Scanner.EOF)
			return new Lexeme(ch, Token.EOF);
		else if (Character.isLetterOrDigit(ch)) {
			while (Character.isLetterOrDigit(scanner.current())) {
				strBuilder.append(scanner.current());
				scanner.moveNext();
			}
			String lexeme = strBuilder.toString();
			
			if (lexeme.matches(intPattern)) {
				return new Lexeme(lexeme, Token.INT_LIT);
			} 
			else if (lexeme.matches(idPattern)) {
				return new Lexeme(lexeme, Token.IDENT);
			}
			else
				throw new TokenizerException("Unknown lexeme: " + strBuilder.toString());
		} 
		else if (symbols.containsKey(ch)) {
			scanner.moveNext();
			return new Lexeme(ch, symbols.get(ch));
		} 
		else
			throw new TokenizerException("Unknown character: " + String.valueOf(ch));
	}

	private void consumeWhiteSpaces() throws IOException {
		while (Character.isWhitespace(scanner.current())) {
			scanner.moveNext();
		}
	}

	@Override
	public Lexeme current() {
		return current;
	}

	@Override
	public void moveNext() throws IOException, TokenizerException {
		current = next;
		next = extractLexeme();
	}

	@Override
	public void close() throws IOException {
		scanner.close();
	}
	
	@Override
	public String toString() {
		String toReturn = "current: ";
		
		if (current == null)
			toReturn+="null";
		else 
			toReturn+=current;
		toReturn+= " next: ";
		if (next == null)
			toReturn+="null";
		else
			toReturn+=next;
		return toReturn;
	}
	
}
