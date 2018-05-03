package core.game.poker_sss;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.corba.se.impl.oa.poa.Policies;

import business.global.log.BaseLog;
import business.global.poker.HandPokerColorInfo;
import business.global.poker.HandPokerInfo;
import business.global.poker.HandPokerValueInfo;
import business.global.poker.Poker;
import business.global.poker.PokerGroupCardInfo;
import business.global.poker.PokerManager;

public class SSSPokerCardUtil extends BaseLog{
	
	//六同
	public String CardName_LiuTong = "c_a";
	//五同     %s为牌的string值
	public String CardName_WuTong = "c_9_%s";
	//同花顺 
	public String CardName_TongHuaShun = "c_8_%s";
	//铁支
	public String CardName_TieZhi = "c_7_%s";
	//葫芦  
	public String CardName_HuLu = "c_6_%s";
	//同花3条
	public String CardName_TongHuaSanTiao = "c_5_3_%s";
	//同花2对
	public String CardName_TongHuaLiangDui = "c_5_2_%s";
	//同花1对
	public String CardName_TongHuaYiDui = "c_5_1_%s";
	//普通同花  %s 为牌的点数
	public String CardName_TongHua = "c_5_0_%s";
	//顺子 
	public String CardName_ShunZi = "c_4_%s";
	
	//3鬼3条
	public String CardName_SanTiao3Joker = "c_3_3";
	//2鬼3条
	public String CardName_SanTiao2Joker = "c_3_2";
	//1鬼3条 或者 扣牌3条
	public String CardName_SanTiao1Joker = "c_3_1";
	//三条
	public String CardName_SanTiao = "c_3_%s";
	
	//两对 
	public String CardName_LiangDui = "c_2_%s";
	//一对 
	public String CardName_YiDui = "c_1_%s";
	//乌龙 %s 表示总的点数string
	public String CardName_WuLong = "c_0_%s";
	
	

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
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(101,102,103,401,401,500,304,304,410,412,412,413,500));
		
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
		cardInfo.endCardIDList.addAll(firstDaoCardInfo.showCardIDList);
		cardInfo.cardName.add(firstDaoCardInfo.cardName);
		
		
		SSSDaoCardInfo secondDaoCardInfo = this.get5CardCardName(secondDaoCardIDList, allJokerIDList);
		cardInfo.endCardIDList.addAll(secondDaoCardInfo.showCardIDList);
		cardInfo.cardName.add(secondDaoCardInfo.cardName);
		
		SSSDaoCardInfo thirdDaoCardInfo = this.get5CardCardName(thirdDaoCardIDList, allJokerIDList);
		cardInfo.endCardIDList.addAll(thirdDaoCardInfo.showCardIDList);
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
		
		daoCardInfo = c_9_WuTong(cardIDList,value2CardInfo, handJokerCardIDList);
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
		
		String cardName = "";
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		String stringValue = new String();
		
		srcCardIDList = cardIDList;
		showCardIDList = cardIDList;
		
		this.pokerManager.sortCardIDListMaxA(showCardIDList, false);
		stringValue = getPokerStringValueArray(showCardIDList);
		
		cardName = String.format(CardName_WuLong, stringValue);		
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = srcCardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		
		return daoCardInfo;
	}
	
	//获取一对牌型
	//@para cardIDList:玩家手中牌的id队列	value2CardInfo:手牌中，不包含鬼牌的，其余牌的hashtable	handJokerCardIDList:手牌中鬼牌ID队列
	public SSSDaoCardInfo c_1_YiDui(List<Integer> cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
		
		List<Integer> leftPokerIDList = new ArrayList<>(cardIDList);
		List<Integer> showCardIDList = new ArrayList<>();
		
		PokerGroupCardInfo pokerGroupCardInfo = getSameMaxIDList(value2CardInfo, handJokerCardIDList, 2, true);
		
		if (pokerGroupCardInfo == null) {
			return null;
		}
		showCardIDList.addAll(pokerGroupCardInfo.showCardIDList);
		this.removeCardByIDList(leftPokerIDList, pokerGroupCardInfo.srcCardIDList);
		
		this.pokerManager.sortCardIDListMaxA(leftPokerIDList, false);
		showCardIDList.addAll(leftPokerIDList);
		
		stringValue = getPokerStringValueArray(showCardIDList);
		cardName = String.format(CardName_YiDui, stringValue);
		
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		return daoCardInfo;
	}
	
	//获取2对
	public SSSDaoCardInfo c_2_LiangDui(List<Integer> cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
		List<Integer> leftPokerIDList = new ArrayList<>(cardIDList);
		List<Integer> leftJokerIDList = new ArrayList<>(handJokerCardIDList);
		List<Integer> showCardIDList = new ArrayList<>();
		
		PokerGroupCardInfo firstPokerGroupCardInfo = getSameMaxIDList(value2CardInfo, leftJokerIDList, 2, true);
		if(firstPokerGroupCardInfo == null)
		{
			return null;
		}
		showCardIDList.addAll(firstPokerGroupCardInfo.showCardIDList);
		
		this.removeCardByIDList(leftPokerIDList, firstPokerGroupCardInfo.showCardIDList);
		this.removeCardByIDList(leftJokerIDList, firstPokerGroupCardInfo.srcCardIDList);
		
		HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(leftPokerIDList, allJokerIDList);
		PokerGroupCardInfo secondPokerGroupInfo = getSameMaxIDList(handPokerInfo.value2CardInfo, leftJokerIDList, 2, true);
		if (secondPokerGroupInfo == null) {
			return null;
		}
		
		showCardIDList.addAll(secondPokerGroupInfo.showCardIDList);
		this.removeCardByIDList(leftPokerIDList, secondPokerGroupInfo.srcCardIDList);
		
		showCardIDList.addAll(leftPokerIDList);
		stringValue = getPokerStringValueArray(showCardIDList);
		cardName = String.format(CardName_LiangDui, stringValue);
		
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		return daoCardInfo;
		
	}
	
	//获取3条,头道是3尖刀
	public SSSDaoCardInfo c_3_SanTiao(List<Integer> cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
		List<Integer> showCardIDList = new ArrayList<>();
		
		List<Integer> leftPokerIDList = new ArrayList<>(cardIDList);
		List<Integer> leftJokerIDList = new ArrayList<>(handJokerCardIDList);
		
		PokerGroupCardInfo pokerGroupCardInfo = getSameMaxIDList(value2CardInfo, leftJokerIDList, 3, true);
		if (pokerGroupCardInfo == null) {
			return null;
		}
		this.removeCardByIDList(leftPokerIDList, pokerGroupCardInfo.srcCardIDList);
		this.removeCardByIDList(leftJokerIDList, pokerGroupCardInfo.srcCardIDList);
		showCardIDList.addAll(pokerGroupCardInfo.showCardIDList);
		//加入剩余的2张牌
		this.pokerManager.sortCardIDListMaxA(leftPokerIDList, false);
		showCardIDList.addAll(leftPokerIDList);
		stringValue = getPokerStringValueArray(showCardIDList);
		cardName = String.format(CardName_SanTiao, stringValue);
		
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		return daoCardInfo;	
	}
	
	//获取顺子
	public SSSDaoCardInfo c_4_ShunZi(List<Integer> cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		if( value2CardInfo.size() + handJokerCardIDList.size() < 5)
		{
			return null;
		}
		
		String cardName = "";
		String stringValue = new String();
		
		
		List<Integer> showCardIDList = new ArrayList<>();
		
		List<Integer> leftPokerIDList = new ArrayList<>(cardIDList);
		List<Integer> leftJokerIDList = new ArrayList<>(handJokerCardIDList);
		
		List<Integer> keyList = new ArrayList<>(value2CardInfo.keySet());
		List<HandPokerValueInfo> handPokerValueList = new ArrayList<>(value2CardInfo.values());
		int maxCardValue = Collections.max(keyList);
		//最大值大于 5，A不可能当做1，则将A作为14
		if( maxCardValue > 5 && value2CardInfo.containsKey(1))
		{
			for(HandPokerValueInfo temp : handPokerValueList)
				if(temp.value == 1)
					temp.value = 14;
		}
		//将手牌按牌面值升序排列
		handPokerValueList.sort(( first, second) ->
		{
			return first.value - second.value;
		});
		//补足空档
		PokerGroupCardInfo midPokerGroupCardInfo = getMidShunzi(handPokerValueList, handJokerCardIDList);
		if (midPokerGroupCardInfo == null) {
			return null;
		}
		
		showCardIDList.addAll(midPokerGroupCardInfo.showCardIDList);
		
		this.removeCardByIDList(leftPokerIDList, midPokerGroupCardInfo.srcCardIDList);
		this.removeCardByIDList(leftJokerIDList, midPokerGroupCardInfo.srcCardIDList);
		
		//向右增长
		int preValue = showCardIDList.get(showCardIDList.size()-1)%100;
		if(preValue == 1 )
		{
			preValue = 14;
		}
		PokerGroupCardInfo rightPokerGroupCardInfo = getRightShunzi(showCardIDList, leftJokerIDList,preValue);
		if( rightPokerGroupCardInfo != null)
		{
			showCardIDList.addAll(rightPokerGroupCardInfo.showCardIDList);
			this.removeCardByIDList(leftPokerIDList, rightPokerGroupCardInfo.srcCardIDList);
			this.removeCardByIDList(leftJokerIDList, rightPokerGroupCardInfo.srcCardIDList);
		}
		
		
		
		//向左增长
		int nextValue = showCardIDList.get(0)%100;
		PokerGroupCardInfo leftPokerGroupCardInfo = getLeftShunzi(showCardIDList, leftJokerIDList, nextValue);
		if( leftPokerGroupCardInfo != null)
		{
			
			for(int id : leftPokerGroupCardInfo.showCardIDList)
			{
				showCardIDList.add(0,id);
			}
		}
		if(showCardIDList.size() < 5)
		{
			return null;
		}
		
		stringValue = getPokerStringValueArray(showCardIDList);
		String tempString = new String();
		if( stringValue.endsWith("5"))
		{
			tempString = "1"+"5";
		}
		else if( stringValue.endsWith("e"))
		{
			tempString = "1"+"e";
		}
		else 
		{
			tempString = "0"+stringValue.substring(4);
		}
		cardName =	String.format(CardName_ShunZi, tempString);
			
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		return daoCardInfo;	
	}
	
	//获取同花  
	public SSSDaoCardInfo c_5_TongHua(List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
		List<Integer> showCardIDList = new ArrayList<>();
		if( color2CardInfo.size() > 1)
		{
			return null;
		}
		//同花三条
		daoCardInfo = c_3_SanTiao(cardIDList, value2CardInfo, handJokerCardIDList);
		if( daoCardInfo != null)
		{
			stringValue = daoCardInfo.cardName.substring(4);
			cardName = String.format(CardName_TongHuaSanTiao, stringValue);
			daoCardInfo.cardName = cardName;
			return daoCardInfo;
		}
		//同花两对
		daoCardInfo = c_2_LiangDui(cardIDList, value2CardInfo, handJokerCardIDList, allJokerIDList);
		if ( daoCardInfo != null) {
			stringValue = daoCardInfo.cardName.substring(4);
			cardName = String.format(CardName_TongHuaLiangDui, stringValue);
			daoCardInfo.cardName = cardName;
			return daoCardInfo;
		}
		//同花一对
		daoCardInfo = c_1_YiDui(cardIDList, value2CardInfo, handJokerCardIDList);
		if ( daoCardInfo !=null) {
			stringValue = daoCardInfo.cardName.substring(4);
			cardName = String.format(CardName_TongHuaYiDui, stringValue);
			daoCardInfo.cardName = cardName;
			return daoCardInfo;
		}
		//普通同花
		showCardIDList = new ArrayList<>(cardIDList);
		this.pokerManager.sortCardIDListMaxA(showCardIDList, false);
		stringValue = getPokerStringValueArray(showCardIDList);
		cardName = String.format(CardName_TongHua, stringValue);
		
		daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		return daoCardInfo;
		
		
	}
	
	//获取葫芦
	public SSSDaoCardInfo c_6_HuLu(List<Integer> cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
		
		List<Integer> leftPokerIDList = new ArrayList<>(cardIDList);
		List<Integer> leftJokerIDList = new ArrayList<>(handJokerCardIDList);
		PokerGroupCardInfo firstPokerGroupCardInfo = getSameMaxIDList(value2CardInfo, leftJokerIDList, 3, true);
		if (firstPokerGroupCardInfo == null) {
			return null;
		}
		this.removeCardByIDList(leftPokerIDList, firstPokerGroupCardInfo.srcCardIDList);
		this.removeCardByIDList(leftJokerIDList, firstPokerGroupCardInfo.srcCardIDList);

		List<Integer> showCardIDList = firstPokerGroupCardInfo.showCardIDList;
		
		HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(leftPokerIDList, allJokerIDList);
		PokerGroupCardInfo secondPokerGroupCardInfo = getSameMaxIDList(handPokerInfo.value2CardInfo, leftJokerIDList, 2, true);
		if(secondPokerGroupCardInfo == null)
		{
			return null;
		}
		showCardIDList.addAll(secondPokerGroupCardInfo.showCardIDList);
		stringValue = getPokerStringValueArray(showCardIDList);
		cardName = String.format(CardName_HuLu, stringValue);
		
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		return daoCardInfo;
	}
	
	//获取手牌中最大的4条
	public SSSDaoCardInfo c_7_TieZhi(List<Integer> cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
		List<Integer> leftPokerIDList = new ArrayList<>(cardIDList);
		List<Integer> leftJokerIDList = new ArrayList<>(handJokerCardIDList);
		
		PokerGroupCardInfo pokerGroupCardInfo = getSameMaxIDList(value2CardInfo, leftJokerIDList, 4, true);
		if( pokerGroupCardInfo == null)
		{
			return null;
		}
		this.removeCardByIDList(leftPokerIDList, pokerGroupCardInfo.srcCardIDList);
		this.removeCardByIDList(leftJokerIDList, pokerGroupCardInfo.srcCardIDList);
		//添加剩余的一张牌
		pokerGroupCardInfo.srcCardIDList.addAll(leftPokerIDList);
		pokerGroupCardInfo.showCardIDList.addAll(leftPokerIDList);
		
		stringValue = getPokerStringValueArray(pokerGroupCardInfo.showCardIDList);
		cardName = String.format(CardName_TieZhi, stringValue);
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = pokerGroupCardInfo.srcCardIDList;
		daoCardInfo.showCardIDList = pokerGroupCardInfo.showCardIDList;
		
		return daoCardInfo;
	}
	//同花顺
	public SSSDaoCardInfo c_8_TongHuaShun(List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
		List<Integer> showCardIDList = new ArrayList<>();
		
		//超过一种花色 || 出现相同大小的牌 ，不是同花顺
		if(color2CardInfo.size() > 1 || value2CardInfo.size() + handJokerCardIDList.size() < 5)
		{
			return null;
		}
		daoCardInfo = c_4_ShunZi(cardIDList, value2CardInfo, handJokerCardIDList);
		if( daoCardInfo != null)
		{
			stringValue = daoCardInfo.cardName.substring(4);
			cardName = String.format(CardName_TongHuaShun, stringValue);			
			daoCardInfo.cardName = cardName;
			return daoCardInfo;	
		}else{
			return null;
		}	
	}
	
	//获取手牌中最大的5条
	public SSSDaoCardInfo c_9_WuTong(List<Integer> cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
	
		PokerGroupCardInfo pokerGroupCardInfo = getSameMaxIDList(value2CardInfo, handJokerCardIDList, 5, true);
		if( pokerGroupCardInfo == null)
		{
			return null;
		}
		stringValue = getPokerStringValueArray(pokerGroupCardInfo.showCardIDList);	
		cardName = String.format(CardName_WuTong, stringValue);
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = pokerGroupCardInfo.showCardIDList;
		return daoCardInfo;
	}

	/**
	 * @param value2CardInfo
	 * @param handJokerCardIDList
	 * @param findCardCount
	 * @param isFindMax
	 * @return
	 */
	public PokerGroupCardInfo getSameMaxIDList(Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, int findCardCount, boolean isFindMax){
		List<Integer> valueList = new ArrayList<>(value2CardInfo.keySet());
		int count = valueList.size();
		if(count == 0){
			return null;
		}
		
		this.pokerManager.sortCardValueList(valueList);
		
		//如果是找最小的,倒序
		if(!isFindMax){
			Collections.reverse(valueList);
		}
		
		PokerGroupCardInfo groupCardInfo = null;
		
		//从大到小查询满足条件的id列表 A最大
		for(int index=0; index < count; index++){
			int value = valueList.get(index);
			HandPokerValueInfo valuePokerInfo = value2CardInfo.get(value);
			groupCardInfo = this.getSameValuePokerGroupCardInfo(valuePokerInfo, handJokerCardIDList, findCardCount);
			if(groupCardInfo != null){
				return groupCardInfo;
			}
		}
		
		return null;
	}
	
	
	//查找指定数量的 相同value的 卡牌信息
	public PokerGroupCardInfo getSameValuePokerGroupCardInfo(HandPokerValueInfo valuePokerInfo, List<Integer> handJokerCardIDList, int findCardCount){
		
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		
		int jokerCount = handJokerCardIDList.size();
		
		
		List<Integer> cardIDList = valuePokerInfo.cardIDList;
		
		if(cardIDList.size() >= findCardCount){
			srcCardIDList = new ArrayList<>(cardIDList.subList(0, findCardCount));
			showCardIDList = new ArrayList<>(srcCardIDList);
		}
		else if(cardIDList.size() + jokerCount >= findCardCount){
			
			srcCardIDList = new ArrayList<>(cardIDList);
			showCardIDList = new ArrayList<>(cardIDList);
			int needCount = findCardCount - cardIDList.size();
			
			for(int index_2=0; index_2<needCount; index_2++){
				int jokerCardID = handJokerCardIDList.get(index_2);
				
				srcCardIDList.add(jokerCardID);
				//追加鬼牌替换的牌
				showCardIDList.add(cardIDList.get(0));
			}
		}
		//如果没有找到
		if(srcCardIDList.size() == 0){
			return null;
		}
		PokerGroupCardInfo groupCardInfo = new PokerGroupCardInfo();
		groupCardInfo.srcCardIDList = srcCardIDList;
		groupCardInfo.showCardIDList = showCardIDList;
		return groupCardInfo;
		
	}
	public Boolean removeCardByIDList( List<Integer> srcCardIDList, List<Integer> toBeRemovedCardIDList){
		Boolean isRemoved = false;		
		for (Integer Id : toBeRemovedCardIDList) {
			if (srcCardIDList.contains(Id)) {
				srcCardIDList.remove(Id);
				isRemoved = true;
			}
			
		}
		
		return isRemoved;
	}
	
	/** 获取扑克的stringValue
	 * @param cardIDList
	 * @return string  扑克牌的string
	 */
	public String getPokerStringValueArray(List<Integer> cardIDList)
	{
		String stringValue = new String();
		
		
		for(int id : cardIDList)
		{
			Poker poker = this.pokerManager.getPokerByCardID(id);
			stringValue += poker.getStringValue();
		}
		return stringValue;
	}
	
	public PokerGroupCardInfo getMidShunzi(List<HandPokerValueInfo> handPokerValueList, List<Integer> handJokerCardIDList){
		PokerGroupCardInfo pokerGroupCardInfo = new PokerGroupCardInfo();
		
		int color = handPokerValueList.get(0).cardIDList.get(0)/100;
		int preValue = handPokerValueList.get(0).value;
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		
		srcCardIDList.addAll(handPokerValueList.get(0).cardIDList);
		showCardIDList.addAll(handPokerValueList.get(0).cardIDList);
		int usedJoker = 0;
		int jokerCount = handJokerCardIDList.size();
		
		for( int index = 1; index < handPokerValueList.size(); )
		{	
			int currentValue = handPokerValueList.get(index).value;
			int currentID = handPokerValueList.get(index).cardIDList.get(0);
			//当前牌无法与前一张牌连起来
			if( currentValue - preValue > 1)
			{
				if( usedJoker < jokerCount)//鬼牌数量充足，让鬼牌替代缺少的牌
				{
					int jokerID = handJokerCardIDList.get(usedJoker);
					if( preValue == 13) //若前一张牌是K,补上A
					{
						showCardIDList.add(color *100 +1);
						
					}else
					{
						showCardIDList.add(color *100 +preValue+1);
					}
				
					srcCardIDList.add(jokerID);
					usedJoker ++;
					preValue ++;
				}else //鬼牌数量不足，不是顺子
				{
					return null;
				}
			}else //当前牌可以和前一张牌连起来
			{	
				srcCardIDList.add(currentID);
				showCardIDList.add(currentID);
				preValue ++;
				index ++;
			}
		}
		pokerGroupCardInfo.srcCardIDList = srcCardIDList;
		pokerGroupCardInfo.showCardIDList = showCardIDList;
		return pokerGroupCardInfo;
		
	
		
	}
	public PokerGroupCardInfo getRightShunzi(List<Integer> currentShunziCardIDList, List<Integer> handJokerCardIDList,int preValue){
		PokerGroupCardInfo pokerGroupCardInfo = new PokerGroupCardInfo();
		
		int usedJoker = 0;
		int jokerCount = handJokerCardIDList.size();
		int color = currentShunziCardIDList.get(0)/100;
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		for(int index = currentShunziCardIDList.size(); index < 5; index++)
		{
			if( preValue != 14 && preValue != 5)
			{
				if( usedJoker < jokerCount )
				{
					int jokerID = handJokerCardIDList.get(usedJoker);
					if( preValue == 13) //若前一张牌是K,补上A
					{
						showCardIDList.add(color *100 +1);
					}else
					{
						showCardIDList.add(color *100 +preValue+1);
					}
					srcCardIDList.add(jokerID);
					usedJoker ++;
					preValue ++;	
				}else
				{
					return null;
				}
			}else
			{
				break;
			}
		}
		if (srcCardIDList.size() == 0) {
			return null;
		}
		pokerGroupCardInfo.srcCardIDList = srcCardIDList;
		pokerGroupCardInfo.showCardIDList = showCardIDList;
		return pokerGroupCardInfo;
	}
	public PokerGroupCardInfo getLeftShunzi(List<Integer> currentShunziCardIDList, List<Integer> handJokerCardIDList, int nextValueValue){
		PokerGroupCardInfo pokerGroupCardInfo = new PokerGroupCardInfo();
		
		int usedJoker = 0;
		int jokerCount = handJokerCardIDList.size();
		int color = currentShunziCardIDList.get(0)/100;
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		
		for( int index = currentShunziCardIDList.size(); index < 5; index++)
		{
			if( usedJoker < jokerCount)
			{
				int jokerID = handJokerCardIDList.get(usedJoker);
				showCardIDList.add(color * 100 + nextValueValue -1);
				srcCardIDList.add(jokerID);
				nextValueValue--;
				usedJoker++;
			}else
			{
				return null;
			}
			
		}
		if( srcCardIDList.size() == 0)
		{
			return null;
		}
		pokerGroupCardInfo.srcCardIDList = srcCardIDList;
		pokerGroupCardInfo.showCardIDList = showCardIDList;
		return pokerGroupCardInfo;
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
	
	
	//获取顺子的字符串形式  showCardIDList：默认顺序是  A K Q J 10，...2    
	public String getShunZiSSSDaoStringValue(List<Integer> showCardIDList) {

		List<Integer> tempShowIDList = new ArrayList<>(showCardIDList);

		int lastCardID = tempShowIDList.get(showCardIDList.size() - 1);
		Poker lastPoker = this.pokerManager.getPokerByCardID(lastCardID);

		// 如果是5,4,3,2,1 是第2大,把1放到第一位
		if (lastPoker.value == 1) {
			tempShowIDList.remove(showCardIDList.size() - 1);
			tempShowIDList.add(0, lastCardID);
		}

		return this.getSSSDaoStringValue(tempShowIDList);
	}

	// 获取某到牌的 字符串形式
	// cardIDList:指定牌组,排序过的牌
	
	public String getSSSDaoStringValue(List<Integer> cardIDList) {
		String ret = "";
		int count = cardIDList.size();
		for (int index = 0; index < count; index++) {
			int cardID = cardIDList.get(index);

			// 如果传递0 是移除2,3,4,5后山寨替换的一张cardID
			if (cardID == 0) {
				ret += "0";
				continue;
			}
			Poker poker = this.pokerManager.getPokerByCardID(cardID);
			ret += poker.getStringValue();
		}
		return ret;
	}
}
