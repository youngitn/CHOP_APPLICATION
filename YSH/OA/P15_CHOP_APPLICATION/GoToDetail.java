package YSH.OA.P15_CHOP_APPLICATION;

//YSH/OA/P15_CHOP_APPLICATION/GoToDetail
import jcx.db.talk;
import jcx.jform.hproc;

public class GoToDetail extends hproc {

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		talk t = getTalk();
		CHOP_APPLICATION_DAO dao = new CHOP_APPLICATION_DAO();
		CHOP_APPLICATION_BEAN bean = dao.getBean(getValue("table1.PNO"), t);
		String sql,HECNAME,DEP_NAME;
		
		if (bean.getEMPID().trim().length() != 0){
			sql = "select HECNAME,DEP_NAME from USER_INFO_VIEW where EMPID = '"+bean.getEMPID()+"'";
			String[][] ret = t.queryFromPool(sql);

			HECNAME = ret[0][0];
			DEP_NAME = ret[0][1];
			
			setValue("EMPID_NAME",HECNAME);
			setValue("DEPT_NO_NAME",DEP_NAME);
		}
		if (bean.getCHOP_USER().trim().length() != 0){
			sql = "select HECNAME,DEP_NAME from USER_INFO_VIEW where EMPID = '"+bean.getCHOP_USER()+"'";
			String[][] ret = t.queryFromPool(sql);
			if (ret.length != 0){
				HECNAME = ret[0][0];				
				setValue("CHOP_USER_NAME",HECNAME);
			}
			
		}
		setVisible("SEND", false);
		setVisible("QUERYPAGE", false);
		setValue("PNO", bean.getPNO());
		setValue("CPNYID", bean.getCPNYID());
		setValue("EMPID", bean.getEMPID());
		setValue("APP_TYPE", bean.getAPP_TYPE());
		setValue("CHOP_COMPANY", bean.getCHOP_COMPANY());
		setValue("CHANGE_TYPE", bean.getCHANGE_TYPE());
		setValue("CHOP1_NO", bean.getCHOP1_NO());
		setValue("CHOP1_TODO", bean.getCHOP1_TODO());
		setValue("CHOP1_PROCESS_DEPT", bean.getCHOP1_PROCESS_DEPT());
		setValue("CHOP1_SIGN_LV", bean.getCHOP1_SIGN_LV());
		setValue("CHOP1_NOTE", bean.getCHOP1_NOTE());
		setValue("CHOP2_NO", bean.getCHOP2_NO());
		setValue("CHOP2_TODO", bean.getCHOP2_TODO());
		setValue("CHOP2_PROCESS_DEPT", bean.getCHOP2_PROCESS_DEPT());
		setValue("CHOP2_SIGN_LV", bean.getCHOP2_SIGN_LV());
		setValue("CHOP2_NOTE", bean.getCHOP2_NOTE());
		setValue("CHOP3_NO", bean.getCHOP3_NO());
		setValue("CHOP3_TODO", bean.getCHOP3_TODO());
		setValue("CHOP3_PROCESS_DEP", bean.getCHOP3_PROCESS_DEPT());
		setValue("CHOP3_SIGN_LV", bean.getCHOP3_SIGN_LV());
		setValue("CHOP3_NOTE", bean.getCHOP3_NOTE());
		setValue("CHOP_USER", bean.getCHOP_USER());
		setValue("NOTE", bean.getNOTE());
		setValue("DATE", bean.getDATE());
		setVisible("SEND", false);
		setVisible("QUERYPAGE", false);
		return arg0;
	}
}
