package net.ncrash.cbmax.core.creditcard;

/**
 * 신용카드 회사
 * 
 * @author Daekwon.Kang
 * @since 2010.04.24 
 * @see 
 */
public class CreditCardCompany {
	private CreditCardSmsParser parser;

	public CreditCardCompany() {
	}
	
	public CreditCardCompany(CreditCardSmsParser parser) {
		super();
		this.parser = parser;
	}

	public void setParser(CreditCardSmsParser parser) {
		this.parser = parser;
	}
	
	public CreditCardSmsParser getParser() {
		return parser;
	}
}
