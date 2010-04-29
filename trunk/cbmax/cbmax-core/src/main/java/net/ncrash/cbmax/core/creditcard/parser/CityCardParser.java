package net.ncrash.cbmax.core.creditcard.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ncrash.cbmax.core.creditcard.CreditCardSmsParser;
import net.ncrash.cbmax.core.dto.CreditCardReceiptSms;

/**
 * 
 * @author daekwon.kang
 * @since 2010. 4. 29.
 * @see
 */
public class CityCardParser implements CreditCardSmsParser {

	public List<CreditCardReceiptSms> receiptSmsParse(String mmsContent) {
		List<CreditCardReceiptSms> result = new ArrayList<CreditCardReceiptSms>();
		CreditCardReceiptSms creditCardReceiptSms;

		/*
			씨티카드 강대권님 승인내역 03월13일 19:47 이마트구로점 40,410원 일시불
		 */
		Pattern p = Pattern
		.compile("(씨티카드) (.*님) (승인|취소)(내역) (.*월.*일) ([\\d]*:[\\d]*) (.*) ([0-9,]*)(원) (일시불|.*$)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardReceiptSms = new CreditCardReceiptSms();

			creditCardReceiptSms.setSenderPhoneNumber("01027976877");
			creditCardReceiptSms.setSenderName(m.group(2));
			creditCardReceiptSms.setCardCompanyName(m.group(1));
			creditCardReceiptSms.setCardLastFourNumber(null);
			creditCardReceiptSms.setPayedWhenDate(m.group(5));
			creditCardReceiptSms.setPayedWhenTime(m.group(6));
			creditCardReceiptSms.setPayedWhere(m.group(7));
			creditCardReceiptSms.setPayedMoney(m.group(8));
			creditCardReceiptSms.setPayedCardType(null);
			creditCardReceiptSms.setPayedApproveType(m.group(3));
			creditCardReceiptSms.setPayedLumpSumOrInstallmentPlan(m.group(10));

			result.add(creditCardReceiptSms);
		}

		return result;
	}
}
