package net.ncrash.cbmax.core.creditcard;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ncrash.cbmax.core.dto.CreditCardReceiptSms;

/**
 * 
 * @author daekwon.kang
 * @since 2010. 4. 29.
 * @see 
 */
public class HyundaiCardParser implements CreditCardSmsParser {

	public List<CreditCardReceiptSms> receiptSmsParse(String mmsContent) {
		List<CreditCardReceiptSms> result = new ArrayList<CreditCardReceiptSms>();
		CreditCardReceiptSms creditCardReceiptSms;

		/*
			[현대카드C]
			강대권님
			12:10
			2,400원(일시불)
			정상승인
			온누리조은약국
		 */
		Pattern p = Pattern
		.compile("\\[(현대카드)(\\w)\\]\\n(.*님)\\n(\\d{2}:\\d{2})\\n([0-9,\\.]*)(원)\\((일시불)\\)\\n(정상승인)\\n(.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardReceiptSms = new CreditCardReceiptSms();

			creditCardReceiptSms.setSenderName(m.group(3));
			creditCardReceiptSms.setCardCompanyName(m.group(1));
			creditCardReceiptSms.setCardLastFourNumber(null);
			creditCardReceiptSms.setPayedWhenDate(null);
			creditCardReceiptSms.setPayedWhenTime(m.group(4));
			creditCardReceiptSms.setPayedWhere(m.group(9));
			creditCardReceiptSms.setPayedMoney(m.group(5));
			creditCardReceiptSms.setPayedCardType(m.group(2));
			creditCardReceiptSms.setPayedApproveType(m.group(8));
			creditCardReceiptSms.setPayedLumpSumOrInstallmentPlan(m.group(7));

			result.add(creditCardReceiptSms);
		}

		return result;
	}
}
