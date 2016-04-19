package YSH.OA.P15_CHOP_APPLICATION;
//YSH/OA/P15_CHOP_APPLICATION/SignInit
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import jcx.db.talk;
import jcx.jform.hproc;

public class SignInit extends hproc{

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		String sql,HECNAME,DEP_NAME;
		talk t = getTalk();
		
		if (getValue("CHOP_USER").trim().length() != 0){
			sql = "select HECNAME,DEP_NAME from USER_INFO_VIEW where EMPID = '"+getValue("CHOP_USER")+"'";
			String[][] ret = t.queryFromPool(sql);

			HECNAME = ret[0][0];
			DEP_NAME = ret[0][1];
			
			setValue("CHOP_USER_NAME",HECNAME);
			
		}
		if (getValue("EMPID").trim().length() != 0){
			sql = "select HECNAME,DEP_NAME from USER_INFO_VIEW where EMPID = '"+getValue("EMPID")+"'";
			String[][] ret = t.queryFromPool(sql);

			HECNAME = ret[0][0];
			DEP_NAME = ret[0][1];
			
			setValue("EMPID_NAME",HECNAME);
			setValue("DEPT_NO_NAME",DEP_NAME);
		}
		if (POSITION == 5){
			setVisible("SEND", false);
			setVisible("QUERYPAGE", false);
			
		}
		return arg0;
	}

}
