package business.global.poker;

import java.util.ArrayList;
import java.util.List;

public class HandPokerValueInfo {
	//����ֵ
	public int value = 0;
	
	//���ܴ���һ����cardID(���Ƽ���ʱ) ��Ӧ��ʹ��Hashtable,ʹ��2���б�洢
	public List<Integer> cardIDList = new ArrayList<>();
	public List<Poker> pokerList = new ArrayList<>();
}
