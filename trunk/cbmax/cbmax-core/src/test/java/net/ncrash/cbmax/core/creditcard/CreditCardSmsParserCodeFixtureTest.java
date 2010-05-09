package net.ncrash.cbmax.core.creditcard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.ncrash.cbmax.core.dto.CreditCardPaymentSms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 신용카드 사용내역 parser code fixture를 통한 test
 * 
 * @author Daekwon.Kang
 * @since 2010.04.23 
 * @see 
 */
public class CreditCardSmsParserCodeFixtureTest {
	List<String> PaymentSmsList = new ArrayList<String>();
	StringBuffer sb;
	int matchCount;
	
	CreditCardCompany creditCardCompany;
	CreditCardPaymentSms creditCardPaymentSms;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		creditCardCompany = new CreditCardCompany();
		
		matchCount = 0;

		PaymentSmsList.add("씨티카드 강대권님 승인내역 03월13일 19:47 이마트구로점 40,410원 일시불");

		sb = new StringBuffer();
		sb.append("[일시불.승인]");
		sb.append("\n").append("186,000원");
		sb.append("\n").append("우리BC(3*8*)강대권님");
		sb.append("\n").append("04/07 23:17");
		sb.append("\n").append("양은냄비");
		PaymentSmsList.add(sb.toString());

		sb = new StringBuffer();
		sb.append("[일시불.승인]");
		sb.append("\n").append("348,600원");
		sb.append("\n").append("농협BC(1*0*)강대권님");
		sb.append("\n").append("01/24 16:16");
		sb.append("\n").append("(주)테크노에어포트몰");
		PaymentSmsList.add(sb.toString());

		sb = new StringBuffer();
		sb.append("[승인취소]");
		sb.append("\n").append("22,500원");
		sb.append("\n").append("우리BC(0*5*)강대권님");
		sb.append("\n").append("04/24 19:28");
		sb.append("\n").append("(주)인터파크INT");
		PaymentSmsList.add(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n").append("[10개월.승인]");
		sb.append("\n").append("150,100원");
		sb.append("\n").append("우리BC(3*8*)강대권님");
		sb.append("\n").append("04/23 23:33");
		sb.append("\n").append("(주)인터파크INT");
		PaymentSmsList.add(sb.toString());
		
		PaymentSmsList.add("[KB카드]강대권님 카드가 04월12일 SK텔레콤-자동납부에서 13,080원 사용");

		sb = new StringBuffer();
		sb.append("[KB체크]");
		sb.append("\n").append("강대권님");
		sb.append("\n").append("04월05일12:47");
		sb.append("\n").append("광양불고기");
		sb.append("\n").append("52,000원 사용");
		PaymentSmsList.add(sb.toString());

		sb = new StringBuffer();
		sb.append("[KB카드]");
		sb.append("\n").append("강대권님");
		sb.append("\n").append("03월27일17:13");
		sb.append("\n").append("이마트구로점");
		sb.append("\n").append("40,480원 사용");
		PaymentSmsList.add(sb.toString());

		PaymentSmsList.add("신한카드정상승인강대권님        04/13 13:51     200,570원(일시불) （주）인터파크");
		PaymentSmsList.add("신한카드승인취소강대권님        04/13 13:57     200,570원(일시불) （주）인터파크");

		PaymentSmsList.add("[외환카드]강대권님     15,720원 승인 택시(서울)한국스 04/08 01:22");

		sb = new StringBuffer();
		sb.append("[현대카드C]");
		sb.append("\n").append("강대권님");
		sb.append("\n").append("12:10");
		sb.append("\n").append("2,400원(일시불)");
		sb.append("\n").append("정상승인");
		sb.append("\n").append("온누리조은약국");
		PaymentSmsList.add(sb.toString());

		PaymentSmsList.add("롯데카드 강대권님 939,900원 일시불 04/17 10:16 이베이옥션                       ");
		PaymentSmsList.add("롯데카드 강대권님 33,800원 일시불 04/21 23:55 (주)이니시스                      ");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	// TODO 씨티카드는 할부개월 및 승인취소 내역 실제 테스트 해야하고 체크카드 작업이 필요
	public void testCityCard() throws Exception {

		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("CITY"));

		matchCount += getSize(creditCardCompany.getParser());
		
		assertEquals(1, matchCount);
		
		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany.getParser().paymentSmsParse(this.convertPaymentSmsListToString());
		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		
		assertEquals("씨티카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("03월13일", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("19:47", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("이마트구로점", creditCardPaymentSms.getPayedWhere());
		assertEquals("40,410", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불", creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
	}
	
	@Test
	// TODO 비씨카드 체크카드 발급 필요
	public void testBcCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("BC"));
		
		matchCount += getSize(creditCardCompany.getParser());
		
		assertEquals(4, matchCount);
		
		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany.getParser().paymentSmsParse(this.convertPaymentSmsListToString());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		assertEquals("우리BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/07", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("23:17", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("양은냄비", creditCardPaymentSms.getPayedWhere());
		assertEquals("186,000", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불", creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("(3*8*)", creditCardPaymentSms.getCardLastFourNumber());
		
		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(1);
		assertEquals("농협BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("01/24", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("16:16", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)테크노에어포트몰", creditCardPaymentSms.getPayedWhere());
		assertEquals("348,600", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불", creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("(1*0*)", creditCardPaymentSms.getCardLastFourNumber());
		
		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(2);
		assertEquals("우리BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/24", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("19:28", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("22,500", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("취소", creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("(0*5*)", creditCardPaymentSms.getCardLastFourNumber());
		
		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(3);
		assertEquals("우리BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/23", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("23:33", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("150,100", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("10", creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("(3*8*)", creditCardPaymentSms.getCardLastFourNumber());
		
	}
	
	@Test
	// TODO KB카드는 할부승인과 신용체크의 취소 필요
	public void testKbCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("KB"));
		
		matchCount += getSize(creditCardCompany.getParser());
		
		assertEquals(2, matchCount);
	}

	@Test
	// TODO 신한카드는 할부승인, 체크카드 승인, 체크카드 승인취소 처리 필요
	public void testShinhanCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("SHINHAN"));
		
		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(2, matchCount);
	}

	@Test
	// TODO 외환카드는 할부승인, 체크카드 승인, 체크카드 승인취소 처리 필요
	public void testYesCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("KEB"));
		
		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(1, matchCount);
	}

	@Test
	// TODO 현대카드는 신용승인, 할부승인, 신용 승인취소, 체크카드 승인취소 처리 필요
	public void testHyundaiCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("HYUNDAI"));
		
		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(1, matchCount);
	}

	@Test
	// TODO 롯데카드는 할부승인, 신용 승인취소, 체크카드 승인, 승인취소 처리 필요
	public void testLotteCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("LOTTE"));
		
		matchCount += getSize(creditCardCompany.getParser());
		
		assertEquals(2, matchCount);
	}

	public int getSize(CreditCardSmsParser creditCardSmsParser) {
		for (int i = 0; i < PaymentSmsList.size(); i++) {
			String PaymentSms = PaymentSmsList.get(i);
			
			matchCount += creditCardSmsParser.paymentSmsParse(PaymentSms).size();
		}
		
		return matchCount;
	}
	
	public String convertPaymentSmsListToString() {
		StringBuffer mmsContent = new StringBuffer();
		
		for (int i = 0; i < PaymentSmsList.size(); i++) {
			String paymentSms = PaymentSmsList.get(i);
			
			mmsContent.append(paymentSms).append("\n");
		}
		return mmsContent.toString();
	}
}
