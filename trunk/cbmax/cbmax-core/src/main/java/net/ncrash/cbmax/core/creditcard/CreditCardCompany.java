package net.ncrash.cbmax.core.creditcard;

import java.util.ArrayList;
import java.util.List;

public class CreditCardCompany {

	// TODO Card Company별로 sms수신내역 parser, 자동납부내역 parser 등을 클래스로 관리
	
	public static List<String> getReceiptSmsRegexList() {
		List<String> result = new ArrayList<String>();
		
		result.add("(롯데카드) (.*님) ([0-9,\\.]*)(원) (일시불) (\\d{2}/\\d{2}) (\\d{2}:\\d{2}) (.*\\b)");
		result.add("\\[(현대카드)(\\w)\\]\\n(.*님)\\n(\\d{2}:\\d{2})\\n([0-9,\\.]*)(원)\\((일시불)\\)\\n(정상승인)\\n(.*\\b)");
		result.add("\\[(외환카드)\\](.*님)\\s*([0-9,]*)(원) (승인) (.*\\b) (\\d{2}/\\d{2}) (\\d{2}:\\d{2})");
		result.add("(신한카드)(정상승인|승인취소)(.*님)\\s*(\\d{2}/\\d{2}) (\\d{2}:\\d{2})\\s*([0-9,]*)(원)\\((일시불)\\)(.*\\b)");
		result.add("\\[(KB)(카드|체크)\\]\\n(.*님)\\n(\\d{2}월\\d{2}일)(\\d{2}:\\d{2})\\n(.*\\b)\\n([0-9,]*)(원) (사용)");
		result.add("\\[(일시불.승인|[\\d]*개월.승인|승인취소)\\]\\n([0-9,]*)(원)\\n(.*BC)(\\(\\d\\*\\d\\*\\))(.*님)\\n(\\d*\\/\\d*) (\\d*:\\d*)\\n(.*\\b)");
		result.add("(씨티카드) (.*님) (승인|취소)(내역) (.*월.*일) ([\\d]*:[\\d]*) (.*) ([0-9,]*)(원) (일시불|.*$)");
		
		return result;
	}
	
	public static List<String> getAutomaticWithdrawalSmsRegexList() {
		List<String> result = new ArrayList<String>();
		
		result.add("\\[(KB카드)\\](.*님 카드가) (\\d{2}월\\d{2}일) (.*\\b) ([0-9,\\.]*)(원) (사용)");
		
		return result;
	}
//	result.add("［(신한카드)］(.*님) (\\d{2}/\\d{2})(결제금액)\\((\\d{2}\\/\\d{2})(기준)\\) ([0-9,\\\\.]*)(원)\\((결제:)(.*\\b)\\)");
}
