package net.ncrash.cbmax.core.creditcard;

import net.ncrash.cbmax.core.creditcard.parser.BcCardParser;
import net.ncrash.cbmax.core.creditcard.parser.CityCardParser;
import net.ncrash.cbmax.core.creditcard.parser.HanaSkCardParser;
import net.ncrash.cbmax.core.creditcard.parser.HyundaiCardParser;
import net.ncrash.cbmax.core.creditcard.parser.KbCardParser;
import net.ncrash.cbmax.core.creditcard.parser.KebCardParser;
import net.ncrash.cbmax.core.creditcard.parser.LotteCardParser;
import net.ncrash.cbmax.core.creditcard.parser.SamsungCardParser;
import net.ncrash.cbmax.core.creditcard.parser.ShinhanCardParser;

/**
 * 
 * @author daekwon.kang
 * @since 2010. 4. 29.
 * @see 
 */
public class CreditCardSmsParserFactory {
	public static CreditCardSmsParser getParser(String parserId) {
		if(("BC").equals(parserId)) {
			return BcCardParser.getBcCardParser();
		} else if(("CITY").equals(parserId)) {
			return CityCardParser.getCityCardParser();
		} else if(("KB").equals(parserId)) {
			return KbCardParser.getKbCardParser();
		} else if(("SHINHAN").equals(parserId)) {
			return ShinhanCardParser.getShinhanCardParser();
		} else if(("KEB").equals(parserId)) {
			return KebCardParser.getKebCardParser();
		} else if(("HYUNDAI").equals(parserId)) {
			return HyundaiCardParser.getHyundaiCardParser();
		} else if(("LOTTE").equals(parserId)) {
			return LotteCardParser.getLotteCardParser();
		} else if(("SAMSUNG").equals(parserId)) {
			return SamsungCardParser.getSamsungCardParser();
		} else if(("HANA_SK").equals(parserId)) {
			return HanaSkCardParser.getHanaSkCardParser();
		}

		return null;
	}
}
