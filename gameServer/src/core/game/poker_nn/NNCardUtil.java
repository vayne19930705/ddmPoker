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

	// ��������-����ֵ("1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
	// "s", "w")_maxStringValueCardID

	// �������Ͷ�Ӧ�������ַ���:���ַ�Unicode����Խ�������ȼ�Խ�ߣ�
	// ��һλ:��ʾ�����ƻ�����ͨ�ƣ�s�������� c����ͨ�ƣ� 
	// �ڶ�λ:��ʾ���͵����ȼ��������ƣ�z����Сţ y��ը��ţ x���廨ţ      ��ͨ�ƣ�z��ţţ  1-9��ţX 0����ţ ��
	// ����λ����ʾ���������ֵ��1-9 ��1-9 a-d��10-K s��С��  w�����
	// ����λ����ʾ����ŵĻ�ɫ��0:���	0��С��	4������	3������	2��÷��	1�����飩
	// ��������+��ɫ ��һ��%s����
	
	// ��Сţ
	public String CardName_FiveLittleNiu = "sz%s";
	// ��ը
	public String CardName_BoomNiu = "sy%s";
	// �廨ţ
	public String CardName_FlowerNiu = "sx%s";
	// ţţ
	public String CardName_NiuNiu = "cz%s";
	// ţx
	public String CardName_NiuPoint = "c%s%s";
	// ��ţ
	public String CardName_NotNiu = "c0%s";
 
	private static NNCardUtil instance = new NNCardUtil();

	public PokerManager pokerManager = null;

	protected ArrayList<Integer> allJokerCardIDList = new ArrayList<>(); // ���й���

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

		// ��ʼ������
		ArrayList<Integer> jokerCardIDList = pokerManager.getJokerCardIDList();

		this.allJokerCardIDList.addAll(jokerCardIDList);
	}

	// -------------���---------------
	// �ⲿ�������
	public void onStart() {

		// �������5��
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(110, 111, 112, 113, 213));

		// TODO:��ȡ������ţ��
		NNPointCardInfo cardInfo = this.getCardInfo(handCardIDList, this.allJokerCardIDList);

		this.info(":{} ���:{},{}", handCardIDList, cardInfo.cardName, cardInfo.endCardIDList);
	}

	// ��ȡ���ƽ��
	public NNPointCardInfo getCardInfo(List<Integer> cardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();

		if (cardIDList.size() != 5) {
			this.error("getCardInfo cardIDList:{} error", cardIDList);
			return cardInfo;
		}
		// ����һ��
		List<Integer> allCardIDList = new ArrayList<>(cardIDList);
		allCardIDList.sort((x, y) -> {
			return y - x;
		});

		List<Poker> pokerList = this.pokerManager.getPokerList(allCardIDList);

		// �Ƿ���5Сţ
		cardInfo = this.s1_FiveLittleNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// �Ƿ��� ��ը
		cardInfo = this.s2_BoomNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// �廨ţ
		cardInfo = this.s3_FlowerNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// ��ȡ���Ϳ��ܵĵ���
		cardInfo = this.c1_point(pokerList, allCardIDList, jokerIDList);

		return cardInfo;
	}

	// ��Сţ
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
				sumPoint++; // ��ʱ��Ĭ�Ϲ�������ֵΪ1
				continue;
			} else if (poker.value < 5) // ����ֵС��5�������ܺͣ���������ѭ��
			{
				sumPoint += poker.value;
			} else // ����ֵ����5���Ҳ��ǹ��ƣ��ж��޷�������Сţ,����ѭ��
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

		if (isFiveLittleNiu) // �������Сţ�������Ƶ�������Ϣ & ������ID��Ϣ
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

	// ��ը
	public NNPointCardInfo s2_BoomNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		
		Hashtable<Integer, List<Integer>> sumSameHashTable = new Hashtable<>();
		List<Integer> existJokerList = new ArrayList<>();
		List<Integer> endCardIDList = new ArrayList<>();
		String maxStringValueColor = "";
		
		for( Poker poker : cards)
		{
			if( poker.stringValueColor.compareTo(maxStringValueColor) >0 ) //��ȡ�����������ֵ����ɫ
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
				//ֱ������
				cardIDList.sort(( x , y )-> 
				{
					return y-x;
				});
				endCardIDList.addAll(cardIDList);
				
				//������ƣ�����еĻ���
				existJokerList.sort(( x, y)->
				{
					return y-x;
				}); 
				endCardIDList.addAll(existJokerList);
				break;
			}
		}
		
		//�����ը��ţ��׷��ʣ���ƣ������÷�����Ϣ���������÷���ֵΪnull
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

	// �廨ţ
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

	// ��ȡ���Ϳ��ܵĵ���
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
		
		//���Ź��ƣ��ض�Ϊţţ
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
			
			//���÷���ֵ
			String cardName = String.format(CardName_NiuNiu, maxStringValueColor);
			cardInfo.cardName = cardName;
			
			List<Integer> endCardIDList = new ArrayList<>();
			endCardIDList = getSortedCardIDList(niuPokerList);
			endCardIDList.addAll(getSortedCardIDList(leftPokerList));
			cardInfo.endCardIDList = endCardIDList;
			
		}
		//һ�Ź��ƣ�����������������������Ϊţţ����������������������������ƣ�Ϊţţ������ΪţX
		else if(jokerPokerList.size() == 1)
		{
			List<Poker> niuPokerList = new ArrayList<>();
			List<Poker> leftPokerList = new ArrayList<>();
			
			List<Integer> niuCardIDList = getNiuCardIDList(commonPokerList);
			List<Integer> endCardIDList = new ArrayList<>();
			if( niuCardIDList != null )//��������������������
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
			}else //�������������������ƣ�Ϊţţ������ΪţX
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
		//û�й��ƣ�����������������������Ϊţţ������ţX������Ϊ��ţ
		else
		{
			List<Poker> niuPokerList = new ArrayList<>();
			List<Poker> leftPokerList = new ArrayList<>();
			
			List<Integer> niuCardIDList = getNiuCardIDList(commonPokerList);
			List<Integer> endCardIDList = new ArrayList<>();
			if( niuCardIDList != null )//��������������������
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
				//�ж���ţţ����ţX
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
			}else //��ţ
			{
				String cardName = String.format(CardName_NotNiu, maxStringValueColor);
				endCardIDList.addAll(getSortedCardIDList(cards));
				
				cardInfo.cardName = cardName;
				cardInfo.endCardIDList = endCardIDList;
			}
		}
	
	
		return cardInfo;

	}
	//�Ӷ�����ѡ�� �͵ĸ�λ������ �����ƣ��������ǵĺ͵ĸ�λ�� ���ڷ��ض��е����һ��λ��
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
