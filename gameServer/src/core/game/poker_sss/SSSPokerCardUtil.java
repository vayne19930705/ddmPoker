package core.game.poker_sss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import business.global.log.BaseLog;
import business.global.poker.HandPokerColorInfo;
import business.global.poker.HandPokerInfo;
import business.global.poker.HandPokerValueInfo;
import business.global.poker.PokerManager;

public class SSSPokerCardUtil extends BaseLog{
	
	//��ͬ
	public String CardName_LiuTong = "c_a";
	//��ͬ
	public String CardName_WuTong = "c_9";
	//ͬ��˳
	public String CardName_TongHuaShun = "c_8";
	//��֧
	public String CardName_TieZhi = "c_7";
	//��«
	public String CardName_HuLu = "c_6";
	//ͬ��3��
	public String CardName_TongHuaSanTiao = "c_5_3";
	//ͬ��2��
	public String CardName_TongHuaLiangDui = "c_5_2";
	//ͬ��1��
	public String CardName_TongHuaYiDui = "c_5_1";
	//��ͨͬ��
	public String CardName_TongHua = "c_5_0";
	//˳��
	public String CardName_ShunZi = "c_4";
	
	//3��3��
	public String CardName_SanTiao3Joker = "c_3_3";
	//2��3��
	public String CardName_SanTiao2Joker = "c_3_2";
	//1��3�� ���� ����3��
	public String CardName_SanTiao1Joker = "c_3_1";
	//����
	public String CardName_SanTiao = "c_3_0";
	
	//����
	public String CardName_LiangDui = "c_2";
	//һ��
	public String CardName_YiDui = "c_1";
	//����
	public String CardName_WuLong = "c_0";
	

	public String CardName_ZhiZunQingLong = "s_19";//����������A��K��һɫ˳�ӡ�
	public String CardName_YiTiaoLong = "s_18";//һ������A��K˳�ӡ�
	public String CardName_SanTongHuaShun = "s_17";//��ͬ��˳
	public String CardName_SanFenTianXia = "s_16";//�������£�������֧�����ⵥ��
	public String CardName_SiTaoSanTiao = "s_15";//����������ӵ������������
	
	public String CardName_SanHuangWuDi = "s_14";//�������
	public String CardName_ShiErHuangZu = "s_13";//ʮ������ A?
	public String CardName_QuanDa = "s_12";//ȫ��
	public String CardName_QuanXiao = "s_11";//ȫС
	public String CardName_CouYiSe = "s_10";//��һɫ
	public String CardName_WuDuiChongSan = "s_09";//��Գ���
	
	public String CardName_SanTongHua = "s_08";//��ͬ��
	public String CardName_SanShunZi = "s_07";//��˳��
	public String CardName_LiuDuiBan = "s_06";//���԰�
	public String CardName_LiuLiuDaShun = "s_05";//������˳
	public String CardName_BeiDouQiXing = "s_04";//��������
	public String CardName_BaXianGuoHai = "s_03";//���ɹ���
	
	private static SSSPokerCardUtil instance = new SSSPokerCardUtil();
	
	public PokerManager pokerManager = null;

	protected ArrayList<Integer> allJokerCardIDList = new ArrayList<>(); //���й���
	
	public static SSSPokerCardUtil getInstance(){
		return instance;
	}
	
	public SSSPokerCardUtil(){
		this.init();
	}
	
	public String getLogString(){
		return "[SSSPokerCardUtil]\t";
	}
	
	public void init(){
		this.pokerManager = PokerManager.getInstance();
		
		//��ʼ������
		ArrayList<Integer> jokerCardIDList = pokerManager.getJokerCardIDList();
		
		this.allJokerCardIDList.addAll(jokerCardIDList);
	}
	
	//-------------���---------------
	//�ⲿ�������
	public void onStart(){
		
		//�������5��
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(101,102,103,203,204,205,206,207,305,306,307,308,309));
		
		SSSPointCardInfo cardInfo = this.getCardInfo(handCardIDList, this.allJokerCardIDList);
		
		this.info(":{} ���:{},{}", handCardIDList, cardInfo.cardName, cardInfo.endCardIDList);
	}
	
	//------------------��ͨ����----------------------------
	//�ⲿ�������
	public SSSPointCardInfo getCardInfo(List<Integer> handCardIDList, List<Integer> allJokerIDList){
		
		if(handCardIDList.size() != 13){
			this.error("getCardInfo cardIDList:{} not 13 length", handCardIDList);
			return null;
		}
		
		SSSPointCardInfo cardInfo = new SSSPointCardInfo();
		
		List<Integer> firstDaoCardIDList = new ArrayList<>(handCardIDList.subList(0, 3));
		List<Integer> secondDaoCardIDList = new ArrayList<>(handCardIDList.subList(3, 8));
		List<Integer> thirdDaoCardIDList = new ArrayList<>(handCardIDList.subList(8, 13));
		
		SSSDaoCardInfo firstDaoCardInfo = this.get3CardCardName(firstDaoCardIDList, allJokerIDList);
		cardInfo.endCardIDList.addAll(firstDaoCardInfo.srcCardIDList);
		cardInfo.cardName.add(firstDaoCardInfo.cardName);
		
		SSSDaoCardInfo secondDaoCardInfo = this.get5CardCardName(secondDaoCardIDList, allJokerIDList);
		cardInfo.endCardIDList.addAll(secondDaoCardInfo.srcCardIDList);
		cardInfo.cardName.add(secondDaoCardInfo.cardName);
		
		SSSDaoCardInfo thirdDaoCardInfo = this.get5CardCardName(thirdDaoCardIDList, allJokerIDList);
		cardInfo.endCardIDList.addAll(thirdDaoCardInfo.srcCardIDList);
		cardInfo.cardName.add(thirdDaoCardInfo.cardName);

		return cardInfo;
	}
	
	//��ȡͷ��3������
	public SSSDaoCardInfo get3CardCardName(List<Integer> cardIDList, List<Integer> allJokerIDList){
		SSSDaoCardInfo daoCardInfo = null;
		
		HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(cardIDList, allJokerIDList);
		
		Hashtable<Integer, HandPokerValueInfo> value2CardInfo = handPokerInfo.value2CardInfo;
		
		List<Integer> handJokerCardIDList = handPokerInfo.handJokerCardIDList;
		
		daoCardInfo = this.c_3_SanTiao(cardIDList, value2CardInfo, handJokerCardIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		daoCardInfo = this.c_1_YiDui(cardIDList, value2CardInfo, handJokerCardIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		return this.c_0_WuLong(cardIDList);
	}
	
	//��ȡβ�� �е�����
	public SSSDaoCardInfo get5CardCardName(List<Integer> cardIDList, List<Integer> allJokerIDList){
		
		HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(cardIDList, allJokerIDList);
		
		Hashtable<Integer, HandPokerValueInfo> value2CardInfo = handPokerInfo.value2CardInfo;
		Hashtable<Integer, HandPokerColorInfo> color2CardInfo = handPokerInfo.color2CardInfo;
		
		List<Integer> handJokerCardIDList = handPokerInfo.handJokerCardIDList;
		
		SSSDaoCardInfo daoCardInfo = null;
		
		daoCardInfo = c_9_WuTong(value2CardInfo, handJokerCardIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		daoCardInfo = c_8_TongHuaShun(cardIDList, color2CardInfo, value2CardInfo, handJokerCardIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		daoCardInfo = c_7_TieZhi(cardIDList, value2CardInfo, handJokerCardIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		daoCardInfo = c_6_HuLu(cardIDList, value2CardInfo, handJokerCardIDList, allJokerIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		daoCardInfo = c_5_TongHua(cardIDList, color2CardInfo, value2CardInfo, handJokerCardIDList, allJokerIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		daoCardInfo = c_4_ShunZi(cardIDList, value2CardInfo, handJokerCardIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		daoCardInfo = c_3_SanTiao(cardIDList, value2CardInfo, handJokerCardIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		daoCardInfo = c_2_LiangDui(cardIDList, value2CardInfo, handJokerCardIDList, allJokerIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		daoCardInfo = c_1_YiDui(cardIDList, value2CardInfo, handJokerCardIDList);
		if(daoCardInfo != null){
			return daoCardInfo;
		}
		
		return this.c_0_WuLong(cardIDList);
	}

	//------------------------------��ͨ����---------------------------------
	//��ȡ��������
	public SSSDaoCardInfo c_0_WuLong(List<Integer> cardIDList){
		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = cardIDList;
		daoCardInfo.cardName = CardName_WuLong;
		return daoCardInfo;
	}
	
	//��ȡһ������
	public SSSDaoCardInfo c_1_YiDui(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		return null;
	}
	
	//��ȡ2��
	public SSSDaoCardInfo c_2_LiangDui(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		return null;
	}
	
	//��ȡ3��,ͷ����3�⵶
	public SSSDaoCardInfo c_3_SanTiao(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		return null;
	}
	
	//��ȡ˳��
	public SSSDaoCardInfo c_4_ShunZi(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		return null;
	}
	
	//��ȡͬ��  
	public SSSDaoCardInfo c_5_TongHua(List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		return null;
	}
	
	//��ȡ��«
	public SSSDaoCardInfo c_6_HuLu(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		return null;
	}
	
	//��ȡ����������4��
	public SSSDaoCardInfo c_7_TieZhi(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		return null;
		
	}
	//ͬ��˳
	public SSSDaoCardInfo c_8_TongHuaShun(List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		return null;
	}
	
	//��ȡ����������5��
	public SSSDaoCardInfo c_9_WuTong(Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		return null;
	}
	
	
	
	
	//---------------------------��������----------------------
	//�ⲿ�������
	public SSSSpecailCardInfo getSpecailCardInfo(List<Integer> handCardIDList, List<Integer> allJokerIDList, int gameID){
		if(handCardIDList.size() != 13){
			this.error("getSpecailCardInfo cardIDList:{} not 13 length", handCardIDList);
			return null;
		}
		
		HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(handCardIDList, allJokerIDList);
		
		Hashtable<Integer, HandPokerValueInfo> value2CardInfo = handPokerInfo.value2CardInfo;
		Hashtable<Integer, HandPokerColorInfo> color2CardInfo = handPokerInfo.color2CardInfo;
		List<Integer> handJokerCardIDList = handPokerInfo.handJokerCardIDList;
		
		//����й��� ������������
		if(handJokerCardIDList.size() > 0){
			return null;
		}
			
		SSSSpecailCardInfo specailCardInfo = this.getSpecailCardName(100, handCardIDList, color2CardInfo, value2CardInfo);
		
		return specailCardInfo;
	}
	
	//��ȡָ����������
	public SSSSpecailCardInfo getSpecailCardName(int point, List<Integer> handCardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo specailCardInfo = null;
		specailCardInfo = s_19_ZhiZunQingLong(point, handCardIDList, color2CardInfo, value2CardInfo);
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_18_YiTiaoLong(point, handCardIDList, color2CardInfo, value2CardInfo);
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_17_SanTongHuaShun(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_16_SanFenTianXia(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_15_SiTaoSanTiao(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_14_SanHuangWuDi(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_13_ShiErHuangZu(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_12_QuanDa(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_11_QuanXiao(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_10_CouYiSe(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_09_WuDuiChongSan(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_08_SanTongHua(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_07_SanShunZi(point, handCardIDList);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_06_LiuDuiBan(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_05_LiuLiuDaShun(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_04_BeiDouQiXing(point, handCardIDList, color2CardInfo, value2CardInfo);	
		if(specailCardInfo != null){
			return specailCardInfo;
		}
		
		specailCardInfo =  s_03_BaXianGuoHai(point, handCardIDList, color2CardInfo, value2CardInfo);	
		
		return specailCardInfo;
	}
	
	//1.	����������A��K��һɫ˳�ӡ�
	public SSSSpecailCardInfo s_19_ZhiZunQingLong(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//2.	һ������A��K˳�ӡ�
	public SSSSpecailCardInfo s_18_YiTiaoLong(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//3.	��ͬ��˳��ͷ�����е���β���Կ����ͬ��˳���͡�   
	public SSSSpecailCardInfo s_17_SanTongHuaShun(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//4.	�������£�������֧�����ⵥ�ơ�
	public SSSSpecailCardInfo s_16_SanFenTianXia(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//10.	����������ӵ������������Ӯÿ��6�֡�
	public SSSSpecailCardInfo s_15_SiTaoSanTiao(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//2��5ͬ��1������
	public SSSSpecailCardInfo s_14_SanHuangWuDi(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//13�Ŷ���J-Q-K-A��Ӯÿ��24�֡�
	public SSSSpecailCardInfo s_13_ShiErHuangZu(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		
		return null;
	}
	
	//ʮ���������ֶ�Ϊ8~A��Ӯÿ��10�֡�
	public SSSSpecailCardInfo s_12_QuanDa(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		
		return null;
	}
	
	//ʮ���������ֶ�Ϊ2~8��Ӯÿ��10�֡�
	public SSSSpecailCardInfo s_11_QuanXiao(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){

		return null;
	}

	//9.	��һɫ��ʮ�����ƶ��Ǻ�ɫ������/���ģ����ɫ��÷��/���ң���Ӯÿ��10�֡�
	public SSSSpecailCardInfo s_10_CouYiSe(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		
		return null;
	}
	
	//11.	��Գ�����ӵ��5�����Ӻ�һ��������Ӯÿ��5�֡�
	public SSSSpecailCardInfo s_09_WuDuiChongSan(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//14.	��ͬ����ͷ�����е���β���Կ����ͬ�����ͣ�Ӯÿ��5�֡�
	public SSSSpecailCardInfo s_08_SanTongHua(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//13.	��˳�ӣ�ͷ�����е���β���Կ����˳�����ͣ�Ӯÿ��5�֡�
	public SSSSpecailCardInfo s_07_SanShunZi(int point, List<Integer>cardIDList){
		return null;
	}
	

		
	//12.	���԰룺13������ӵ��6�Ŷ��ƣ�Ӯÿ��5�֡�
	public SSSSpecailCardInfo s_06_LiuDuiBan(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//6.	������˳����6��һ�����ƣ�Ӯÿ��20�֡�
	public SSSSpecailCardInfo s_05_LiuLiuDaShun(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//6.	�������ǣ���7��һ�����ƣ�Ӯÿ��20�֡�
	public SSSSpecailCardInfo s_04_BeiDouQiXing(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//6.	���ɹ�������8��һ�����ƣ�Ӯÿ��20�֡�
	public SSSSpecailCardInfo s_03_BaXianGuoHai(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		
		return null;
	}
}
