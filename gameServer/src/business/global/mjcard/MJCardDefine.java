package business.global.mjcard;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
*
*
* 抓牌人是逆时针出手
* 牌是顺时针被抓
* 
* 麻将牌编号说明:
* 
* 序数牌:共:9*4*3=108
* 1101-1901 1万-9万  1101-1104 4张1万
* 2101-2901 1条-9条
* 3101-3901 1筒-9筒
* 
* ==========4000==========
* 风牌:各4张  共:4*4=16
* 4101 东
* 4201 西
* 4301 南
* 4401 北
* 
* 箭牌:各4张 共:3*4=12
* 5101 中
* 5201 发
* 5301 白
* 
* 花牌:各一张 共:4*1=4
* 6101 梅
* 6201 兰
* 6301 竹
* 6401 菊
* 
* 季牌:各一张 共:4*1=4
* 7101 春
* 7201 夏
* 7301 秋
* 7401 东
* 
* 百搭牌:4张 共:1*4=4
* 8101:百搭牌
* 
* 特殊牌:各张 4*1=4
* 9101:老鼠
* 9201:财神
* 9301:猫
* 9401:聚宝盆
* 
* 百搭麻将:
* 总共:108+16+12+4+4+4+4 = 152张
* 
* 精简麻将:
* 总共:108+16+12 = 136张
* 
* 红中麻将
*  总共:108 + 4(红中) = 112张
* 
* 拉米麻将:
* 总共:(108 - 4(扣:2万*2，2筒*1，2条*1)) + 16 + 12 + (4 + 4 + 4(多:一份春夏秋冬)) = 144张
*/

public class MJCardDefine {
	
	//牌类型,1:万,2:条,3:筒,4:风牌,5:箭牌,6:花牌,7:季牌,8:百搭,9:特殊牌
	
	public static int WAN = 1;
	
	public static int TIAO = 2;
	
	public static int TONG = 3;
	
	public static int FENG = 4;
	
	public static int JIAN = 5;
	
	public static int HUA = 6;
	
	public static int JI = 7;
	
	public static int BAIDA = 8;
	
	public static int SPE = 9;
	
	//字牌value对应的单位名字 固定1-9+单位
	public static String WANString = "万";
	public static String TIAOString = "条";
	public static String TONGString = "筒";
	
	//字牌数字范围
	public static List<Integer> ZiPaiValueList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
	
	public static List<Integer> WANCardTypeValueList = Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19);
	public static List<Integer> TIAOCardTypeValueList = Arrays.asList(21, 22, 23, 24, 25, 26, 27, 28, 29);
	public static List<Integer> TONGCardTypeValueList = Arrays.asList(31, 32, 33, 34, 35, 36, 37, 38, 39);
	
	//卡牌value对应的单位名字
	public static int East = 1;
	public static int South = 2;
	public static int West = 3;
	public static int North = 4;
	public static List<Integer> FengPaiValueList = Arrays.asList(East, South, West, North);
	
	public static List<Integer> FengCardTypeValueList = Arrays.asList(41, 42, 43, 44);
	
	public static Hashtable<Integer, String> FENGStingInfo = new Hashtable<>();
	
	public static int Zhong = 1;
	public static int Fa = 2;
	public static int Bai = 3;
	public static List<Integer> JianPaiValueList = Arrays.asList(Zhong, Fa, Bai);
	
	public static List<Integer> JianCardTypeValueList = Arrays.asList(51, 52, 53);
	
	public static Hashtable<Integer, String> JIANStingInfo = new Hashtable<>();
	
	
	public static int Mei = 1;
	public static int Lan = 2;
	public static int Zhu = 3;
	public static int Ju = 4;
	public static List<Integer> HuaPaiValueList = Arrays.asList(Mei, Lan, Zhu, Ju);
	public static List<Integer> HuaCardTypeValueList = Arrays.asList(61, 62, 63, 64);
	
	public static Hashtable<Integer, String> HUAStingInfo = new Hashtable<>();
	
	public static int Spring = 1;
	public static int Summer = 2;
	public static int Autumn = 3;
	public static int Winter = 4;
	public static List<Integer> JiPaiValueList = Arrays.asList(Spring, Summer, Autumn, Winter);
	public static List<Integer> JiCardTypeValueList = Arrays.asList(71, 72, 73, 74);
	
	public static Hashtable<Integer, String> JIStingInfo = new Hashtable<>();
	
	public static String BAIDAString = "百搭";
	public static List<Integer> BAIDPaiValueList = Arrays.asList(1);
	public static List<Integer> BAIDCardTypeValueList = Arrays.asList(81);
	
	public static int Mouse = 1;
	public static int CaiShen = 2;
	public static int Cat = 3;
	public static int JuBaoPeng = 4;
	
	public static List<Integer> SPEPaiValueList = Arrays.asList(Mouse, CaiShen, Cat, JuBaoPeng);
	public static List<Integer> SPECardTypeValueList = Arrays.asList(91,92,93,94);
	
	public static Hashtable<Integer, String> SPEStingInfo = new Hashtable<>();
	
	static{
		
		FENGStingInfo.put(East, "东");
		FENGStingInfo.put(South, "南");
		FENGStingInfo.put(West, "西");
		FENGStingInfo.put(North, "北");
		
		JIANStingInfo.put(Zhong, "中");
		JIANStingInfo.put(Fa, "发");
		JIANStingInfo.put(Bai, "白");
				
		HUAStingInfo.put(Mei, "梅");
		HUAStingInfo.put(Lan, "兰");
		HUAStingInfo.put(Zhu, "竹");
		HUAStingInfo.put(Ju, "菊");
		
		JIStingInfo.put(Spring, "春");
		JIStingInfo.put(Summer, "夏");
		JIStingInfo.put(Autumn, "秋");
		JIStingInfo.put(Winter, "东");
		
		SPEStingInfo.put(Mouse, "老鼠");
		SPEStingInfo.put(CaiShen, "财神");
		SPEStingInfo.put(Cat, "猫");
		SPEStingInfo.put(JuBaoPeng, "聚宝盆");
	}
	
}
