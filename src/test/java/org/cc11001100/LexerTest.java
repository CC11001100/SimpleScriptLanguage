package org.cc11001100;

import org.cc11001100.parser.Lexer;
import org.cc11001100.parser.Token;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CC11001100
 */
public class LexerTest {

	@Test
	public void test_001() throws FileNotFoundException {

		Reader reader = new FileReader("D:/test/script/a.txt");
		Lexer lexer = new Lexer(reader);

		List<Token> tokenList = new ArrayList<>();
		Token token;
		do {
			tokenList.add(token = lexer.read());
		} while (token != Token.EOF);

		tokenList.forEach(x -> {
			if (x instanceof Lexer.IdToken) {
				System.out.printf("id token, %s %s \n", x.getLineNumber(), x.getText());
			} else if (x instanceof Lexer.StringToken) {
				System.out.printf("string token, %s %s \n", x.getLineNumber(), x.getText());
			} else if (x instanceof Lexer.NumberToken) {
				System.out.printf("number token, %s %g \n", x.getLineNumber(), x.getNumber());
			} else if (x == Token.EOF) {
				System.out.printf("eof token\n");
			}
		});

	}

}
