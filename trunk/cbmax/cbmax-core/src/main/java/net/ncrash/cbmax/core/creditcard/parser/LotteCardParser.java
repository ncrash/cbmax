package net.ncrash.cbmax.core.creditcard.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ncrash.cbmax.core.creditcard.CreditCardSmsParser;
import net.ncrash.cbmax.core.dto.CreditCardAutoPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardPaymentSms;

/**
 * 
 * @author daekwon.kang
 * @since 2010. 4. 29.
 * @see 
 */
public class LotteCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardReceiptSms;

		/*
			롯데카드 강대권님 939,900원 일시불 04/17 10:16 이베이옥션                       
			롯데카드 강대권님 33,800원 일시불 04/21 23:55 (주)이니시스                      
		 */
		Pattern p = Pattern
		.compile("(롯데카드) (.*님) ([0-9,\\.]*)(원) (일시불) (\\d{2}/\\d{2}) (\\d{2}:\\d{2}) (.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardReceiptSms = new CreditCardPaymentSms();

			creditCardReceiptSms.setSenderName(m.group(2));
			creditCardReceiptSms.setCardCompanyName(m.group(1));
			creditCardReceiptSms.setCardLastFourNumber(null);
			creditCardReceiptSms.setPayedWhenDate(m.group(6));
			creditCardReceiptSms.setPayedWhenTime(m.group(7));
			creditCardReceiptSms.setPayedWhere(m.group(8));
			creditCardReceiptSms.setPayedMoney(m.group(3));
			creditCardReceiptSms.setPayedCardType(m.group(1));
			creditCardReceiptSms.setPayedApproveType(m.group(1));
			creditCardReceiptSms.setPayedLumpSumOrInstallmentPlan(m.group(5));

			result.add(creditCardReceiptSms);
		}

		return result;
	}

	public List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}
}
