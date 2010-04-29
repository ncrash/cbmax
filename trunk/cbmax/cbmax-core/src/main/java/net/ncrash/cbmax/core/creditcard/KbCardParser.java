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
public class KbCardParser implements CreditCardSmsParser {

	public List<CreditCardReceiptSms> receiptSmsParse(String mmsContent) {
		List<CreditCardReceiptSms> result = new ArrayList<CreditCardReceiptSms>();
		CreditCardReceiptSms creditCardReceiptSms;

		/*
			[KB체크]
			강대권님
			04월05일12:47
			광양불고기
			52,000원 사용
			
			[KB카드]
			강대권님
			03월27일17:13
			이마트구로점
			40,480원 사용
		 */
		Pattern p = Pattern
		.compile("\\[(KB)(카드|체크)\\]\\n(.*님)\\n(\\d{2}월\\d{2}일)(\\d{2}:\\d{2})\\n(.*\\b)\\n([0-9,]*)(원) (사용)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardReceiptSms = new CreditCardReceiptSms();

			creditCardReceiptSms.setSenderName(m.group(3));
			creditCardReceiptSms.setCardCompanyName(m.group(1));
			creditCardReceiptSms.setCardLastFourNumber(null);
			creditCardReceiptSms.setPayedWhenDate(m.group(4));
			creditCardReceiptSms.setPayedWhenTime(m.group(5));
			creditCardReceiptSms.setPayedWhere(m.group(6));
			creditCardReceiptSms.setPayedMoney(m.group(7));
			creditCardReceiptSms.setPayedCardType(m.group(2));
			creditCardReceiptSms.setPayedApproveType(null);
			creditCardReceiptSms.setPayedLumpSumOrInstallmentPlan(null);

			result.add(creditCardReceiptSms);
		}

		return result;
	}
}
