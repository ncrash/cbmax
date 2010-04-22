package net.ncrash.cbmax.core;

/** 
 * mms messages convert to original sms messages
 *
 * @author Daekwon Kang
 * @since 2010. 4. 7.
 * @see
 */
public class receiptMessageResolver {

	public static String convertOriginalSms(String mmsContent) {
		// TODO mms���ڸ� sms���� �Ǻ��� �и� �ؾ���
		// ó���� mmsContent�� ������ file�� �е��� ���� ���� �о����
		// ������ ���̰� Ư�� ���̺��� ª�� ��� ���ٷ� ��ġ��

		StringBuffer result = new StringBuffer();
		String smsContentPerLine = new String();

		String[] split = mmsContent.split("\n");
		for (int i = 0; i < split.length; i++) {
			if (split[i].length() == 0) {
				continue;
			}

			if (split[i].getBytes().length > 50) {
				if (smsContentPerLine.length() > 0) {
					result.append(smsContentPerLine + "\n");
					smsContentPerLine = new String();
				}

				result.append(split[i] + "\n");
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
