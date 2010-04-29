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
		} else if(("CITY").equals(parserId)) {
			return new CityCardParser();
		} else if(("KB").equals(parserId)) {
			return new KbCardParser();
		} else if(("SHINHAN").equals(parserId)) {
			return new ShinhanCardParser();
		} else if(("KEB").equals(parserId)) {
			return new KebCardParser();
		} else if(("HYUNDAI").equals(parserId)) {
			return new HyundaiCardParser();
		} else if(("LOTTE").equals(parserId)) {
			return new LotteCardParser();
		}

		return null;
	}
}
