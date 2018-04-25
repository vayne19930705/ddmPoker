package core.game.poker_nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import business.global.log.BaseLog;
import business.global.poker.Poker;
import business.global.poker.PokerManager;
import sun.net.www.content.audio.x_aiff;

public class NNCardUtil extends BaseLog {

	// 特殊牌名-牌面值("1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
	// "s", "w")_maxStringValueCardID

	// 各种牌型对应牌型名字符串:（字符Unicode编码越大，其优先级越高）
	// 第一位:表示特殊牌还是普通牌（s：特殊牌 c：普通牌） 
	// 第二位:表示牌型的优先级（特殊牌：z：五小牛 y：炸弹牛 x：五花牛      普通牌：z：牛牛  1-9：牛X 0：无牛 ）
	// 第三位：表示最大单张牌面值（1-9 ：1-9 a-d：10-K s：小鬼  w：大鬼）
	// 第四位：表示最大单张的花色（0:大鬼	0：小鬼	4：黑桃	3：红桃	2：梅花	1：方块）
	// 单张牌面+花色 用一个%s输入
	
	// 五小牛
	public String CardName_FiveLittleNiu = "sz%s";
	// 四炸
	public String CardName_BoomNiu = "sy%s";
	// 五花牛
	public String CardName_FlowerNiu = "sx%s";
	// 牛牛
	public String CardName_NiuNiu = "cz%s";
	// 牛x
	public String CardName_NiuPoint = "c%s%s";
	// 无牛
	public String CardName_NotNiu = "c0%s";
 
	private static NNCardUtil instance = new NNCardUtil();

	public PokerManager pokerManager = null;

	protected ArrayList<Integer> allJokerCardIDList = new ArrayList<>(); // 所有鬼牌

	public static NNCardUtil getInstance() {
		return instance;
	}

	public NNCardUtil() {
		this.init();
	}

	public String getLogString() {
		return "[NNCardUtil]\t";
	}

	public void init() {
		this.pokerManager = PokerManager.getInstance();

		// 初始化鬼牌
		ArrayList<Integer> jokerCardIDList = pokerManager.getJokerCardIDList();

		this.allJokerCardIDList.addAll(jokerCardIDList);
	}

	// -------------入口---------------
	// 外部调用入口
	public void onStart() {

		// 玩家手牌5张
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(110, 111, 112, 113, 213));

		// TODO:获取手牌是牛几
		NNPointCardInfo cardInfo = this.getCardInfo(handCardIDList, this.allJokerCardIDList);

		this.info(":{} 结果:{},{}", handCardIDList, cardInfo.cardName, cardInfo.endCardIDList);
	}

	// 获取手牌结果
	public NNPointCardInfo getCardInfo(List<Integer> cardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();

		if (cardIDList.size() != 5) {
			this.error("getCardInfo cardIDList:{} error", cardIDList);
			return cardInfo;
		}
		// 拷贝一份
		List<Integer> allCardIDList = new ArrayList<>(cardIDList);
		allCardIDList.sort((x, y) -> {
			return y - x;
		});

		List<Poker> pokerList = this.pokerManager.getPokerList(allCardIDList);

		// 是否是5小牛
		cardInfo = this.s1_FiveLittleNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// 是否是 四炸
		cardInfo = this.s2_BoomNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// 五花牛
		cardInfo = this.s3_FlowerNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// 获取牌型可能的点数
		cardInfo = this.c1_point(pokerList, allCardIDList, jokerIDList);

		return cardInfo;
	}

	// 五小牛
	public NNPointCardInfo s1_FiveLittleNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		int sumPoint = 0;
		Boolean isLessThanFive = true;
		Boolean isFiveLittleNiu = false;
		String maxStringValueColor = "";
		for (Poker poker : cards) {
			if( poker.stringValueColor.compareTo(maxStringValueColor) > 0 )
			{
				maxStringValueColor = poker.stringValueColor;
			}
			if (jokerIDList.contains(poker.id)) {
				sumPoint++; // 此时，默认鬼牌牌面值为1
				continue;
			} else if (poker.value < 5) // 牌面值小于5，计入总和，跳过本次循环
			{
				sumPoint += poker.value;
			} else // 牌面值大于5，且不是鬼牌，判定无法构成五小牛,跳出循环
			{
				isLessThanFive = false;
				break;
			}
		}

		if (isLessThanFive) {
			if (sumPoint > 10)
				isFiveLittleNiu = false;
			else
				isFiveLittleNiu = true;
		}

		if (isFiveLittleNiu) // 如果是五小牛，返回牌的类型信息 & 排序后的ID信息
		{
			String cardName = String.format(CardName_FiveLittleNiu, maxStringValueColor);
			cardInfo.cardName = cardName;
			List<Integer> sortedCardIDList = new ArrayList<>();
			sortedCardIDList = getSortedCardIDList(cards);
			cardInfo.endCardIDList.addAll(sortedCardIDList);
		}else{
			cardInfo = null;
		}

		return cardInfo;
	}

	// 四炸
	public NNPointCardInfo s2_BoomNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		
		Hashtable<Integer, List<Integer>> sumSameHashTable = new Hashtable<>();
		List<Integer> existJokerList = new ArrayList<>();
		List<Integer> endCardIDList = new ArrayList<>();
		String maxStringValueColor = "";
		
		for( Poker poker : cards)
		{
			if( poker.stringValueColor.compareTo(maxStringValueColor) >0 ) //获取单张最大牌面值及花色
			{
				maxStringValueColor = poker.stringValueColor;
			}
			if( jokerIDList.contains( poker.id ) )
			{
				existJokerList.add(poker.id);
				continue;
			}
			if( !sumSameHashTable.contains(poker.value))
			{
				sumSameHashTable.put(poker.value , new ArrayList<>());
			}
			
			List<Integer> cardIDList = sumSameHashTable.get(poker.value);
			cardIDList.add(poker.id);
		}
		
		for(List<Integer> cardIDList : sumSameHashTable.values())
		{
			if( cardIDList.size() + existJokerList.size() >= 4)
			{
				//直接排序
				cardIDList.sort(( x , y )-> 
				{
					return y-x;
				});
				endCardIDList.addAll(cardIDList);
				
				//加入鬼牌（如果有的话）
				existJokerList.sort(( x, y)->
				{
					return y-x;
				}); 
				endCardIDList.addAll(existJokerList);
				break;
			}
		}
		
		//如果是炸弹牛，追加剩余牌，并设置返回信息，否则，设置返回值为null
		if( endCardIDList.size() == 0)
		{
			endCardIDList = null;
		}else
		{
			for(int id : allCardIDList)
			{
				if(!endCardIDList.contains(id))
				{
					endCardIDList.add(id);
				}
			}
			
			String cardName = String.format(CardName_BoomNiu, maxStringValueColor);
			cardInfo.cardName = cardName;
			cardInfo.endCardIDList.addAll(endCardIDList);
		}
		
		return cardInfo;

	}

	// 五花牛
	public NNPointCardInfo s3_FlowerNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		Boolean isFlowerNiu = true;
		String maxStringValueColor = "";

		for (Poker poker : cards) {
			if( poker.stringValueColor.compareTo(maxStringValueColor) > 0)
			{
				maxStringValueColor = poker.stringValueColor;
			}
			if (jokerIDList.contains(poker.id)) {
				continue;
			} else if (poker.value < 11) {
				isFlowerNiu = false;
				break;
			}
		}

		if (isFlowerNiu) {
			String cardName = String.format(CardName_FlowerNiu, maxStringValueColor);
			cardInfo.cardName = cardName;
			List<Integer> endCardIDList = getSortedCardIDList(cards);
			cardInfo.endCardIDList.addAll(endCardIDList);
		}else{
			cardInfo = null;
		}

		return cardInfo;
	}

	// 获取牌型可能的点数
	public NNPointCardInfo c1_point(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		
		List<Poker> jokerPokerList = new ArrayList<>();
		List<Poker> commonPokerList = new ArrayList<>();
		String maxStringValueColor = "";
		int niuPoint = 0;
		
		for( Poker poker : cards)
		{
			if( poker.stringValueColor.compareTo(maxStringValueColor) > 0)
			{
				maxStringValueColor = poker.stringValueColor;
			}
			if( jokerIDList.contains(poker.id))
			{
				jokerPokerList.add(poker);
			}else
			{
				commonPokerList.add(poker);
			}
		}
		
		//两张鬼牌，必定为牛牛
		if( jokerPokerList.size() == 2 )
		{
			List<Poker> niuPokerList = new ArrayList<>();
			List<Poker> leftPokerList = new ArrayList<>();
			
			for( int index = 0; index < commonPokerList.size(); index++)
			{
				if( index == 2)
				{
					leftPokerList.add( commonPokerList.get(index));
				}else
				{
					niuPokerList.add( commonPokerList.get(index));
				}
			}
			niuPokerList.add(jokerPokerList.get(0));
			leftPokerList.add(jokerPokerList.get(1));
			
			//设置返回值
			String cardName = String.format(CardName_NiuNiu, maxStringValueColor);
			cardInfo.cardName = cardName;
			
			List<Integer> endCardIDList = new ArrayList<>();
			endCardIDList = getSortedCardIDList(niuPokerList);
			endCardIDList.addAll(getSortedCardIDList(leftPokerList));
			cardInfo.endCardIDList = endCardIDList;
			
		}
		//一张鬼牌，存在满足条件的三张牌则为牛牛，否则如果存在满足条件的两张牌，为牛牛，否则为牛X
		else if(jokerPokerList.size() == 1)
		{
			List<Poker> niuPokerList = new ArrayList<>();
			List<Poker> leftPokerList = new ArrayList<>();
			
			List<Integer> niuCardIDList = getNiuCardIDList(commonPokerList);
			List<Integer> endCardIDList = new ArrayList<>();
			if( niuCardIDList != null )//存在满足条件的三张牌
			{
				for(Poker poker : cards)
				{
					if( niuCardIDList.contains( poker.id))
					{
						niuPokerList.add(poker);
					}else
					{
						leftPokerList.add(poker);
					}
				}
				
				endCardIDList = getSortedCardIDList(niuPokerList);
				endCardIDList.addAll(getSortedCardIDList(leftPokerList));
				
				String cardName = String.format(CardName_NiuNiu, maxStringValueColor);
				cardInfo.cardName = cardName;
				cardInfo.endCardIDList = endCardIDList;
			}else //存在满足条件的两张牌，为牛牛，否则为牛X
			{
				List<Integer> leftCardIDList = getMaxPointIDList(commonPokerList);
				
				for(Poker poker : cards)
				{
					if( !leftCardIDList.contains( poker.id))
					{
						niuPokerList.add(poker);
					}else
					{
						leftPokerList.add(poker);
					}
				}
				
				endCardIDList = getSortedCardIDList(niuPokerList);
				endCardIDList.addAll(getSortedCardIDList(leftPokerList));
				
				String cardName = "";
				if(leftCardIDList.get(2) == 10)
				{
					cardName = String.format(CardName_NiuNiu, maxStringValueColor);
				}else
				{
					cardName = String.format(CardName_NiuPoint, leftCardIDList.get(2), maxStringValueColor);
				}
				cardInfo.cardName = cardName;
				cardInfo.endCardIDList = endCardIDList;
					

			}
		}
		//没有鬼牌，存在满足条件的三张牌则为牛牛，或者牛X，否则为无牛
		else
		{
			List<Poker> niuPokerList = new ArrayList<>();
			List<Poker> leftPokerList = new ArrayList<>();
			
			List<Integer> niuCardIDList = getNiuCardIDList(commonPokerList);
			List<Integer> endCardIDList = new ArrayList<>();
			if( niuCardIDList != null )//存在满足条件的三张牌
			{
				for(Poker poker : cards)
				{
					if( niuCardIDList.contains( poker.id))
					{
						niuPokerList.add(poker);
					}else
					{
						leftPokerList.add(poker);
					}
				}
				
				endCardIDList = getSortedCardIDList(niuPokerList);
				endCardIDList.addAll(getSortedCardIDList(leftPokerList));
				//判断是牛牛还是牛X
				Poker poker1 = leftPokerList.get(0);
				Poker poker2 = leftPokerList.get(1);
				
				int sumPoint = 0;
				int maxPoint = 0;
				
				if(poker1.value >= 10){
					sumPoint += 10;
				}
				else{
					sumPoint += poker1.value;
				}
				
				if(poker2.value >= 10){
					sumPoint += 10;
				}
				else{
					sumPoint += poker2.value;
				}
				
				maxPoint = sumPoint%10;
				String cardName = "";
				if( maxPoint == 10 )
				{
					cardName = String.format(CardName_NiuNiu, maxStringValueColor);
				}else
				{
					cardName = String.format(CardName_NiuPoint, maxPoint, maxStringValueColor);
				}

				cardInfo.cardName = cardName;
				cardInfo.endCardIDList = endCardIDList;
			}else //无牛
			{
				String cardName = String.format(CardName_NotNiu, maxStringValueColor);
				endCardIDList.addAll(getSortedCardIDList(cards));
				
				cardInfo.cardName = cardName;
				cardInfo.endCardIDList = endCardIDList;
			}
		}
	
	
		return cardInfo;

	}
	//从队列中选出 和的个位数最大的 两张牌，并将它们的和的个位数 放在返回队列的最后一个位置
	public List<Integer> getMaxPointIDList(List<Poker> commonPokerList)
	{
		List<Integer> maxPointIDList = new ArrayList<>();
		int maxPoint = 0;
		
		for( int index1 = 0; index1 < commonPokerList.size(); index1++)
		{
			Poker poker1 = commonPokerList.get(index1);
			int pokerID1 = poker1.id;
			
			for( int index2 = index1+1; index2 < commonPokerList.size()-1; index2++)
			{
				Poker poker2 = commonPokerList.get(index2);
				int pokerID2 = poker2.id;
				
				int sumValue = 0;
				
				if(poker1.value >= 10){
					sumValue += 10;
				}
				else{
					sumValue += poker1.value;
				}
				
				if(poker2.value >= 10){
					sumValue += 10;
				}
				else{
					sumValue += poker2.value;
				}
				
				if( sumValue%10 == 0)
				{
					maxPoint = 10;
					maxPointIDList = Arrays.asList( pokerID1, pokerID2, maxPoint);
					break;
				} 
				if( sumValue%10 > maxPoint)
				{
					maxPoint = sumValue%10;
					maxPointIDList = Arrays.asList( pokerID1, pokerID2, maxPoint);
				}		
				
			}
					
		}
			
		return maxPointIDList;
	}
	public List<Integer> getNiuCardIDList(List<Poker> commonPokerList)
	{
		List<Integer> niuCardIDList = new ArrayList<>();
		
		for( int index1 = 0; index1 < commonPokerList.size(); index1++)
		{
			Poker poker1 = commonPokerList.get(index1);
			int pokerID1 = poker1.id;
			
			for( int index2 = index1+1; index2 < commonPokerList.size()-1; index2++)
			{
				Poker poker2 = commonPokerList.get(index2);
				int pokerID2 = poker2.id;
				
				for( int index3 = index2+1; index3 < commonPokerList.size()-2; index3++)
				{
					Poker poker3 = commonPokerList.get(index3);
					int pokerID3 = poker3.id;
					
					int sumValue = 0;
					if(poker1.value >= 10){
						sumValue += 10;
					}
					else{
						sumValue += poker1.value;
					}
					
					if(poker2.value >= 10){
						sumValue += 10;
					}
					else{
						sumValue += poker2.value;
					}
					
					if(poker3.value >= 10){
						sumValue += 10;
					}
					else{
						sumValue += poker3.value;
					}
						
					if( sumValue%10 == 0)
					{
						niuCardIDList = Arrays.asList( pokerID1, pokerID2, pokerID3);
						
					}		
				}
			}
					
		}
		
		if( niuCardIDList.size() == 0)
		{
			niuCardIDList = null;
		}
		
		return niuCardIDList;
	}
	
	
	public List<Integer> getSortedCardIDList( List<Poker> cards )
	{
		cards.sort(( first , second )->
		{
			return first.stringValueColor.compareTo( second.stringValueColor );
		});
		
		List<Integer> sortedCardIDList = new ArrayList<>();
		for( Poker poker : cards )
		{
			sortedCardIDList.add(poker.id);
		}
		

		
		return sortedCardIDList;
	}

}
