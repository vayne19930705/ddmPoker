package core.game.poker_sss;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.omg.CORBA.PUBLIC_MEMBER;


import business.global.log.BaseLog;
import business.global.poker.HandPokerColorInfo;
import business.global.poker.HandPokerInfo;
import business.global.poker.HandPokerValueInfo;
import business.global.poker.Poker;
import business.global.poker.PokerGroupCardInfo;
import business.global.poker.PokerManager;


public class SSSPokerCardUtil extends BaseLog{
	
	//��ͬ
	public String CardName_LiuTong = "c_a";
	//��ͬ     %sΪ�Ƶ�stringֵ
	public String CardName_WuTong = "c_9_%s";
	//ͬ��˳ 
	public String CardName_TongHuaShun = "c_8_%s";
	//��֧
	public String CardName_TieZhi = "c_7_%s";
	//��«  
	public String CardName_HuLu = "c_6_%s";
	//ͬ��3��
	public String CardName_TongHuaSanTiao = "c_5_3_%s";
	//ͬ��2��
	public String CardName_TongHuaLiangDui = "c_5_2_%s";
	//ͬ��1��
	public String CardName_TongHuaYiDui = "c_5_1_%s";
	//��ͨͬ��  %s Ϊ�Ƶĵ���
	public String CardName_TongHua = "c_5_0_%s";
	//˳�� 
	public String CardName_ShunZi = "c_4_%s";
	
	//3��3��
	public String CardName_SanTiao3Joker = "c_3_3";
	//2��3��
	public String CardName_SanTiao2Joker = "c_3_2";
	//1��3�� ���� ����3��
	public String CardName_SanTiao1Joker = "c_3_1";
	//����
	public String CardName_SanTiao = "c_3_%s";
	
	//���� 
	public String CardName_LiangDui = "c_2_%s";
	//һ�� 
	public String CardName_YiDui = "c_1_%s";
	//���� %s ��ʾ�ܵĵ���string
	public String CardName_WuLong = "c_0_%s";
	
	

	public String CardName_ZhiZunQingLong = "s_19";//����������A��K��һɫ˳�ӡ�
	public String CardName_YiTiaoLong = "s_18";//һ������A��K˳�ӡ�
	public String CardName_SanTongHuaShun = "s_17";//��ͬ��˳
	public String CardName_SanFenTianXia = "s_16";//�������£�������֧�����ⵥ��
	public String CardName_SiTaoSanTiao = "s_15";//����������ӵ������������
	
	public String CardName_SanHuangWuDi = "s_14";//�������
	public String CardName_ShiErHuangZu = "s_13";//ʮ������ 
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
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(110,312,311,113,112,211,310,201,105,106,407,308,209));
		
		SSSSpecailCardInfo cardInfo = this.getSpecailCardInfo(handCardIDList, this.allJokerCardIDList,1);
		
		if(cardInfo == null){
			this.info("��������");
		}
		else{
			this.info(":{} ���:{},{}", handCardIDList, cardInfo.cardName, cardInfo.cardIDList);
		}
			
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

	//------------------------------��ͨ����---------------------------------
	//��ȡ��������
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
	
	//��ȡһ������
	//@para cardIDList:��������Ƶ�id����	value2CardInfo:�����У����������Ƶģ������Ƶ�hashtable	handJokerCardIDList:�����й���ID����
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
	
	//��ȡ2��
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
	
	//��ȡ3��,ͷ����3�⵶
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
		//����ʣ���2����
		if(leftPokerIDList.size() != 0)
		{
			this.pokerManager.sortCardIDListMaxA(leftPokerIDList, false);
			showCardIDList.addAll(leftPokerIDList);
		}
		stringValue = getPokerStringValueArray(showCardIDList);
		cardName = String.format(CardName_SanTiao, stringValue);
		
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = cardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		return daoCardInfo;	
	}
	
	//��ȡ˳��
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
		//���ֵ���� 5��A�����ܵ���1����A��Ϊ14
		if( maxCardValue > 5 && value2CardInfo.containsKey(1))
		{
			for(HandPokerValueInfo temp : handPokerValueList)
				if(temp.value == 1)
					temp.value = 14;
		}
		//�����ư�����ֵ��������
		handPokerValueList.sort(( first, second) ->
		{
			return first.value - second.value;
		});
		//����յ�
		PokerGroupCardInfo midPokerGroupCardInfo = getMidShunzi(handPokerValueList, handJokerCardIDList);
		if (midPokerGroupCardInfo == null) {
			return null;
		}
		
		showCardIDList.addAll(midPokerGroupCardInfo.showCardIDList);
		
		this.removeCardByIDList(leftPokerIDList, midPokerGroupCardInfo.srcCardIDList);
		this.removeCardByIDList(leftJokerIDList, midPokerGroupCardInfo.srcCardIDList);
		
		//��������
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
		
		
		
		//��������
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
	
	//��ȡͬ��  
	public SSSDaoCardInfo c_5_TongHua(List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
		List<Integer> showCardIDList = new ArrayList<>();
		if( color2CardInfo.size() > 1)
		{
			return null;
		}
		//ͬ������
		daoCardInfo = c_3_SanTiao(cardIDList, value2CardInfo, handJokerCardIDList);
		if( daoCardInfo != null)
		{
			stringValue = daoCardInfo.cardName.substring(4);
			cardName = String.format(CardName_TongHuaSanTiao, stringValue);
			daoCardInfo.cardName = cardName;
			return daoCardInfo;
		}
		//ͬ������
		daoCardInfo = c_2_LiangDui(cardIDList, value2CardInfo, handJokerCardIDList, allJokerIDList);
		if ( daoCardInfo != null) {
			stringValue = daoCardInfo.cardName.substring(4);
			cardName = String.format(CardName_TongHuaLiangDui, stringValue);
			daoCardInfo.cardName = cardName;
			return daoCardInfo;
		}
		//ͬ��һ��
		daoCardInfo = c_1_YiDui(cardIDList, value2CardInfo, handJokerCardIDList);
		if ( daoCardInfo !=null) {
			stringValue = daoCardInfo.cardName.substring(4);
			cardName = String.format(CardName_TongHuaYiDui, stringValue);
			daoCardInfo.cardName = cardName;
			return daoCardInfo;
		}
		//��ͨͬ��
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
	
	//��ȡ��«
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
	
	//��ȡ����������4��
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
		//���ʣ���һ����
		pokerGroupCardInfo.srcCardIDList.addAll(leftPokerIDList);
		pokerGroupCardInfo.showCardIDList.addAll(leftPokerIDList);
		
		stringValue = getPokerStringValueArray(pokerGroupCardInfo.showCardIDList);
		cardName = String.format(CardName_TieZhi, stringValue);
		daoCardInfo.cardName = cardName;
		daoCardInfo.srcCardIDList = pokerGroupCardInfo.srcCardIDList;
		daoCardInfo.showCardIDList = pokerGroupCardInfo.showCardIDList;
		
		return daoCardInfo;
	}
	//ͬ��˳
	public SSSDaoCardInfo c_8_TongHuaShun(List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		String cardName = "";
		String stringValue = new String();
		List<Integer> showCardIDList = new ArrayList<>();
		
		//����һ�ֻ�ɫ || ������ͬ��С���� ������ͬ��˳
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
	
	//��ȡ����������5��
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
		
		//���������С��,����
		if(!isFindMax){
			Collections.reverse(valueList);
		}
		
		PokerGroupCardInfo groupCardInfo = null;
		
		//�Ӵ�С��ѯ����������id�б� A���
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
	
	
	//����ָ�������� ��ͬvalue�� ������Ϣ
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
			
			for(int index_2 = 0; index_2 < needCount; index_2++){
				int jokerCardID = handJokerCardIDList.get(index_2);
				
				srcCardIDList.add(jokerCardID);
				//׷�ӹ����滻����
				showCardIDList.add(cardIDList.get(0));
			}
		}
		//���û���ҵ�
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
	
	/** ��ȡ�˿˵�stringValue
	 * @param cardIDList
	 * @return string  �˿��Ƶ�string
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
			//��ǰ���޷���ǰһ����������
			if( currentValue - preValue > 1)
			{
				if( usedJoker < jokerCount)//�����������㣬�ù������ȱ�ٵ���
				{
					int jokerID = handJokerCardIDList.get(usedJoker);
					if( preValue == 13) //��ǰһ������K,����A
					{
						showCardIDList.add(color *100 +1);
						
					}else
					{
						showCardIDList.add(color *100 +preValue+1);
					}
				
					srcCardIDList.add(jokerID);
					usedJoker ++;
					preValue ++;
				}else //�����������㣬����˳��
				{
					return null;
				}
			}else //��ǰ�ƿ��Ժ�ǰһ����������
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
					if( preValue == 13) //��ǰһ������K,����A
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
//		
//		specailCardInfo =  s_05_LiuLiuDaShun(point, handCardIDList, color2CardInfo, value2CardInfo);	
//		if(specailCardInfo != null){
//			return specailCardInfo;
//		}
		
//		specailCardInfo =  s_04_BeiDouQiXing(point, handCardIDList, color2CardInfo, value2CardInfo);	
//		if(specailCardInfo != null){
//			return specailCardInfo;
//		}
		
		specailCardInfo =  s_03_BaXianGuoHai(point, handCardIDList, color2CardInfo, value2CardInfo);	
		
		return specailCardInfo;
	}
	
	//1.	����������A��K��һɫ˳�ӡ�104��
	public SSSSpecailCardInfo s_19_ZhiZunQingLong(int point, List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		if( color2CardInfo.size() != 1)
		{
			return null;
		}
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		PokerGroupCardInfo pokerGroupCardInfo = this.isShunZi(leftHandCardIDList,true);
		if( pokerGroupCardInfo != null)
		{
			sssSpecailCardInfo.cardName = CardName_ZhiZunQingLong;
			this.pokerManager.sortCardIDListMaxK(pokerGroupCardInfo.showCardIDList, true);
			sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
			return sssSpecailCardInfo;
		}
		
		return null;
	}
	
	//2.	һ������A��K˳�ӡ�52��
	public SSSSpecailCardInfo s_18_YiTiaoLong(int point, List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		PokerGroupCardInfo pokerGroupCardInfo = this.isShunZi(leftHandCardIDList,false);
		if( pokerGroupCardInfo != null)
		{
			sssSpecailCardInfo.cardName = CardName_YiTiaoLong;
			this.pokerManager.sortCardIDListMaxK(pokerGroupCardInfo.showCardIDList, true);
			sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
			return sssSpecailCardInfo;
		}
		
		return null;
	}
	
	//3.	��ͬ��˳��ͷ�����е���β���Կ����ͬ��˳���͡�   26��
	public SSSSpecailCardInfo s_17_SanTongHuaShun(int point, List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> showCardIDList = new ArrayList<>();
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		
		PokerGroupCardInfo pokerGroupCardInfo = this.getShunZi(leftHandCardIDList, true);
		
		if( pokerGroupCardInfo != null )
		{
			Collections.reverse(showCardIDList);
			sssSpecailCardInfo.cardName = CardName_SanTongHuaShun;
			sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
			return sssSpecailCardInfo;
		}
		
		return null;
		
	}
	
	//4.	�������£�������֧�����ⵥ�ơ�26��
	public SSSSpecailCardInfo s_16_SanFenTianXia(int point, List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		PokerGroupCardInfo pokerGroupCardInfo = this.getMaxSameValueIDList(cardIDList, 3, 4, true);
		if( pokerGroupCardInfo == null)
		{
			return null;
		}
		
		this.removeCardByIDList(leftHandCardIDList, pokerGroupCardInfo.srcCardIDList);
		pokerGroupCardInfo.srcCardIDList.addAll(leftHandCardIDList);
		pokerGroupCardInfo.showCardIDList.addAll(leftHandCardIDList);
		
		sssSpecailCardInfo.cardName = CardName_SanFenTianXia;
		sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
		
		return sssSpecailCardInfo;
	}
	
	//10.	����������ӵ������������Ӯÿ��6�֡�
	public SSSSpecailCardInfo s_15_SiTaoSanTiao(int point, List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		
		PokerGroupCardInfo pokerGroupCardInfo = this.getMaxSameValueIDList(leftHandCardIDList, 4, 3, true);
		if( pokerGroupCardInfo == null)
		{
			return null;
		}
		
		this.removeCardByIDList(leftHandCardIDList, pokerGroupCardInfo.srcCardIDList);
		pokerGroupCardInfo.srcCardIDList.addAll(leftHandCardIDList);
		pokerGroupCardInfo.showCardIDList.addAll(leftHandCardIDList);
		
		sssSpecailCardInfo.cardName = CardName_SiTaoSanTiao;
		sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
		
		return sssSpecailCardInfo;
		
	}
	
	//2��5ͬ��1������
	public SSSSpecailCardInfo s_14_SanHuangWuDi(int point, List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		List<Integer> showCardIDList = new ArrayList<>();
		PokerGroupCardInfo firstPokerGroupCardInfo = this.getMaxSameValueIDList(leftHandCardIDList, 2, 5, true);
		if( firstPokerGroupCardInfo == null)
		{
			return null;
		}
		
		this.removeCardByIDList(leftHandCardIDList, firstPokerGroupCardInfo.srcCardIDList);
		showCardIDList.addAll(firstPokerGroupCardInfo.showCardIDList);
		PokerGroupCardInfo secondPokerGroupCardInfo = this.getMaxSameValueIDList(leftHandCardIDList, 1, 3, true);
		if (secondPokerGroupCardInfo == null) {
			return null;
		}
		
		showCardIDList.addAll(secondPokerGroupCardInfo.showCardIDList);
	
		sssSpecailCardInfo.cardName = CardName_SanHuangWuDi;
		sssSpecailCardInfo.cardIDList = showCardIDList;
		
		return sssSpecailCardInfo;
		
	}
	
	//13�Ŷ���J-Q-K-A��Ӯÿ��24�֡�
	public SSSSpecailCardInfo s_13_ShiErHuangZu(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		PokerGroupCardInfo pokerGroupCardInfo = this.getValueInRange(leftHandCardIDList, 12, 11, 1, true);
		if( pokerGroupCardInfo != null)
		{
			sssSpecailCardInfo.cardIDList.addAll(pokerGroupCardInfo.showCardIDList);
			this.removeCardByIDList(leftHandCardIDList, pokerGroupCardInfo.srcCardIDList);
			sssSpecailCardInfo.cardIDList.addAll(leftHandCardIDList);
			sssSpecailCardInfo.cardName = CardName_ShiErHuangZu;
			return sssSpecailCardInfo;
		}
		return null;
	}
	
	//ʮ���������ֶ�Ϊ8~A��Ӯÿ��10�֡�
	public SSSSpecailCardInfo s_12_QuanDa(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		PokerGroupCardInfo pokerGroupCardInfo = this.getValueInRange(leftHandCardIDList, 13, 8, 1, true);
		if( pokerGroupCardInfo != null)
		{
			sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
			sssSpecailCardInfo.cardName = CardName_QuanDa;
			return sssSpecailCardInfo;
		}
		return null;
	}
	
	//ʮ���������ֶ�Ϊ2~8��Ӯÿ��10�֡�
	public SSSSpecailCardInfo s_11_QuanXiao(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		PokerGroupCardInfo pokerGroupCardInfo = this.getValueInRange(leftHandCardIDList, 13, 2, 8, true);
		if( pokerGroupCardInfo != null)
		{
			sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
			sssSpecailCardInfo.cardName = CardName_QuanXiao;
			return sssSpecailCardInfo;
		}
		return null;
	}

	//9.	��һɫ��ʮ�����ƶ��Ǻ�ɫ������/���ģ����ɫ��÷��/���ң���Ӯÿ��10�֡�
	public SSSSpecailCardInfo s_10_CouYiSe(int point, List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		PokerGroupCardInfo pokerGroupCardInfo = this.getSameColorCardIDList(leftHandCardIDList, 1, 13, 2);
		
		if( pokerGroupCardInfo != null)
		{System.out.println("11");
			sssSpecailCardInfo.cardName = CardName_CouYiSe;
			sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
			return sssSpecailCardInfo;
		}
		
		return null;
	}
	
	//14.	��ͬ����ͷ�����е���β���Կ����ͬ�����ͣ�Ӯÿ��5�֡�
	public SSSSpecailCardInfo s_08_SanTongHua(int point, List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		List<Integer> showCardIDList = new ArrayList<>();
		PokerGroupCardInfo firstPokerGroupCardInfo = this.getSameColorCardIDList(leftHandCardIDList, 2, 5, 4);
		if( firstPokerGroupCardInfo != null)
		{
			showCardIDList.addAll(firstPokerGroupCardInfo.showCardIDList);
		}
		
		//�ڵ���getSameColorCardIDList()ʱ��leftHandCardIDList�Ѿ������¹�
		PokerGroupCardInfo secondPokerGroupCardInfo = this.getSameColorCardIDList(leftHandCardIDList, 1, 3, 4);
		
		if( secondPokerGroupCardInfo != null)
		{
			showCardIDList.addAll(secondPokerGroupCardInfo.showCardIDList);
		}
		if(showCardIDList.size() == 13)
		{
			sssSpecailCardInfo.cardName = CardName_SanTongHua;
			Collections.reverse(showCardIDList);
			sssSpecailCardInfo.cardIDList = showCardIDList;
			return sssSpecailCardInfo;
		}
		return null;
	}

	//11.	��Գ�����ӵ��5�����Ӻ�һ��������Ӯÿ��5�֡�
	public SSSSpecailCardInfo s_09_WuDuiChongSan(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		List<Integer> showCardIDList = new ArrayList<>();
		PokerGroupCardInfo firstPokerGroupCardInfo = this.getMaxSameValueIDList(leftHandCardIDList, 1, 3, true);
		if( firstPokerGroupCardInfo == null)
		{
			return null;
		}
		
		this.removeCardByIDList(leftHandCardIDList, firstPokerGroupCardInfo.srcCardIDList);
		showCardIDList.addAll(firstPokerGroupCardInfo.showCardIDList);
		
		PokerGroupCardInfo secondPokerGroupCardInfo = this.getMaxSameValueIDList(leftHandCardIDList, 5, 2, true);
		if (secondPokerGroupCardInfo == null) {
			return null;
		}
		
		showCardIDList.addAll(secondPokerGroupCardInfo.showCardIDList);
		Collections.reverse(showCardIDList);
		sssSpecailCardInfo.cardName = CardName_WuDuiChongSan;
		sssSpecailCardInfo.cardIDList = showCardIDList;
		
		return sssSpecailCardInfo;
	}
	
	//13.	��˳�ӣ�ͷ�����е���β���Կ����˳�����ͣ�Ӯÿ��5�֡�
	public SSSSpecailCardInfo s_07_SanShunZi(int point, List<Integer>cardIDList){
        SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> showCardIDList = new ArrayList<>();
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		
		PokerGroupCardInfo pokerGroupCardInfo = this.getShunZi(leftHandCardIDList, false);
		
		if( pokerGroupCardInfo != null )
		{
			Collections.reverse(showCardIDList);
			sssSpecailCardInfo.cardName = CardName_SanShunZi;
			sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
			return sssSpecailCardInfo;
		}		
		return null;	
	}
	

		
	//12.	���԰룺13������ӵ��6�Ŷ��ƣ�Ӯÿ��5�֡�
	public SSSSpecailCardInfo s_06_LiuDuiBan(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		PokerGroupCardInfo pokerGroupCardInfo = this.getMaxSameValueIDList(cardIDList, 6, 2, true);
		if( pokerGroupCardInfo == null)
		{
			return null;
		}
		
		this.removeCardByIDList(leftHandCardIDList, pokerGroupCardInfo.srcCardIDList);
		pokerGroupCardInfo.srcCardIDList.addAll(leftHandCardIDList);
		pokerGroupCardInfo.showCardIDList.addAll(leftHandCardIDList);
		
		sssSpecailCardInfo.cardName = CardName_LiuDuiBan;
		sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
		
		return sssSpecailCardInfo;
	}
	
	//6.	������˳����6��һ�����ƣ�Ӯÿ��20�֡�
	public SSSSpecailCardInfo s_05_LiuLiuDaShun(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		
		PokerGroupCardInfo pokerGroupCardInfo = this.getMaxSameValueIDList(cardIDList, 1, 6, true);
		if( pokerGroupCardInfo == null)
		{
			return null;
		}
		
		this.removeCardByIDList(leftHandCardIDList, pokerGroupCardInfo.srcCardIDList);
		this.pokerManager.sortCardIDListMaxA(leftHandCardIDList, false);
		pokerGroupCardInfo.srcCardIDList.addAll(leftHandCardIDList);
		pokerGroupCardInfo.showCardIDList.addAll(leftHandCardIDList);
		
		sssSpecailCardInfo.cardName = CardName_LiuLiuDaShun;
		sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
		
		return sssSpecailCardInfo;
		
	}
	
	//6.	�������ǣ���7��һ�����ƣ�Ӯÿ��20�֡�
	public SSSSpecailCardInfo s_04_BeiDouQiXing(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		
		PokerGroupCardInfo pokerGroupCardInfo = this.getMaxSameValueIDList(cardIDList, 1, 7, true);
		if( pokerGroupCardInfo == null)
		{
			return null;
		}
		
		this.removeCardByIDList(leftHandCardIDList, pokerGroupCardInfo.srcCardIDList);
		this.pokerManager.sortCardIDListMaxA(leftHandCardIDList, false);
		pokerGroupCardInfo.srcCardIDList.addAll(leftHandCardIDList);
		pokerGroupCardInfo.showCardIDList.addAll(leftHandCardIDList);
		
		sssSpecailCardInfo.cardName = CardName_BeiDouQiXing;
		sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
		
		return sssSpecailCardInfo;
	}
	
	//6.	���ɹ�������8��һ�����ƣ�Ӯÿ��20�֡�
	public SSSSpecailCardInfo s_03_BaXianGuoHai(int point, List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo){		
		SSSSpecailCardInfo sssSpecailCardInfo = new SSSSpecailCardInfo();
		
		List<Integer> leftHandCardIDList = new ArrayList<>(cardIDList);
		
		PokerGroupCardInfo pokerGroupCardInfo = this.getMaxSameValueIDList(cardIDList, 1, 8, true);
		if( pokerGroupCardInfo == null)
		{
			return null;
		}
		
		this.removeCardByIDList(leftHandCardIDList, pokerGroupCardInfo.srcCardIDList);
		this.pokerManager.sortCardIDListMaxA(leftHandCardIDList, false);
		pokerGroupCardInfo.srcCardIDList.addAll(leftHandCardIDList);
		pokerGroupCardInfo.showCardIDList.addAll(leftHandCardIDList);
		
		sssSpecailCardInfo.cardName = CardName_BaXianGuoHai;
		sssSpecailCardInfo.cardIDList = pokerGroupCardInfo.showCardIDList;
		
		return sssSpecailCardInfo;
	}
	
	public PokerGroupCardInfo getMaxSameValueIDList(List<Integer> handCardIDList, int groups, int numberOfEachGroup, boolean isFindMax)
	{
		PokerGroupCardInfo pokerGroupCardInfo = new PokerGroupCardInfo();
				
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();	
		//����һ��handCardIDList
		List<Integer> leftCardIDList = new ArrayList<>(handCardIDList);
		//Ѱ��groups�飬ÿ��numberOfEachGroup����
		for( int group = 0; group < groups; group++)
		{
			HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(leftCardIDList, new ArrayList<>());
			Hashtable<Integer, HandPokerValueInfo> value2CardInfo = handPokerInfo.value2CardInfo;
			
			List<Integer> valueList = new ArrayList<>(value2CardInfo.keySet());
			int valueCount = valueList.size();
			if (valueCount == 0 ) {
				return null;
			}
	
			this.pokerManager.sortCardValueList(valueList);
			//�������С����ת����
			if( !isFindMax )
			{
				Collections.reverse(valueList);
			}
			
			for(int index = 0; index < valueCount; index++)
			{
				int value = valueList.get(index);
				HandPokerValueInfo handPokerValueInfo = value2CardInfo.get(value);
				PokerGroupCardInfo tempPokerGroupCardInfo = this.getSameValuePokerGroupCardInfo(handPokerValueInfo, new ArrayList<>(), numberOfEachGroup);
				
				if( tempPokerGroupCardInfo != null)
				{
					srcCardIDList.addAll(tempPokerGroupCardInfo.srcCardIDList);
					showCardIDList.addAll(tempPokerGroupCardInfo.showCardIDList);
					//this.pokerManager.sortCardIDListMaxA(showCardIDList, false);
					//�Ƴ��Ѿ�����������
					this.removeCardByIDList(leftCardIDList, tempPokerGroupCardInfo.srcCardIDList);
					break; //��ѡ�����ʵ�һ�飬����forѭ��
				}
			}
		}
		if( showCardIDList.size() == groups * numberOfEachGroup)
		{
			pokerGroupCardInfo.srcCardIDList = srcCardIDList;
			pokerGroupCardInfo.showCardIDList = showCardIDList;
			return pokerGroupCardInfo;
		}
		
		return null;
	}
	
	public PokerGroupCardInfo getValueInRange(List<Integer> handCardIDList,int need ,int leftBound,int rightBound,boolean maxIsA){
		PokerGroupCardInfo pokerGroupCardInfo = new PokerGroupCardInfo();
		
		HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(handCardIDList, new ArrayList<>());
		Hashtable<Integer, HandPokerValueInfo> value2CardInfo = handPokerInfo.value2CardInfo;
		
		List<Integer> valueList = new ArrayList<>(value2CardInfo.keySet());
		int valueCount = valueList.size();
		
		if(valueCount == 0)
		{
			return null;
		}
		
		this.pokerManager.sortCardValueList(valueList);
		//��������ΪA�����ұ߽���A�����ұ߽��Ϊ14
		if( maxIsA && rightBound == 1)
		{
			rightBound = 14;
		}
		int count = 0;
		for( int index = 0 ; index < valueCount; index++)
		{
			 int value = valueList.get(index);
			
			 HandPokerValueInfo handPokerValueInfo = value2CardInfo.get(value);
			 if (maxIsA && value == 1) 
			 {
				 value = 14;
			 }
			 
			 if( value >= leftBound && value <= rightBound )
			 {				 
				for( int cardID : handPokerValueInfo.cardIDList)
				{
					if (count < need )
					{ 
						pokerGroupCardInfo.srcCardIDList.add(cardID);
						pokerGroupCardInfo.showCardIDList.add(cardID);
						count++; 
					}else
					{
						break;
					}
				}	 
			 }
			 if( count >= need)
			 {
				 break;
			 }
		}
		if( pokerGroupCardInfo.srcCardIDList.size() < need)
		{
			return null;
		}
		
		this.pokerManager.sortCardIDListMaxA(pokerGroupCardInfo.showCardIDList, false);
		
		return pokerGroupCardInfo;
		
	}
	
	public PokerGroupCardInfo getSameColorCardIDList(List<Integer> handCardIDList, int groups, int numberOfEachGroup, int kindOfColor)
	{
		PokerGroupCardInfo pokerGroupCardInfo = new PokerGroupCardInfo();
		
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		
		for(int group = 0; group < groups; group++)
		{
			HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(handCardIDList, new ArrayList<>());
			Hashtable<Integer, HandPokerColorInfo> tempColor2CardInfo = handPokerInfo.color2CardInfo;
			
			Hashtable<Integer, HandPokerColorInfo> color2CardInfo = new Hashtable<>();
			//��Ҫ�󰴺�ڷ��࣬�����µ�hashtable������ɫ�ơ���ɫ�ƺϲ�
			if( kindOfColor == 2)
			{
				color2CardInfo = this.mergePokerByRedOrBlack(tempColor2CardInfo);
			}
			else
			{
				color2CardInfo = new Hashtable<>(tempColor2CardInfo);
			}
			
			List<Integer> colorList = new ArrayList<>(color2CardInfo.keySet());
			int colorCount = colorList.size();	
			if (colorCount == 0) {
				return null;
			}
			
			for( int index = 0; index < colorCount; index++)
			{
				int color = colorList.get(index);
				
				HandPokerColorInfo handPokerColorInfo = color2CardInfo.get(color); 
				
				
				PokerGroupCardInfo tempPokerGroupCardInfo = this.getSameColorPokerGroupInfo(handPokerColorInfo, numberOfEachGroup);
				if( tempPokerGroupCardInfo != null)
				{
					srcCardIDList.addAll(tempPokerGroupCardInfo.srcCardIDList);
					this.pokerManager.sortCardIDListMaxA(tempPokerGroupCardInfo.showCardIDList, false);
					showCardIDList.addAll(tempPokerGroupCardInfo.showCardIDList);
					//�Ƴ��Ѿ�����������
					this.removeCardByIDList(handCardIDList, tempPokerGroupCardInfo.srcCardIDList);
					break; //��ѡ�����ʵ�һ�飬����forѭ��
				}
			}
		}
		
		if( showCardIDList.size() == groups * numberOfEachGroup)
		{
			pokerGroupCardInfo.srcCardIDList = srcCardIDList;
			pokerGroupCardInfo.showCardIDList = showCardIDList;
			return pokerGroupCardInfo;
		}
	
		return null;
	}
	public Hashtable<Integer, HandPokerColorInfo> mergePokerByRedOrBlack(Hashtable<Integer, HandPokerColorInfo> color2CardInfo)
	{
		Hashtable<Integer, HandPokerColorInfo> tempColor2CardInfo = new Hashtable<>();
		
		HandPokerColorInfo tempHandPokerColorInfo1 = new HandPokerColorInfo();
		tempHandPokerColorInfo1.color = 1;
		HandPokerColorInfo tempHandPokerColorInfo2 = new HandPokerColorInfo();
		tempHandPokerColorInfo2.color = 2;
		for(Iterator<Integer> iterator = color2CardInfo.keySet().iterator(); iterator.hasNext();)
		{
			int tempColor = iterator.next();
			if( tempColor%2 == 1)
			{
				tempHandPokerColorInfo1.cardIDList.addAll(color2CardInfo.get(tempColor).cardIDList);
			}else
			{
				tempHandPokerColorInfo2.cardIDList.addAll(color2CardInfo.get(tempColor).cardIDList);
			}
		}
		tempColor2CardInfo.put(1, tempHandPokerColorInfo1);
		tempColor2CardInfo.put(2, tempHandPokerColorInfo2);
		
		if( tempColor2CardInfo != null)
		{
			return tempColor2CardInfo;
		}
		return null;
	}
	//Ѱ��ָ����������ͬ��ɫ����
	public PokerGroupCardInfo getSameColorPokerGroupInfo(HandPokerColorInfo colorPokerInfo,int findCount )
	{
		PokerGroupCardInfo pokerGroupCardInfo = new PokerGroupCardInfo();
		
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		if( colorPokerInfo == null)
		{
			return null;
		}
		int count = 0;
		for(int cardID : colorPokerInfo.cardIDList)
		{
			if( count < findCount)
			{
				srcCardIDList.add(cardID);
				showCardIDList.add(cardID);
				count++;
			}else
			{
				break;
			}
			
		}
		if(showCardIDList.size() == findCount)
		{
			pokerGroupCardInfo.srcCardIDList = srcCardIDList;
			pokerGroupCardInfo.showCardIDList = showCardIDList;
			return pokerGroupCardInfo;
		}
		
		return null;
	}
	public PokerGroupCardInfo getShunZi(List<Integer> handCardIDList,boolean needTongHua)
	{
		//����
		handCardIDList.sort((first,second) ->
		{
			return first%100 -second%100;
		});
		//���ѡȡ5����
		Hashtable<Integer, List<Integer>> firstSelected = new Hashtable<>();
		firstSelected = select5CardID(handCardIDList);
		
		for(Iterator<Integer> iterator = firstSelected.keySet().iterator(); iterator.hasNext();)
		{
			List<Integer> firstSelectedCardList = firstSelected.get(iterator.next());

			PokerGroupCardInfo firstPokerGroupCardInfo = this.isShunZi(firstSelectedCardList,needTongHua);
			if( firstPokerGroupCardInfo !=null)
			{
				//�Ƴ��ѽ��ճ�˳�ӵ�cardID
				List<Integer> leftHandCardIDList = new ArrayList<>(handCardIDList);
				this.removeCardByIDList(leftHandCardIDList, firstSelectedCardList);
				//��ѡ�ڶ���˳��
				Hashtable<Integer, List<Integer>> secondSelected = new Hashtable<>();
				secondSelected = select5CardID(leftHandCardIDList);
				
				for (Iterator iterator2 = secondSelected.keySet().iterator(); iterator2.hasNext();) 
				{
					List<Integer> secondSelectedCardList = secondSelected.get(iterator2.next());
					
					PokerGroupCardInfo secondPokerGroupCardInfo = this.isShunZi(secondSelectedCardList, needTongHua);
					if (secondPokerGroupCardInfo != null) 
					{
						this.removeCardByIDList(leftHandCardIDList, secondSelectedCardList);
						
						PokerGroupCardInfo thirdPokerGroupCardInfo = this.isShunZi(leftHandCardIDList, needTongHua);
						
						if( thirdPokerGroupCardInfo != null )
						{
							PokerGroupCardInfo pokerGroupCardInfo = new PokerGroupCardInfo();
							pokerGroupCardInfo.showCardIDList.addAll(firstPokerGroupCardInfo.showCardIDList);
							pokerGroupCardInfo.showCardIDList.addAll(secondPokerGroupCardInfo.showCardIDList);
							pokerGroupCardInfo.showCardIDList.addAll(thirdPokerGroupCardInfo.showCardIDList);
							
							return pokerGroupCardInfo;
						}
					}
					
				}	
			}
		}
		
		return null;
	}
	public Hashtable<Integer, List<Integer> > select5CardID(List<Integer> handCardIDList)
	{
		Hashtable<Integer, List<Integer> > selected = new Hashtable<>();	
		
		int cardCount = handCardIDList.size();
		int count = 0;
		for(int index1 = 0; index1 < cardCount; index1++ )
		{
			for(int index2 = index1+1; index2 < cardCount ; index2++)
			{
				for(int index3 = index2+1; index3 < cardCount; index3++)
				{
					for(int index4 = index3+1; index4 < cardCount; index4++)
					{
						for( int index5 = index4+1; index5 < cardCount; index5++)
						{
							List<Integer> selectedList = new ArrayList<>();
							selectedList.add(handCardIDList.get(index1));
							selectedList.add(handCardIDList.get(index2));
							selectedList.add(handCardIDList.get(index3));
							selectedList.add(handCardIDList.get(index4));
							selectedList.add(handCardIDList.get(index5));
							selected.put(count++, selectedList);
						}
					}
				}
			}
		}

		return selected;
	}
	public PokerGroupCardInfo isShunZi(List<Integer> handCardIDList,boolean needTongHua)
	{
		if( needTongHua)
		{
			HandPokerInfo handPokerInfo = this.pokerManager.getHandInfo(handCardIDList, new ArrayList<>());
			Hashtable<Integer, HandPokerColorInfo> color2CardInfo = handPokerInfo.color2CardInfo;
			if(color2CardInfo.size() != 1)
			{
				return null;
			}			
		}
		List<Poker> pokerList = new ArrayList<>();
		pokerList = this.pokerManager.getPokerList(handCardIDList);
		
		pokerList.sort(( first, second) ->
		{
			return second.value - first.value;
		});
		
		int pokerCount = pokerList.size();
		for (int index = 0; index < pokerCount-1; index++) 
		{
			int currentValue = pokerList.get(index).value;
			int nextValue = pokerList.get(index+1).value;
			if( currentValue - nextValue !=1)
			{
				//��������ΪK���ұȽϵ���ΪA�����Ծ���Ϊ˳�ӣ��������αȽϣ�������˳��
				if( pokerList.get(0).value == 13 && nextValue == 1)
				{
					continue;
				}else
				{
					return null;
				}
			}
		}
		PokerGroupCardInfo pokerGroupCardInfo = new PokerGroupCardInfo();
		pokerGroupCardInfo.srcCardIDList = handCardIDList;
		pokerGroupCardInfo.showCardIDList = handCardIDList;
		return pokerGroupCardInfo;
	}
	//��ȡ˳�ӵ��ַ�����ʽ  showCardIDList��Ĭ��˳����  A K Q J 10��...2    
	public String getShunZiSSSDaoStringValue(List<Integer> showCardIDList) {

		List<Integer> tempShowIDList = new ArrayList<>(showCardIDList);

		int lastCardID = tempShowIDList.get(showCardIDList.size() - 1);
		Poker lastPoker = this.pokerManager.getPokerByCardID(lastCardID);

		// �����5,4,3,2,1 �ǵ�2��,��1�ŵ���һλ
		if (lastPoker.value == 1) {
			tempShowIDList.remove(showCardIDList.size() - 1);
			tempShowIDList.add(0, lastCardID);
		}

		return this.getSSSDaoStringValue(tempShowIDList);
	}

	// ��ȡĳ���Ƶ� �ַ�����ʽ
	// cardIDList:ָ������,���������
	
	public String getSSSDaoStringValue(List<Integer> cardIDList) {
		String ret = "";
		int count = cardIDList.size();
		for (int index = 0; index < count; index++) {
			int cardID = cardIDList.get(index);

			// �������0 ���Ƴ�2,3,4,5��ɽկ�滻��һ��cardID
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
