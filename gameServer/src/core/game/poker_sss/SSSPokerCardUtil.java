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
	
	//六同
	public String CardName_LiuTong = "c_a";
	//五同
	public String CardName_WuTong = "c_9";
	//同花顺
	public String CardName_TongHuaShun = "c_8";
	//铁支
	public String CardName_TieZhi = "c_7";
	//葫芦
	public String CardName_HuLu = "c_6";
	//同花3条
	public String CardName_TongHuaSanTiao = "c_5_3";
	//同花2对
	public String CardName_TongHuaLiangDui = "c_5_2";
	//同花1对
	public String CardName_TongHuaYiDui = "c_5_1";
	//普通同花
	public String CardName_TongHua = "c_5_0";
	//顺子
	public String CardName_ShunZi = "c_4";
	
	//3鬼3条
	public String CardName_SanTiao3Joker = "c_3_3";
	//2鬼3条
	public String CardName_SanTiao2Joker = "c_3_2";
	//1鬼3条 或者 扣牌3条
	public String CardName_SanTiao1Joker = "c_3_1";
	//三条
	public String CardName_SanTiao = "c_3_0";
	
	//两对
	public String CardName_LiangDui = "c_2";
	//一对
	public String CardName_YiDui = "c_1";
	//乌龙
	public String CardName_WuLong = "c_0";
	

	public String CardName_ZhiZunQingLong = "s_19";//至尊青龙：A—K清一色顺子。
	public String CardName_YiTiaoLong = "s_18";//一条龙：A—K顺子。
	public String CardName_SanTongHuaShun = "s_17";//三同花顺
	public String CardName_SanFenTianXia = "s_16";//三分天下：三组铁支和任意单牌
	public String CardName_SiTaoSanTiao = "s_15";//四套三条：拥有四组三条。
	
	public String CardName_SanHuangWuDi = "s_14";//三皇五帝
	public String CardName_ShiErHuangZu = "s_13";//十二皇族 A?
	public String CardName_QuanDa = "s_12";//全大
	public String CardName_QuanXiao = "s_11";//全小
	public String CardName_CouYiSe = "s_10";//凑一色
	public String CardName_WuDuiChongSan = "s_09";//五对冲三
	
	public String CardName_SanTongHua = "s_08";//三同花
	public String CardName_SanShunZi = "s_07";//三顺子
	public String CardName_LiuDuiBan = "s_06";//六对半
	public String CardName_LiuLiuDaShun = "s_05";//六六大顺
	public String CardName_BeiDouQiXing = "s_04";//北斗七星
	public String CardName_BaXianGuoHai = "s_03";//八仙过海
	
	private static SSSPokerCardUtil instance = new SSSPokerCardUtil();
	
	public PokerManager pokerManager = null;

	protected ArrayList<Integer> allJokerCardIDList = new ArrayList<>(); //所有鬼牌
	
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
		
		//初始化鬼牌
		ArrayList<Integer> jokerCardIDList = pokerManager.getJokerCardIDList();
		
		this.allJokerCardIDList.addAll(jokerCardIDList);
	}
	
	//-------------入口---------------
	//外部调用入口
	public void onStart(){
		
		//玩家手牌5张
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(101,102,103,203,204,205,206,207,305,306,307,308,309));
		
		SSSPointCardInfo cardInfo = this.getCardInfo(handCardIDList, this.allJokerCardIDList);
		
		this.info(":{} 结果:{},{}", handCardIDList, cardInfo.cardName, cardInfo.endCardIDList);
	}
	
	//------------------普通牌型----------------------------
	//外部调用入口
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
	
	//获取头道3张牌型
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
	
	//获取尾道 中道牌型
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

	//------------------------------普通牌型---------------------------------
	//获取乌龙牌型
	public SSSDaoCardInfo c_0_WuLong(List<Integer> cardIDList){
		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = cardIDList;
		daoCardInfo.cardName = CardName_WuLong;
		return daoCardInfo;
	}
	
	//获取一对牌型
	public SSSDaoCardInfo c_1_YiDui(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		return null;
	}
	
	//获取2对
	public SSSDaoCardInfo c_2_LiangDui(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		return null;
	}
	
	//获取3条,头道是3尖刀
	public SSSDaoCardInfo c_3_SanTiao(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		return null;
	}
	
	//获取顺子
	public SSSDaoCardInfo c_4_ShunZi(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		return null;
	}
	
	//获取同花  
	public SSSDaoCardInfo c_5_TongHua(List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		return null;
	}
	
	//获取葫芦
	public SSSDaoCardInfo c_6_HuLu(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		return null;
	}
	
	//获取手牌中最大的4条
	public SSSDaoCardInfo c_7_TieZhi(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		return null;
		
	}
	//同花顺
	public SSSDaoCardInfo c_8_TongHuaShun(List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		return null;
	}
	
	//获取手牌中最大的5条
	public SSSDaoCardInfo c_9_WuTong(Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		return null;
	}
	
	
	
	
	//---------------------------特殊牌型----------------------
	//外部调用入口
	public SSSSpecailCardInfo getSpecailCardInfo(List<Integer> handCardIDList, List<Integer> allJokerIDList, int gameID){
		if(handCardIDList.size() != 13){
			this.error("getSpecailCardInfo cardIDList:{} not 13 length", handCardIDList);
			return null;
		}
		
		HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(handCardIDList, allJokerIDList);
		
		Hashtable<Integer, HandPokerValueInfo> value2CardInfo = handPokerInfo.value2CardInfo;
		Hashtable<Integer, HandPokerColorInfo> color2CardInfo = handPokerInfo.color2CardInfo;
		List<Integer> handJokerCardIDList = handPokerInfo.handJokerCardIDList;
		
		//如果有鬼牌 不是特殊牌型
		if(handJokerCardIDList.size() > 0){
			return null;
		}
			
		SSSSpecailCardInfo specailCardInfo = this.getSpecailCardName(100, handCardIDList, color2CardInfo, value2CardInfo);
		
		return specailCardInfo;
	}
	
	//获取指定特殊牌型
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
	
	//1.	至尊青龙：A—K清一色顺子。
	public SSSSpecailCardInfo s_19_ZhiZunQingLong(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//2.	一条龙：A—K顺子。
	public SSSSpecailCardInfo s_18_YiTiaoLong(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//3.	三同花顺：头道、中道、尾道皆可配出同花顺牌型。   
	public SSSSpecailCardInfo s_17_SanTongHuaShun(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//4.	三分天下：三组铁支和任意单牌。
	public SSSSpecailCardInfo s_16_SanFenTianXia(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//10.	四套三条：拥有四组三条，赢每家6分。
	public SSSSpecailCardInfo s_15_SiTaoSanTiao(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//2个5同，1个三条
	public SSSSpecailCardInfo s_14_SanHuangWuDi(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//13张都是J-Q-K-A，赢每家24分。
	public SSSSpecailCardInfo s_13_ShiErHuangZu(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		
		return null;
	}
	
	//十三张牌数字都为8~A，赢每家10分。
	public SSSSpecailCardInfo s_12_QuanDa(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		
		return null;
	}
	
	//十三张牌数字都为2~8，赢每家10分。
	public SSSSpecailCardInfo s_11_QuanXiao(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){

		return null;
	}

	//9.	凑一色：十三张牌都是红色（方块/红心）或黑色（梅花/黑桃），赢每家10分。
	public SSSSpecailCardInfo s_10_CouYiSe(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		
		return null;
	}
	
	//11.	五对冲三：拥有5个对子和一个三条，赢每家5分。
	public SSSSpecailCardInfo s_09_WuDuiChongSan(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//14.	三同花：头道、中道、尾道皆可配出同花牌型，赢每家5分。
	public SSSSpecailCardInfo s_08_SanTongHua(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//13.	三顺子：头道、中道、尾道皆可配出顺子牌型，赢每家5分。
	public SSSSpecailCardInfo s_07_SanShunZi(int point, List<Integer>cardIDList){
		return null;
	}
	

		
	//12.	六对半：13张牌中拥有6张对牌，赢每家5分。
	public SSSSpecailCardInfo s_06_LiuDuiBan(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//6.	六六大顺：有6张一样的牌，赢每家20分。
	public SSSSpecailCardInfo s_05_LiuLiuDaShun(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//6.	北斗七星：有7张一样的牌，赢每家20分。
	public SSSSpecailCardInfo s_04_BeiDouQiXing(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		return null;
	}
	
	//6.	八仙过海：有8张一样的牌，赢每家20分。
	public SSSSpecailCardInfo s_03_BaXianGuoHai(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		
		return null;
	}
}
