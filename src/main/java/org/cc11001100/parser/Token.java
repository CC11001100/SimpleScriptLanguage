package org.cc11001100.parser;

/**
 * @author CC11001100
 */
public class Token {

	public static final Token EOF = new Token(-1);
	public static final String EOL = "\\n";
	Integer lineNumber;

	public Token(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	// 是否是标识符
	public boolean isIdentifier() {
		return false;
	}

	// 是否是整数
	public boolean isNumber() {
		return false;
	}

	// 是否是字符串
	public boolean isString() {
		return false;
	}

	public double getNumber() {
		throw new StoneException();
	}

	public String getText() {
		throw new StoneException();
	}

}
