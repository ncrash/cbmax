package net.ncrash.cbmax.core.creditcard;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreditCardReceiptSmsParsingTests {

	List<String> receiptSmsList = new ArrayList<String>();
	StringBuffer sb;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	 	receiptSmsList.add("씨티카드 강대권님 승인내역 03월13일 19:47 이마트구로점 40,410원 일시불");

	 	sb = new StringBuffer();
	 	sb.append("[일시불.승인]");
	 	sb.append("\n").append("186,000원");
	 	sb.append("\n").append("우리BC(3*8*)강대권님");
	 	sb.append("\n").append("04/07 23:17");
	 	sb.append("\n").append("양은냄비");
	 	
	 	receiptSmsList.add(sb.toString());
	 	
	 	sb = new StringBuffer();
	 	sb.append("[일시불.승인]");
	 	sb.append("\n").append("348,600원");
	 	sb.append("\n").append("농협BC(1*0*)강대권님");
	 	sb.append("\n").append("01/24 16:16");
	 	sb.append("\n").append("(주)테크노에어포트몰");
	 	
	 	receiptSmsList.add(sb.toString());

	 	receiptSmsList.add("[KB카드]강대권님 카드가 04월12일 SK텔레콤-자동납부에서 13,080원 사용");

	 	sb = new StringBuffer();
	 	sb.append("[KB체크]");
	 	sb.append("\n").append("강대권님");
	 	sb.append("\n").append("04월05일12:47");
	 	sb.append("\n").append("광양불고기");
	 	sb.append("\n").append("52,000원 사용");
	 	
	 	receiptSmsList.add(sb.toString());
	 	
	 	receiptSmsList.add("씨티카드 강대권님 승인내역 03월13일 19:47 이마트구로점 40,410원 일시불");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	// TODO 씨티카드는 할부개월 및 승인취소 내역 실제 테스트 해야
	public void testCityCard() throws Exception {
		for (int i = 0; i < receiptSmsList.size(); i++) {
			String receiptSms = receiptSmsList.get(i);
			
			Pattern p = Pattern.compile("(씨티카드) (.*님) (승인|취소)(내역) (.*월.*일) ([\\d]*:[\\d]*) (.*) ([0-9,]*)(원) (일시불|.*$)");
			Matcher m = p.matcher(receiptSms);
			
			while(m.find()) {
				assertTrue(receiptSms.contains("씨티카드"));
			}
		}
	}
	
	@Test
	public void testBcCard() throws Exception {
		for (int i = 0; i < receiptSmsList.size(); i++) {
			String receiptSms = receiptSmsList.get(i);
			
			Pattern p = Pattern.compile("\\[(일시불.승인|[\\d]*개월.승인|승인취소)\\]\\n([0-9,]*)(원)\\n(.*BC)(\\(\\d\\*\\d\\*\\))(.*님)\\n(\\d*\\/\\d*) (\\d*:\\d*)\\n(.*\\b)");
			Matcher m = p.matcher(receiptSms);
			
			while(m.find()) {
				assertTrue(receiptSms.contains("BC"));
			}
		}
	}
}
