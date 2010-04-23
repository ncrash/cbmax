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
	 	receiptSmsList.add("��Ƽī�� ����Ǵ� ���γ��� 03��13�� 19:47 �̸�Ʈ������ 40,410�� �Ͻú�");

	 	sb = new StringBuffer();
	 	sb.append("[�Ͻú�.����]");
	 	sb.append("\n").append("186,000��");
	 	sb.append("\n").append("�츮BC(3*8*)����Ǵ�");
	 	sb.append("\n").append("04/07 23:17");
	 	sb.append("\n").append("��������");
	 	
	 	receiptSmsList.add(sb.toString());
	 	
	 	sb = new StringBuffer();
	 	sb.append("[�Ͻú�.����]");
	 	sb.append("\n").append("348,600��");
	 	sb.append("\n").append("����BC(1*0*)����Ǵ�");
	 	sb.append("\n").append("01/24 16:16");
	 	sb.append("\n").append("(��)��ũ�뿡����Ʈ��");
	 	
	 	receiptSmsList.add(sb.toString());

	 	receiptSmsList.add("[KBī��]����Ǵ� ī�尡 04��12�� SK�ڷ���-�ڵ����ο��� 13,080�� ���");

	 	sb = new StringBuffer();
	 	sb.append("[KBüũ]");
	 	sb.append("\n").append("����Ǵ�");
	 	sb.append("\n").append("04��05��12:47");
	 	sb.append("\n").append("����Ұ��");
	 	sb.append("\n").append("52,000�� ���");
	 	
	 	receiptSmsList.add(sb.toString());
	 	
	 	receiptSmsList.add("��Ƽī�� ����Ǵ� ���γ��� 03��13�� 19:47 �̸�Ʈ������ 40,410�� �Ͻú�");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	// TODO ��Ƽī��� �Һΰ��� �� ������� ���� ���� �׽�Ʈ �ؾ�
	public void testCityCard() throws Exception {
		for (int i = 0; i < receiptSmsList.size(); i++) {
			String receiptSms = receiptSmsList.get(i);
			
			Pattern p = Pattern.compile("(��Ƽī��) (.*��) (����|���)(����) (.*��.*��) ([\\d]*:[\\d]*) (.*) ([0-9,]*)(��) (�Ͻú�|.*$)");
			Matcher m = p.matcher(receiptSms);
			
			while(m.find()) {
				assertTrue(receiptSms.contains("��Ƽī��"));
			}
		}
	}
	
	@Test
	public void testBcCard() throws Exception {
		for (int i = 0; i < receiptSmsList.size(); i++) {
			String receiptSms = receiptSmsList.get(i);
			
			Pattern p = Pattern.compile("\\[(�Ͻú�.����|[\\d]*����.����|�������)\\]\\n([0-9,]*)(��)\\n(.*BC)(\\(\\d\\*\\d\\*\\))(.*��)\\n(\\d*\\/\\d*) (\\d*:\\d*)\\n(.*\\b)");
			Matcher m = p.matcher(receiptSms);
			
			while(m.find()) {
				assertTrue(receiptSms.contains("BC"));
			}
		}
	}
}
