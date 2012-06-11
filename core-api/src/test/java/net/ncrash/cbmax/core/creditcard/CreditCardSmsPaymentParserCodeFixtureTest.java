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

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		File jsonFile = new File(getClass().getClassLoader().getResource("testBcCard.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Collection<CreditCardPaymentSms> jsonCreditCardPaymentSmsList = null;
		jsonCreditCardPaymentSmsList = mapper.readValue(jsonFile, new TypeReference<Collection<CreditCardPaymentSms>>() { });
		
		assertEquals(jsonCreditCardPaymentSmsList.size(), matchCount);
		ReflectionAssert.assertReflectionEquals(parsedCreditCardPaymentSmsList, jsonCreditCardPaymentSmsList);
	}

	@Test
	// TODO KB카드 승인취소, 할부승인과 신용체크의 취소 필요
	public void testKbCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("KB"));

		matchCount += getSize(creditCardCompany.getParser());

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		File jsonFile = new File(getClass().getClassLoader().getResource("testKbCard.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Collection<CreditCardPaymentSms> jsonCreditCardPaymentSmsList = null;
		jsonCreditCardPaymentSmsList = mapper.readValue(jsonFile, new TypeReference<Collection<CreditCardPaymentSms>>() { });
		
		assertEquals(jsonCreditCardPaymentSmsList.size(), matchCount);
		ReflectionAssert.assertReflectionEquals(parsedCreditCardPaymentSmsList, jsonCreditCardPaymentSmsList);
	}

	@Test
	// TODO 신한카드는 체크카드 승인, 체크카드 승인취소 처리 필요
	public void testShinhanCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("SHINHAN"));

		matchCount += getSize(creditCardCompany.getParser());

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		File jsonFile = new File(getClass().getClassLoader().getResource("testShinhanCard.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Collection<CreditCardPaymentSms> jsonCreditCardPaymentSmsList = null;
		jsonCreditCardPaymentSmsList = mapper.readValue(jsonFile, new TypeReference<Collection<CreditCardPaymentSms>>() { });
		
		assertEquals(jsonCreditCardPaymentSmsList.size(), matchCount);
		ReflectionAssert.assertReflectionEquals(parsedCreditCardPaymentSmsList, jsonCreditCardPaymentSmsList);
	}

	@Test
	// TODO 외환카드는 할부승인, 체크카드 승인, 체크카드 승인취소 처리 필요
	public void testKebCard() throws Exception {
		creditCardCompany
				.setParser(CreditCardSmsParserFactory.getParser("KEB"));

		matchCount += getSize(creditCardCompany.getParser());

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		File jsonFile = new File(getClass().getClassLoader().getResource("testKebCard.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Collection<CreditCardPaymentSms> jsonCreditCardPaymentSmsList = null;
		jsonCreditCardPaymentSmsList = mapper.readValue(jsonFile, new TypeReference<Collection<CreditCardPaymentSms>>() { });
		
		assertEquals(jsonCreditCardPaymentSmsList.size(), matchCount);
		ReflectionAssert.assertReflectionEquals(parsedCreditCardPaymentSmsList, jsonCreditCardPaymentSmsList);
	}

	@Test
	// TODO 현대카드는 신용승인, 할부승인, 신용 승인취소, 체크카드 승인취소 처리 필요
	public void testHyundaiCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("HYUNDAI"));

		matchCount += getSize(creditCardCompany.getParser());

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		File jsonFile = new File(getClass().getClassLoader().getResource("testHyundaiCard.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Collection<CreditCardPaymentSms> jsonCreditCardPaymentSmsList = null;
		jsonCreditCardPaymentSmsList = mapper.readValue(jsonFile, new TypeReference<Collection<CreditCardPaymentSms>>() { });
		
		assertEquals(jsonCreditCardPaymentSmsList.size(), matchCount);
		ReflectionAssert.assertReflectionEquals(parsedCreditCardPaymentSmsList, jsonCreditCardPaymentSmsList);
	}

	@Test
	// TODO 롯데카드는 할부승인, 신용 승인취소, 체크카드 승인, 승인취소 처리 필요
	public void testLotteCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("LOTTE"));

		matchCount += getSize(creditCardCompany.getParser());

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		File jsonFile = new File(getClass().getClassLoader().getResource("testLotteCard.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Collection<CreditCardPaymentSms> jsonCreditCardPaymentSmsList = null;
		jsonCreditCardPaymentSmsList = mapper.readValue(jsonFile, new TypeReference<Collection<CreditCardPaymentSms>>() { });
		
		assertEquals(jsonCreditCardPaymentSmsList.size(), matchCount);
		ReflectionAssert.assertReflectionEquals(parsedCreditCardPaymentSmsList, jsonCreditCardPaymentSmsList);
	}

	@Test
	// TODO 삼성카드는 할부승인, 신용 승인취소, 체크카드 승인, 승인취소 처리 필요
	public void testSamsungCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("SAMSUNG"));

		matchCount += getSize(creditCardCompany.getParser());

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		File jsonFile = new File(getClass().getClassLoader().getResource("testSamsungCard.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Collection<CreditCardPaymentSms> jsonCreditCardPaymentSmsList = null;
		jsonCreditCardPaymentSmsList = mapper.readValue(jsonFile, new TypeReference<Collection<CreditCardPaymentSms>>() { });
		
		assertEquals(jsonCreditCardPaymentSmsList.size(), matchCount);
		ReflectionAssert.assertReflectionEquals(parsedCreditCardPaymentSmsList, jsonCreditCardPaymentSmsList);
	}

	@Test
	// TODO 하나SK카드는 체크카드 승인, 승인취소 처리 필요
	public void testHanaSkCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory
				.getParser("HANA_SK"));

		matchCount += getSize(creditCardCompany.getParser());

		List<CreditCardPaymentSms> parsedCreditCardPaymentSmsList = creditCardCompany
				.getParser().paymentSmsParse(
						this.convertPaymentSmsListToString());

		File jsonFile = new File(getClass().getClassLoader().getResource("testHanaSkCard.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Collection<CreditCardPaymentSms> jsonCreditCardPaymentSmsList = null;
		jsonCreditCardPaymentSmsList = mapper.readValue(jsonFile, new TypeReference<Collection<CreditCardPaymentSms>>() { });
		
		assertEquals(jsonCreditCardPaymentSmsList.size(), matchCount);
		ReflectionAssert.assertReflectionEquals(parsedCreditCardPaymentSmsList, jsonCreditCardPaymentSmsList);
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
