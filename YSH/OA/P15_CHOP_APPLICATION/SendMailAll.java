package YSH.OA.P15_CHOP_APPLICATION;

//YSH/OA/P15_CHOP_APPLICATION/SendMailAll;
import java.util.Arrays;
import java.util.HashSet;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import jcx.util.convert;

import com.ysp.service.BaseService;
import com.ysp.service.MailService;


public class SendMailAll extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ���ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ���ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"
		talk t = getTalk();
		BaseService service = new BaseService();
		MailService mail = new MailService(service);

		String PNO = getValue("PNO").trim();

		String EMPID;
		String name;
		String title;
		String content = "";
		String smtp = (String) get("SYSTEM.POP3");
		if (smtp == null)
			smtp = "www.interinfo.com.tw";
		String sender = (String) get("SYSTEM.SEMAIL");
		if (sender == null)
			sender = "admin@interinfo.com.tw";
		String email = "";
		String sendRS = "";
		// getAllApprovePeople is a local method.
		String[] AllApprovePeople = getAllApprovePeople();
		int isEmailAllSend = 0;

		for (String peopleString : AllApprovePeople) {
			// System.out.println("value=" + it.next().toString());
			content = "";
			EMPID = getValue("EMPID").trim();
			name = getName(EMPID);
			title = "(" + EMPID + ")" + name + "���ΦL���غ[�֨M�v���ӽЪ�( " + PNO + " ) �w����";

			content += "�D��:" + title + "<br>";

			email = getEmail(peopleString);
			//email = service.getUserInfoBean(peopleString).getEmail();
			String usr[] = { email };

			sendRS = mail.sendMailbccUTF8(usr, title, content, null, "",
					"text/html");
			// if send mail Sending Failed,isEmailAllSend will +1 for check.
			if (!sendRS.trim().equals("")) {
				isEmailAllSend++;
			}

		}
		t.close();
		if (isEmailAllSend != 0) {
			message("EMAIL�H�X����");
			return false;

		}

		message("EMAIL�w�H�X�q��");

		return true;

	}

	public String getInformation() {
		return "---------------\u6838\u51c6.preProcess()----------------";
	}

	public String[] getAllApprovePeople() {
		String vid[][] = getFlowHistory();
		String ausr[] = new String[vid.length];
		for (int i = 0; i < vid.length; i++) {
			ausr[i] = vid[i][1].trim();
		}
		HashSet<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(ausr));
		String usr[] = (String[]) set.toArray(new String[0]);
		return usr;

	}
}