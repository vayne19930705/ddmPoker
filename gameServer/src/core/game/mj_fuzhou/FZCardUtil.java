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
	
	//是花的卡牌ID字典
	private Hashtable<Integer, MJCard> huaMJCardInfo = new Hashtable<>();
	
	//非花的卡牌TypeValue列表
	private List<Integer> canUseCardTypeValueList = new ArrayList<>();
	
	//一副福州麻将的所有cardID列表
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
		//初始化花字典
		this.huaMJCardInfo = this.cardMgr.getFuZhouMJAllHuaCardInfo();
		
		this.initCanUseCard();
	}
	
	public void initCanUseCard(){
		Hashtable<Integer, MJCard> allCardDict = this.cardMgr.getFuZhouMJAllCardInfo();
		
		//生成一副麻将
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
	
	//获取所有花数据字典
	public Hashtable<Integer, MJCard> getAllHuaMJCardInfo(){
		return this.huaMJCardInfo;
	}
	
	//获取所有非花的cardTypeValue
	public List<Integer> getAllCanUseCardTypeValueList(){
		return this.canUseCardTypeValueList;
	}
	
	//所有cardID列表
	public List<Integer> getAllowCardIDList(){
		//拷贝一份
		return new ArrayList<>(this.allCardIDList);
	}
	
	//-------------入口---------------
	
	public void onStart(){
		
		this.huTest();
		
		this.tingTest();
		
		this.bestOutTest();
	}
	
	public void huTest(){
		//玩家手牌
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(1102, 1103, 1401, 1501, 1601, 1701, 1801, 1901, 2101, 2202, 2301, 2402, 2503, 2604));
		//开金
		int cardID = 1104;
		
		//获取所有金牌对象
		Hashtable<Integer, MJCard> jokerMJCardInfo = new Hashtable<>(this.getAllJokerMJCardInfoByCardID(cardID));
		
		//TODO:计算胡牌结果
		MJHandCardInfo handCardInfo = this.matchHandCardInfo(handCardIDList, jokerMJCardInfo);
		
		//-----输出胡牌信息-----
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
			this.info("不能胡:{}", handCardIDList);
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
	
	//--------------------------------胡牌算法------------------------------
	
	//获取一副牌 所有可能的胡牌信息
	public MJHandCardInfo matchHandCardInfo(List<Integer> handCardIDList, Hashtable<Integer, MJCard> jokerMJCardInfo){
		
		//生成手牌对象
		MJHandCardInfo handCardInfo = this.getHandCardInfo(handCardIDList, jokerMJCardInfo);
		
		//TODO:胡牌算法
		
		
		return handCardInfo;
	}
		
	
	//--------------------听牌---------------------------
	//获取手牌cardIDList 所有可以胡的CardTypeValue
	public List<Integer> getHuCardTypeValueList(List<Integer> cardIDList, Hashtable<Integer, MJCard> jokerMJCardInfo){
		
		List<Integer> huCardTypeValueList = new ArrayList<>();

		return huCardTypeValueList;
	}
	
	//--------------------最优出牌---------------------------
	
	//获取手牌 cardIDList 出牌后可以进入听牌的卡牌信息
	public Hashtable<Integer, List<Integer>> getBestOutCardIDList(List<Integer> cardIDList, Hashtable<Integer, MJCard> jokerMJCardInfo){
		
		//出牌:听牌ID列表 信息字典
		Hashtable<Integer, List<Integer>> bestOutCardInfo = new Hashtable<>();

		
		return bestOutCardInfo;
	}
}
