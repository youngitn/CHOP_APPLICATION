package YSH.OA.P15_CHOP_APPLICATION;

//YSH/OA/P15_CHOP_APPLICATION/SendMailAll;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import jcx.util.convert;

import com.ysp.service.BaseService;
import com.ysp.service.MailService;


public class SendMailAll extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"
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
		//加入印章保管人EMAIL
		//掃三個印章編號欄位的保管人
		String CHOP1 = getValue("CHOP1_NO").trim();
		String CHOP2 = getValue("CHOP2_NO").trim();
		String CHOP3 = getValue("CHOP3_NO").trim();
		String AllCHOPNO = "";
		if (CHOP1.length() != 0){
			AllCHOPNO += CHOP1;
		}
		if (CHOP2.length() != 0){
			AllCHOPNO += ","+CHOP2;
		}
		if (CHOP3.length() != 0){
			AllCHOPNO += ","+CHOP3;
		}
		;
		String[] ALLCHOP_NO = AllCHOPNO.trim().split(",");
		
		Set<String> StrSet = new HashSet<String>();
		for (String n : ALLCHOP_NO) {
			StrSet.add(n);
		    System.out.print("=> "+ n);
		}
		String showChopNoInMail = "";
		for (String no : StrSet) {
			//AllApprovePeople.addElement(t.queryFromPool("SELECT EMPID FROM CHOP_INFO WHERE CPNYID ='"+getValue("CHOP_COMPANY").trim()+"' AND CHOP_NO = '"+string+"'")[0][0]);
			AllApprovePeople = addElement(AllApprovePeople,t.queryFromPool("SELECT EMPID FROM CHOP_INFO WHERE CPNYID ='"+getValue("CHOP_COMPANY").trim()+"' AND CHOP_NO = '"+no+"'")[0][0]);
			//AllApprovePeople = addElement(AllApprovePeople,getValue("EMPID").trim());
			showChopNoInMail+= no+" ";
		}		
		
		int isEmailAllSend = 0;
		Set<String> AllApprovePeopleSet = new HashSet<String>();
		for (String k : AllApprovePeople) {
			AllApprovePeopleSet.add(k);
		    System.out.print("=> "+ k);
		}
		
		for (Object peopleString : AllApprovePeopleSet) {
			System.out.println("value=" + peopleString);
			content = "";
			EMPID = getValue("EMPID").trim();
			name = getName(EMPID);
			title = "(" + EMPID + ")" + name + "之用印項目暨核決權限申請表( " + PNO + " ) 已結案";

			content += title + "<br>印章公司別:"+getValue("CHOP_COMPANY")+ "<br>";
			
			if (CHOP1.length() != 0)
				content += "申請之印章編號1:"+CHOP1+" 用途類別:"+getValue("CHOP1_TODO")+"<br>";
			if (CHOP2.length() != 0)
				content += "申請之印章編號2:"+CHOP2+" 用途類別:"+getValue("CHOP2_TODO")+"<br>";
			if (CHOP3.length() != 0)
				content += "申請之印章編號3:"+CHOP3+" 用途類別:"+getValue("CHOP3_TODO")+"<br>";
			 
			email = getEmail((String) peopleString);
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
			message("EMAIL寄出失敗");
			return false;

		}

		message("EMAIL已寄出通知");

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
	
	String[] addElement(String[] org, String added) {
		String[] result = Arrays.copyOf(org, org.length +1);
	    result[org.length] = added;
	    return result;
	}
}
