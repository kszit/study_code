package com.kszit.util.String;

public class ByeSize {


	
	public static void main(String[] a){
		String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?><patients><patient><patientId>2297063</patientId><patientName>韩兆辉</patientName><patientIdCard>220105197311082219</patientIdCard><patientCode>22010000040</patientCode><cpIntervRiskCode></cpIntervRiskCode><cpIsMatchScreen>true</cpIsMatchScreen><cpIsConsent>true</cpIsConsent><cpAppointmentRegisterDate></cpAppointmentRegisterDate><followRiskDate></followRiskDate><isSpecial></isSpecial><REG_CREATE_BY>000000000</REG_CREATE_BY><REG_CREATE_DATE>2015-08-04 00:00:00</REG_CREATE_DATE><cpNewPhone></cpNewPhone></patient></patients>";
		System.out.println(s.getBytes().length);
		
	}

}
