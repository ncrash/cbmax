package excel;

public class receiptMessageResolver {

	public static String convertOriginalSms(String mmsContent) {
		// TODO mms문자를 sms문자 건별로 분리 해야함
		// 처리는 mmsContent의 내용을 file을 읽듯이 한줄 한줄 읽어들어여서
		// 한줄의 길이가 특정 길이보다 짧은 경우 한줄로 합치기

		StringBuffer result = new StringBuffer();
		String smsContentPerLine = new String();

		String[] split = mmsContent.split("\n");
		for (int i = 0; i < split.length; i++) {

			if (split[i].getBytes().length > 50) {
				result.append(split[i] + "\n");
				smsContentPerLine = new String();
			} else {
				if (smsContentPerLine.length() > 0 && split[i].indexOf("[") > -1) {
					result.append(smsContentPerLine + "\n");
					smsContentPerLine = new String();
				} else {
					if (smsContentPerLine.length() > 0) {
						smsContentPerLine += " ";
					}
				}
				smsContentPerLine += split[i];
			}
		}

		if (smsContentPerLine.length() > 0) {
			result.append(smsContentPerLine + "\n");
		}

		return result.toString();
	}
}
