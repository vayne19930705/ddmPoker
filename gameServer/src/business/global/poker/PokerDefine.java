package business.global.poker;

import java.util.Arrays;
import java.util.List;

public class PokerDefine {
	public static List<String> ShowList = Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "S", "W");
	//小鬼:S，大鬼:W "1",< "2",< "3",< "4",< "5",< "6",< "7",< "8",< "9",< "10",< "J",< "Q",< "K",< "S",< "W"
	public static List<String> StringValueList = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "s", "w");
		
	public static List<Integer> ValueList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 500, 600);
	
	public static List<String> ImgList = Arrays.asList("♦", "♣", "♥", "♠");
	
	public static List<Integer> CommonValueList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
	
	//黑色的color列表
	public static List<Integer> blackColorList = Arrays.asList(2,4);
	
	//红色的color列表
	public static List<Integer> redColorList = Arrays.asList(1,3);
	
	//小鬼
	public static String sJokerShow = "S";
	public static String sJokerStringValue = "s";
	public static Integer sJokerValue = 500;
	//大鬼
	public static String bJokerShow = "W";
	public static String bJokerStringValue = "w";
	public static Integer bJokerValue = 600;
	
	//A的牌面值
	public static Integer A_Value = 1;
	
	//鬼牌值列表
	public static List<Integer> jokerValueList = Arrays.asList(sJokerValue, bJokerValue);
	
	//鬼牌ID列表
	public static List<Integer> jokerIDList = Arrays.asList(sJokerValue, bJokerValue);
}
