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
public class KbCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

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
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderName(m.group(3));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(4));
			creditCardPaymentSms.setPayedWhenTime(m.group(5));
			creditCardPaymentSms.setPayedWhere(m.group(6));
			creditCardPaymentSms.setPayedMoney(m.group(7));
			creditCardPaymentSms.setPayedCardType(m.group(2));
			creditCardPaymentSms.setPayedApproveType(null);
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(null);

			result.add(creditCardPaymentSms);
		}

		return result;
	}

	public List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent) {
		List<CreditCardAutoPaymentSms> result = new ArrayList<CreditCardAutoPaymentSms>();
		CreditCardAutoPaymentSms creditCardAutoPaymentSms;

		/*
			[KB카드]강대권님 카드가 03월11일 SK텔레콤-자동납부에서 10,350원 사용
		 */
		Pattern p = Pattern
		.compile("\\[(KB카드)\\](.*님) (카드가) (\\d{2}월\\d{2}일) (.*\\b) ([0-9,\\.]*)(원) (사용)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardAutoPaymentSms = new CreditCardAutoPaymentSms();

			creditCardAutoPaymentSms.setSenderName(m.group(2));
			creditCardAutoPaymentSms.setCardCompanyName(m.group(1));
			creditCardAutoPaymentSms.setCardLastFourNumber(null);
			creditCardAutoPaymentSms.setPayedWhenDate(m.group(4));
			creditCardAutoPaymentSms.setPayedWhere(m.group(5));
			creditCardAutoPaymentSms.setPayedMoney(m.group(6));

			result.add(creditCardAutoPaymentSms);
		}

		return result;
	}
}
