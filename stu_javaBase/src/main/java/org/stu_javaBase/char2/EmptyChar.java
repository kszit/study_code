package org.stu_javaBase.char2;

public class EmptyChar {

	public char cha1;
	public static void main(String[] a){
		EmptyChar empltyChar = new EmptyChar();
		
//		System.out.println(empltyChar.cha1);
//		if(Character.isWhitespace(empltyChar.cha1)||Character.isSpaceChar(empltyChar.cha1)){
//			System.out.println("为空");
//		}else{
//			System.out.println("不为空");
//		}
		//Character.isSpaceChar(empltyChar.cha1)
		if(Character.isWhitespace(empltyChar.cha1)){
			System.out.println("为空");
		}else{
			System.out.println("不为空");
		}
		System.out.println(empltyChar.cha1);
	}
}
