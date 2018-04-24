package business.global.poker;

import java.util.ArrayList;
import java.util.List;

public class HandPokerValueInfo {
	//牌面值
	public int value = 0;
	
	//可能存在一样的cardID(胡牌计算时) 不应该使用Hashtable,使用2个列表存储
	public List<Integer> cardIDList = new ArrayList<>();
	public List<Poker> pokerList = new ArrayList<>();
}
