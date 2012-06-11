package net.ncrash.cbmax.core.creditcard;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.ncrash.cbmax.core.dto.CreditCardPaymentSms;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

/**
 * 신용카드 사용내역 parser code fixture를 통한 test
 * 
 * @author Daekwon.Kang
 * @since 2010.04.23
 * @see
 */
public class CreditCardSmsPaymentParserCodeFixtureTest {
	List<String> PaymentSmsList = new ArrayList<String>();
	StringBuffer sb;
	int matchCount;

	CreditCardCompany creditCardCompany;
	CreditCardPaymentSms creditCardPaymentSms;
	
	ObjectMapper mapper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mapper = new ObjectMapper();
		creditCardCompany = new CreditCardCompany();
		matchCount = 0;

		File paymentSmsFile = new File(getClass().getClassLoader().getResource("payment-sms.json").getFile());

		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {};
		PaymentSmsList = mapper.readValue(paymentSmsFile, typeRef);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	// TODO 씨티카드는 할부개월 및 승인취소 내역 실제 테스트 해야하고 체크카드 작업이 필요
	public void testCityCard() throws Exception {

		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("CITY"));

		matchCount += getSize(creditCardCompany.getParser());

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		File jsonFile = new File(getClass().getClassLoader().getResource("testCityCard.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Collection<CreditCardPaymentSms> jsonCreditCardPaymentSmsList = null;
		jsonCreditCardPaymentSmsList = mapper.readValue(jsonFile, new TypeReference<Collection<CreditCardPaymentSms>>() { });
		
		assertEquals(jsonCreditCardPaymentSmsList.size(), matchCount);
		ReflectionAssert.assertReflectionEquals(parsedCreditCardPaymentSmsList, jsonCreditCardPaymentSmsList);
	}

	@Test
	// TODO 비씨카드 체크카드 발급 필요
	public void testBcCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("BC"));

		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(6, matchCount);

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		assertEquals("우리BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/07", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("23:17", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("양은냄비", creditCardPaymentSms.getPayedWhere());
		assertEquals("186,000", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("(3*8*)", creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(1);
		assertEquals("농협BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("01/24", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("16:16", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)테크노에어포트몰", creditCardPaymentSms.getPayedWhere());
		assertEquals("348,600", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("(1*0*)", creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(2);
		assertEquals("우리BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/24", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("19:28", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("22,500", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("취소",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("(0*5*)", creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(3);
		assertEquals("우리BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/23", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("23:33", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("150,100", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("10",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("(3*8*)", creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(4);
		assertEquals("우리BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/23", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("14:52", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("피자헛", creditCardPaymentSms.getPayedWhere());
		assertEquals("21,500", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("(3*8*)", creditCardPaymentSms.getCardLastFourNumber());
		assertEquals("\n(POINT580점 사용)",
				creditCardPaymentSms.getUnknownMessage());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(5);
		assertEquals("우리BC", creditCardPaymentSms.getCardCompanyName());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/01", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:23", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("G마켓", creditCardPaymentSms.getPayedWhere());
		assertEquals("13,900", creditCardPaymentSms.getPayedMoney());
		assertEquals("일시불", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
	}

	@Test
	// TODO KB카드 승인취소, 할부승인과 신용체크의 취소 필요
	public void testKbCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("KB"));

		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(4, matchCount);

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		assertEquals("KB", creditCardPaymentSms.getCardCompanyName());
		assertEquals("체크", creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04월05일", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("12:47", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("광양불고기", creditCardPaymentSms.getPayedWhere());
		assertEquals("52,000", creditCardPaymentSms.getPayedMoney());
		assertEquals(null, creditCardPaymentSms.getPayedApproveType());
		assertEquals(" 사용",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(1);
		assertEquals("KB", creditCardPaymentSms.getCardCompanyName());
		assertEquals("카드", creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("03월27일", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("17:13", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("이마트구로점", creditCardPaymentSms.getPayedWhere());
		assertEquals("40,480", creditCardPaymentSms.getPayedMoney());
		assertEquals(null, creditCardPaymentSms.getPayedApproveType());
		assertEquals(" 사용",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(2);
		assertEquals("KB", creditCardPaymentSms.getCardCompanyName());
		assertEquals("카드", creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05월09일", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:45", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("53,200", creditCardPaymentSms.getPayedMoney());
		assertEquals(null, creditCardPaymentSms.getPayedApproveType());
		assertEquals("\n할부:12개월",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
		assertEquals("12", creditCardPaymentSms.getPayedInstallmentMonths());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(3);
		assertEquals("KB", creditCardPaymentSms.getCardCompanyName());
		assertEquals("카드", creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05월09일", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:46", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("53,200", creditCardPaymentSms.getPayedMoney());
		assertEquals(null, creditCardPaymentSms.getPayedApproveType());
		assertEquals("\n승인취소",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
		assertEquals(null, creditCardPaymentSms.getPayedInstallmentMonths());
	}

	@Test
	// TODO 신한카드는 체크카드 승인, 체크카드 승인취소 처리 필요
	public void testShinhanCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("SHINHAN"));

		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(4, matchCount);

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		assertEquals("신한카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/13", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("13:51", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("（주）인터파크", creditCardPaymentSms.getPayedWhere());
		assertEquals("200,570", creditCardPaymentSms.getPayedMoney());
		assertEquals("정상승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(1);
		assertEquals("신한카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/13", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("13:57", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("（주）인터파크", creditCardPaymentSms.getPayedWhere());
		assertEquals("200,570", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인취소", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(2);
		assertEquals("신한카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/09", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:50", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("（주）인터파크", creditCardPaymentSms.getPayedWhere());
		assertEquals("65,550", creditCardPaymentSms.getPayedMoney());
		assertEquals("정상승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("11개월",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
		assertEquals("11", creditCardPaymentSms.getPayedInstallmentMonths());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(3);
		assertEquals("신한카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/09", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:50", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("（주）인터파크", creditCardPaymentSms.getPayedWhere());
		assertEquals("65,550", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인취소", creditCardPaymentSms.getPayedApproveType());
		assertEquals("11개월",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
		assertEquals("11", creditCardPaymentSms.getPayedInstallmentMonths());
	}

	@Test
	// TODO 외환카드는 할부승인, 체크카드 승인, 체크카드 승인취소 처리 필요
	public void testKebCard() throws Exception {
		creditCardCompany
				.setParser(CreditCardSmsParserFactory.getParser("KEB"));

		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(3, matchCount);

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		assertEquals("외환카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/08", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("01:22", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("택시(서울)한국스", creditCardPaymentSms.getPayedWhere());
		assertEquals("15,720", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals(null,
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(1);
		assertEquals("외환카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/09", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:53", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("인터파크(쇼핑몰)", creditCardPaymentSms.getPayedWhere());
		assertEquals("65,550", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("할부 11개월",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
		assertEquals("11", creditCardPaymentSms.getPayedInstallmentMonths());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(2);
		assertEquals("외환카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/09", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:54", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("인터파크(쇼핑몰)", creditCardPaymentSms.getPayedWhere());
		assertEquals("65,550", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인취소", creditCardPaymentSms.getPayedApproveType());
		assertEquals(null,
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
		assertEquals(null, creditCardPaymentSms.getPayedInstallmentMonths());
	}

	@Test
	// TODO 현대카드는 신용승인, 할부승인, 신용 승인취소, 체크카드 승인취소 처리 필요
	public void testHyundaiCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("HYUNDAI"));

		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(2, matchCount);

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		// sb.append("[현대카드C]");
		// sb.append("\n").append("강대권님");
		// sb.append("\n").append("12:10");
		// sb.append("\n").append("2,400원(일시불)");
		// sb.append("\n").append("정상승인");
		// sb.append("\n").append("온누리조은약국");

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		assertEquals("현대카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals("C", creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals(null, creditCardPaymentSms.getPayedWhenDate());
		assertEquals("12:10", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("온누리조은약국", creditCardPaymentSms.getPayedWhere());
		assertEquals("2,400", creditCardPaymentSms.getPayedMoney());
		assertEquals("정상승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		// sb.append("[현대카드C]-승인");
		// sb.append("\n").append("강대권님");
		// sb.append("\n").append("18:22");
		// sb.append("\n").append("3,780원(일시불)");
		// sb.append("\n").append("（주）현대홈쇼핑ＫＭＰＩ（일");

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(1);
		assertEquals("현대카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals("C", creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals(null, creditCardPaymentSms.getPayedWhenDate());
		assertEquals("18:22", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("（주）현대홈쇼핑ＫＭＰＩ（일", creditCardPaymentSms.getPayedWhere());
		assertEquals("3,780", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
	}

	@Test
	// TODO 롯데카드는 할부승인, 신용 승인취소, 체크카드 승인, 승인취소 처리 필요
	public void testLotteCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("LOTTE"));

		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(4, matchCount);

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		assertEquals("롯데카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/17", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("10:16", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("이베이옥션", creditCardPaymentSms.getPayedWhere());
		assertEquals("939,900", creditCardPaymentSms.getPayedMoney());
		assertEquals("", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(1);
		assertEquals("롯데카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("04/21", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("23:55", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)이니시스", creditCardPaymentSms.getPayedWhere());
		assertEquals("33,800", creditCardPaymentSms.getPayedMoney());
		assertEquals("", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		// 롯데카드 강대권님 65,550원 할부(11) 05/09 23:02 (주)인터파크
		// 롯데카드 강대권님 65,550원 할부(11)취소 05/09 23:02 (주)인터파크

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(2);
		assertEquals("롯데카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/09", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("23:02", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크", creditCardPaymentSms.getPayedWhere());
		assertEquals("65,550", creditCardPaymentSms.getPayedMoney());
		assertEquals("", creditCardPaymentSms.getPayedApproveType());
		assertEquals("11", creditCardPaymentSms.getPayedInstallmentMonths());
		assertEquals("할부(11)",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(3);
		assertEquals("롯데카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/09", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("23:02", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크", creditCardPaymentSms.getPayedWhere());
		assertEquals("65,550", creditCardPaymentSms.getPayedMoney());
		assertEquals("취소", creditCardPaymentSms.getPayedApproveType());
		assertEquals("11", creditCardPaymentSms.getPayedInstallmentMonths());
		assertEquals("할부(11)",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
	}

	@Test
	// TODO 삼성카드는 할부승인, 신용 승인취소, 체크카드 승인, 승인취소 처리 필요
	public void testSamsungCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("SAMSUNG"));

		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(4, matchCount);

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		assertEquals("삼성카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals(null, creditCardPaymentSms.getSenderName());
		assertEquals("04/30", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("09:58", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("63,650", creditCardPaymentSms.getPayedMoney());
		assertEquals("", creditCardPaymentSms.getPayedApproveType());
		assertEquals(null,
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(1);
		assertEquals("삼성카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals(null, creditCardPaymentSms.getSenderName());
		assertEquals("04/30", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("09:59", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("-63,650", creditCardPaymentSms.getPayedMoney());
		assertEquals("취소", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(2);
		assertEquals("삼성카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals(null, creditCardPaymentSms.getSenderName());
		assertEquals("04/30", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("10:00", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("110,200", creditCardPaymentSms.getPayedMoney());
		assertEquals("", creditCardPaymentSms.getPayedApproveType());
		assertEquals("3",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(3);
		assertEquals("삼성카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals(null, creditCardPaymentSms.getSenderName());
		assertEquals("04/30", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("10:01", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("-110,200", creditCardPaymentSms.getPayedMoney());
		assertEquals("취소", creditCardPaymentSms.getPayedApproveType());
		assertEquals("할부",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
	}

	@Test
	// TODO 하나SK카드는 체크카드 승인, 승인취소 처리 필요
	public void testHanaSkCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("HANA_SK"));

		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(4, matchCount);

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(0);
		assertEquals("하나SK카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/09", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:18", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("56,050", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getPayedInstallmentMonths());
		assertEquals("(1*8*)", creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(1);
		assertEquals("하나SK카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/09", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:19", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("56,050", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인취소", creditCardPaymentSms.getPayedApproveType());
		assertEquals("일시불",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getPayedInstallmentMonths());
		assertEquals("(1*8*)", creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(2);
		assertEquals("하나SK카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("05/09", creditCardPaymentSms.getPayedWhenDate());
		assertEquals("22:21", creditCardPaymentSms.getPayedWhenTime());
		assertEquals("(주)인터파크INT", creditCardPaymentSms.getPayedWhere());
		assertEquals("149,150", creditCardPaymentSms.getPayedMoney());
		assertEquals("승인", creditCardPaymentSms.getPayedApproveType());
		assertEquals("할부",
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals("11", creditCardPaymentSms.getPayedInstallmentMonths());
		assertEquals("(1*8*)", creditCardPaymentSms.getCardLastFourNumber());

		creditCardPaymentSms = parsedCreditCardPaymentSmsList.get(3);
		assertEquals("하나카드", creditCardPaymentSms.getCardCompanyName());
		assertEquals(null, creditCardPaymentSms.getPayedCardType());
		assertEquals("강대권님", creditCardPaymentSms.getSenderName());
		assertEquals("2010년05월11일", creditCardPaymentSms.getPayedWhenDate());
		assertEquals(null, creditCardPaymentSms.getPayedWhenTime());
		assertEquals("철도승차권발매", creditCardPaymentSms.getPayedWhere());
		assertEquals("-45,700", creditCardPaymentSms.getPayedMoney());
		assertEquals(null, creditCardPaymentSms.getPayedApproveType());
		assertEquals(null,
				creditCardPaymentSms.getPayedLumpSumOrInstallmentPlan());
		assertEquals(null, creditCardPaymentSms.getPayedInstallmentMonths());
		assertEquals(null, creditCardPaymentSms.getCardLastFourNumber());
	}

	public int getSize(CreditCardSmsParser creditCardSmsParser) {
		for (int i = 0; i < PaymentSmsList.size(); i++) {
			String PaymentSms = PaymentSmsList.get(i);

			matchCount += creditCardSmsParser.paymentSmsParse(PaymentSms)
					.size();
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
