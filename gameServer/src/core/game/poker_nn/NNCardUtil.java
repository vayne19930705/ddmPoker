package core.game.poker_nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import business.global.log.BaseLog;
import business.global.poker.Poker;
import business.global.poker.PokerManager;

public class NNCardUtil extends BaseLog{
	
	//特殊牌名-牌面值("1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "s", "w")_maxStringValueCardID
	
	//各种牌型对应牌型名字符串:
	
	//五小牛 s_x_n
	public String CardName_FiveLittleNiu = "s_x_n";
	//四炸
	public String CardName_BoomNiu = "s_t_n";
	//五花牛
	public String CardName_FlowerNiu = "s_h_n";
	//牛牛
	public String CardName_NiuNiu = "c_a_n";
	//牛9-牛1 c_9_n- c_1_n
	public String CardName_NiuPoint = "c_%s_n";
	//无牛
	public String CardName_NotNiu = "c_0_n";
	
	private static NNCardUtil instance = new NNCardUtil();
	
	public PokerManager pokerManager = null;

	protected ArrayList<Integer> allJokerCardIDList = new ArrayList<>(); //所有鬼牌
	
	public static NNCardUtil getInstance(){
		return instance;
	}
	
	public NNCardUtil(){
		this.init();
	}
	
	public String getLogString(){
		return "[NNCardUtil]\t";
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
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(110,111,112,113,213));
		
		//TODO:获取手牌是牛几
		NNPointCardInfo cardInfo = this.getCardInfo(handCardIDList, this.allJokerCardIDList);
		
		this.info(":{} 结果:{},{}", handCardIDList, cardInfo.cardName, cardInfo.endCardIDList);
	}
	
	
	//获取手牌结果
	public NNPointCardInfo getCardInfo(List<Integer> cardIDList, List<Integer> jokerIDList){
		
		NNPointCardInfo cardInfo = new NNPointCardInfo();
		
		if(cardIDList.size() != 5){
			this.error("getCardInfo cardIDList:{} error", cardIDList);
			return cardInfo;
		}
		//拷贝一份
		List<Integer> allCardIDList = new ArrayList<>(cardIDList);
		allCardIDList.sort((x,y)->{return y-x;});
		
		List<Poker> pokerList = this.pokerManager.getPokerList(allCardIDList);
		
		
		//是否是5小牛
		cardInfo = this.s1_FiveLittleNiu(pokerList, allCardIDList, jokerIDList);
		if(cardInfo != null){
			return cardInfo;
		}
		
		//是否是 四炸
		cardInfo = this.s2_BoomNiu(pokerList, allCardIDList, jokerIDList);
		if(cardInfo != null){
			return cardInfo;
		}
		
		
		//五花牛
		cardInfo = this.s3_FlowerNiu(pokerList, allCardIDList, jokerIDList);
		if(cardInfo != null){
			return cardInfo;
		}
		
		//获取牌型可能的点数
		cardInfo = this.c1_point(pokerList, allCardIDList, jokerIDList);
		
		return cardInfo;
	}
	
	
	
	
	//五小牛
	public NNPointCardInfo s1_FiveLittleNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList){
		
		NNPointCardInfo cardInfo = new NNPointCardInfo();

		return null;
	}
	
	//四炸
	public NNPointCardInfo s2_BoomNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList){
		
		NNPointCardInfo cardInfo = new NNPointCardInfo();

		return cardInfo;
		
	}
	
	//五花牛
	public NNPointCardInfo s3_FlowerNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList){
		
		
		NNPointCardInfo cardInfo = new NNPointCardInfo();

		return cardInfo;
	}
	
	
	//获取牌型可能的点数
	public NNPointCardInfo c1_point(List<Poker> cards,  List<Integer> allCardIDList, List<Integer> jokerIDList){

		NNPointCardInfo cardInfo = new NNPointCardInfo();

		return cardInfo;
		
	}
	
}
