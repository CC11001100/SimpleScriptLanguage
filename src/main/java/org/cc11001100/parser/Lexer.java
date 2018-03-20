package org.cc11001100.parser;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author CC11001100
 */
public class Lexer {

	/**
	 * 注释：
	 * 数值：
	 * 文本：
	 * 标识符：
	 * 逻辑运算：
	 */
	private static String regexPat = "\\s*((//.*|#.*)|([0-9]*.[0-9]+|[0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")" +
			"|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";

	private Pattern pattern = Pattern.compile(regexPat);
	private List<Token> queue = new ArrayList<>();
	private Boolean hasMore;
	private LineNumberReader reader;

	public Lexer(Reader reader) {
		hasMore = true;
		this.reader = new LineNumberReader(reader);
	}

	public Token read() {
		if (fillQueue(0)) {
			return queue.remove(0);
		} else {
			return Token.EOF;
		}
	}

	public Token peek(int i) {
		if (fillQueue(i)) {
			return queue.get(i);
		} else {
			return Token.EOF;
		}
	}

	private boolean fillQueue(int i) {
		while (i >= queue.size()) {
			if (hasMore) {
				readLine();
			} else {
				return false;
			}
		}
		return true;
	}

	private void readLine() {
		String line;
		try {
			line = reader.readLine();
		} catch (IOException e) {
//			e.printStackTrace();
			throw new ParserException();
		}
		if (line == null) {
			hasMore = false;
			return;
		}
		int lineNo = reader.getLineNumber();
		Matcher matcher = pattern.matcher(line);
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos = 0;
		int endPos = line.length();
		while (pos < endPos) {
			matcher.region(pos, endPos);
			if (matcher.lookingAt()) {
				addToken(lineNo, matcher);
				pos = matcher.end();
			} else {
				throw new ParserException("syntax error");
			}
		}
	}

	private void addToken(int lineNo, Matcher matcher) {
		String m = matcher.group(1);
		if (m != null) { // 不是空格
			if (matcher.group(2) == null) { // 不是注释
				Token token;
				if (matcher.group(3) != null) {
					token = new NumberToken(lineNo, Double.valueOf(m));
				} else if (matcher.group(4) != null) {
					token = new StringToken(lineNo, toStringLiteral(m));
				} else {
					token = new IdToken(lineNo, m);
				}
				queue.add(token);
			}
		}
	}

	// 将字符串中的转义字符去除
	private String toStringLiteral(String s) {
		StringBuilder result = new StringBuilder(s.length());
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\\' && i + 1 < s.length()) {
				char nextChar = s.charAt(i + 1);
				if (nextChar == '"' || nextChar == '\\') {
					result.append(nextChar);
					++i;
				} else if (nextChar == 'n') {
					result.append("\n");
					++i;
				}
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	public class NumberToken extends Token {

		private Double value;

		NumberToken(int lineNumber, double value) {
			super(lineNumber);
			this.value = value;
		}

		@Override
		public double getNumber() {
			return value;
		}

		@Override
		public boolean isNumber() {
			return true;
		}
	}

	public class StringToken extends Token {

		private String value;

		StringToken(Integer lineNumber, String value) {
			super(lineNumber);
			this.value = value;
		}

		@Override
		public String getText() {
			return value;
		}

		@Override
		public boolean isString() {
			return true;
		}
	}

	public class IdToken extends Token {

		private String text;

		IdToken(Integer lineNumber, String text) {
			super(lineNumber);
			this.text = text;
		}

		@Override
		public String getText() {
			return text;
		}

		@Override
		public boolean isIdentifier() {
			return true;
		}
	}

}
