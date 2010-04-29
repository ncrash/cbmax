package net.ncrash.cbmax.core.creditcard;

/**
 * 
 * @author daekwon.kang
 * @since 2010. 4. 29.
 * @see 
 */
public class CreditCardSmsParserFactory {
	public static CreditCardSmsParser getParser(String parserId) {
		if(("BC").equals(parserId)) {
			return new BcCardParser();
		}

		return null;
	}
}
