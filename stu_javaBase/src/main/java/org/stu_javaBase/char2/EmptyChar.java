package org.stu_javaBase.char2;

public class EmptyChar {

	public char cha1;
	public static void main(String[] a){
		EmptyChar empltyChar = new EmptyChar();
		
//		System.out.println(empltyChar.cha1);
//		if(Character.isWhitespace(empltyChar.cha1)||Character.isSpaceChar(empltyChar.cha1)){
//			System.out.println("Ϊ��");
//		}else{
//			System.out.println("��Ϊ��");
//		}
		//Character.isSpaceChar(empltyChar.cha1)
		if(Character.isWhitespace(empltyChar.cha1)){
			System.out.println("Ϊ��");
		}else{
			System.out.println("��Ϊ��");
		}
		System.out.println(empltyChar.cha1);
	}
}
