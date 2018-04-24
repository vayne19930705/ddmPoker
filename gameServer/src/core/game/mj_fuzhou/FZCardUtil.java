package core.game.mj_fuzhou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import business.global.mj.MJBaseUtil;
import business.global.mjcard.MJCard;
import business.global.mjhandcard.MJHandCardInfo;
import business.global.mjhandcard.MJHandCard_7DuiHuInfo;
import business.global.mjhandcard.MJHandCard_HuInfo;

public class FZCardUtil extends MJBaseUtil{
	private static FZCardUtil instance = new FZCardUtil();
	
	//�ǻ��Ŀ���ID�ֵ�
	private Hashtable<Integer, MJCard> huaMJCardInfo = new Hashtable<>();
	
	//�ǻ��Ŀ���TypeValue�б�
	private List<Integer> canUseCardTypeValueList = new ArrayList<>();
	
	//һ�������齫������cardID�б�
	private List<Integer> allCardIDList = new ArrayList<>();

	public static FZCardUtil getInstance(){
		return instance;
	}
	
	public FZCardUtil(){
		this.init();
	}
	
	public String getLogString(){
		return "[QZCardUtil]\t";
	}
	
	public void init(){
		//��ʼ�����ֵ�
		this.huaMJCardInfo = this.cardMgr.getFuZhouMJAllHuaCardInfo();
		
		this.initCanUseCard();
	}
	
	public void initCanUseCard(){
		Hashtable<Integer, MJCard> allCardDict = this.cardMgr.getFuZhouMJAllCardInfo();
		
		//����һ���齫
		this.allCardIDList = new ArrayList<>(allCardDict.keySet());
		
		int count = allCardIDList.size();
		for(int index=0; index<count; index++){
			int cardID = allCardIDList.get(index);
			
			if(this.huaMJCardInfo.containsKey(cardID)){
				continue;
			}
			
			int cardTypeValue = allCardDict.get(cardID).cardTypeValue;
			if(this.canUseCardTypeValueList.contains(cardTypeValue)){
				continue;
			}
			this.canUseCardTypeValueList.add(cardTypeValue);
		}
		
		Collections.sort(this.canUseCardTypeValueList);
		this.info("canUseCardTypeValueList:{}", this.canUseCardTypeValueList);
	}
	
	//��ȡ���л������ֵ�
	public Hashtable<Integer, MJCard> getAllHuaMJCardInfo(){
		return this.huaMJCardInfo;
	}
	
	//��ȡ���зǻ���cardTypeValue
	public List<Integer> getAllCanUseCardTypeValueList(){
		return this.canUseCardTypeValueList;
	}
	
	//����cardID�б�
	public List<Integer> getAllowCardIDList(){
		//����һ��
		return new ArrayList<>(this.allCardIDList);
	}
	
	//-------------���---------------
	
	public void onStart(){
		
		this.huTest();
		
		this.tingTest();
		
		this.bestOutTest();
	}
	
	public void huTest(){
		//�������
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(1102, 1103, 1401, 1501, 1601, 1701, 1801, 1901, 2101, 2202, 2301, 2402, 2503, 2604));
		//����
		int cardID = 1104;
		
		//��ȡ���н��ƶ���
		Hashtable<Integer, MJCard> jokerMJCardInfo = new Hashtable<>(this.getAllJokerMJCardInfoByCardID(cardID));
		
		//TODO:������ƽ��
		MJHandCardInfo handCardInfo = this.matchHandCardInfo(handCardIDList, jokerMJCardInfo);
		
		//-----���������Ϣ-----
		List<MJHandCard_HuInfo> allHuInfoList = handCardInfo.allHuInfoList;
		int huCount = allHuInfoList.size();
		
		if(huCount > 0){
			for(int index=0; index<huCount; index++){
				MJHandCard_HuInfo huInfo = allHuInfoList.get(index);
				this.info("index:{} huEndCardIDList:{},huGroupCardIDList:{}", index, huInfo.huEndCardIDList, huInfo.huGroupCardIDList);
			}
		}
		else{
			Collections.sort(handCardIDList);
			this.info("���ܺ�:{}", handCardIDList);
		}
	}
	
	public void tingTest(){
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(1102, 2102, 2102, 2701, 2704, 2801, 2804, 2901, 2904, 3101, 3103, 3303, 3304));
		
		int cardID = 1101;
		Hashtable<Integer, MJCard> jokerMJCardInfo = this.getAllJokerMJCardInfoByCardID(cardID);
		
		List<Integer> huCardTypeValueList = this.getHuCardTypeValueList(handCardIDList, jokerMJCardInfo);
		
		this.info("tingTest:{}", huCardTypeValueList);
	}
	
	public void bestOutTest(){
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(1304,1101,3802,3902,3704));
		
		int cardID = 1301;
		Hashtable<Integer, MJCard> jokerMJCardInfo = this.getAllJokerMJCardInfoByCardID(cardID);
		
		Hashtable<Integer, List<Integer>> bestOutCardIDInfo = this.getBestOutCardIDList(handCardIDList, jokerMJCardInfo);
		
		this.info("bestOutTest:{}", bestOutCardIDInfo);
	}
	
	//--------------------------------�����㷨------------------------------
	
	//��ȡһ���� ���п��ܵĺ�����Ϣ
	public MJHandCardInfo matchHandCardInfo(List<Integer> handCardIDList, Hashtable<Integer, MJCard> jokerMJCardInfo){
		
		//�������ƶ���
		MJHandCardInfo handCardInfo = this.getHandCardInfo(handCardIDList, jokerMJCardInfo);
		
		//TODO:�����㷨
		
		
		return handCardInfo;
	}
		
	
	//--------------------����---------------------------
	//��ȡ����cardIDList ���п��Ժ���CardTypeValue
	public List<Integer> getHuCardTypeValueList(List<Integer> cardIDList, Hashtable<Integer, MJCard> jokerMJCardInfo){
		
		List<Integer> huCardTypeValueList = new ArrayList<>();

		return huCardTypeValueList;
	}
	
	//--------------------���ų���---------------------------
	
	//��ȡ���� cardIDList ���ƺ���Խ������ƵĿ�����Ϣ
	public Hashtable<Integer, List<Integer>> getBestOutCardIDList(List<Integer> cardIDList, Hashtable<Integer, MJCard> jokerMJCardInfo){
		
		//����:����ID�б� ��Ϣ�ֵ�
		Hashtable<Integer, List<Integer>> bestOutCardInfo = new Hashtable<>();

		
		return bestOutCardInfo;
	}
}
