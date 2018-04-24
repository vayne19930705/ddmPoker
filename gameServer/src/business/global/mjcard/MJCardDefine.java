package business.global.mjcard;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
*
*
* ץ��������ʱ�����
* ����˳ʱ�뱻ץ
* 
* �齫�Ʊ��˵��:
* 
* ������:��:9*4*3=108
* 1101-1901 1��-9��  1101-1104 4��1��
* 2101-2901 1��-9��
* 3101-3901 1Ͳ-9Ͳ
* 
* ==========4000==========
* ����:��4��  ��:4*4=16
* 4101 ��
* 4201 ��
* 4301 ��
* 4401 ��
* 
* ����:��4�� ��:3*4=12
* 5101 ��
* 5201 ��
* 5301 ��
* 
* ����:��һ�� ��:4*1=4
* 6101 ÷
* 6201 ��
* 6301 ��
* 6401 ��
* 
* ����:��һ�� ��:4*1=4
* 7101 ��
* 7201 ��
* 7301 ��
* 7401 ��
* 
* �ٴ���:4�� ��:1*4=4
* 8101:�ٴ���
* 
* ������:���� 4*1=4
* 9101:����
* 9201:����
* 9301:è
* 9401:�۱���
* 
* �ٴ��齫:
* �ܹ�:108+16+12+4+4+4+4 = 152��
* 
* �����齫:
* �ܹ�:108+16+12 = 136��
* 
* �����齫
*  �ܹ�:108 + 4(����) = 112��
* 
* �����齫:
* �ܹ�:(108 - 4(��:2��*2��2Ͳ*1��2��*1)) + 16 + 12 + (4 + 4 + 4(��:һ�ݴ����ﶬ)) = 144��
*/

public class MJCardDefine {
	
	//������,1:��,2:��,3:Ͳ,4:����,5:����,6:����,7:����,8:�ٴ�,9:������
	
	public static int WAN = 1;
	
	public static int TIAO = 2;
	
	public static int TONG = 3;
	
	public static int FENG = 4;
	
	public static int JIAN = 5;
	
	public static int HUA = 6;
	
	public static int JI = 7;
	
	public static int BAIDA = 8;
	
	public static int SPE = 9;
	
	//����value��Ӧ�ĵ�λ���� �̶�1-9+��λ
	public static String WANString = "��";
	public static String TIAOString = "��";
	public static String TONGString = "Ͳ";
	
	//�������ַ�Χ
	public static List<Integer> ZiPaiValueList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
	
	public static List<Integer> WANCardTypeValueList = Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19);
	public static List<Integer> TIAOCardTypeValueList = Arrays.asList(21, 22, 23, 24, 25, 26, 27, 28, 29);
	public static List<Integer> TONGCardTypeValueList = Arrays.asList(31, 32, 33, 34, 35, 36, 37, 38, 39);
	
	//����value��Ӧ�ĵ�λ����
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
	
	public static String BAIDAString = "�ٴ�";
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
		
		FENGStingInfo.put(East, "��");
		FENGStingInfo.put(South, "��");
		FENGStingInfo.put(West, "��");
		FENGStingInfo.put(North, "��");
		
		JIANStingInfo.put(Zhong, "��");
		JIANStingInfo.put(Fa, "��");
		JIANStingInfo.put(Bai, "��");
				
		HUAStingInfo.put(Mei, "÷");
		HUAStingInfo.put(Lan, "��");
		HUAStingInfo.put(Zhu, "��");
		HUAStingInfo.put(Ju, "��");
		
		JIStingInfo.put(Spring, "��");
		JIStingInfo.put(Summer, "��");
		JIStingInfo.put(Autumn, "��");
		JIStingInfo.put(Winter, "��");
		
		SPEStingInfo.put(Mouse, "����");
		SPEStingInfo.put(CaiShen, "����");
		SPEStingInfo.put(Cat, "è");
		SPEStingInfo.put(JuBaoPeng, "�۱���");
	}
	
}
