package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.List;

import business.global.mjcard.MJCard;

//玩家手牌一种cardType对应的数据信息
public class MJHandCardTypeInfo {
	//手牌类型
	public int cardType = 0;
	
	//可能存在一样的cardID(胡牌计算时) 不应该使用Hashtable,使用2个列表存储
	public List<Integer> cardIDList = new ArrayList<>();
	public List<MJCard> mjCardList = new ArrayList<>();

}
