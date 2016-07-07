package com.github.chm;

public class Test {
	public static void main(String[] args){
		String regex = "^[\u4e00-\u9fa5][A-Z0-9]{6}$";
		String test = "浙AZD19x";
		if(test!=null && !test.matches(regex)){
			System.out.println("fuck");
		}
	}

}
