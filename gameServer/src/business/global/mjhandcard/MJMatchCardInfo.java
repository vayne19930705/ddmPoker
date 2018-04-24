package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.List;

import business.global.mjcard.MJCard;

//胡牌算法筛选中间对象
public class MJMatchCardInfo {
	public MJGroupCardInfo info = new MJGroupCardInfo();           	//构建新牌型数据
	public List<Integer> leftCardIDList = new ArrayList<>(); 	//剩余的手牌
	public List<MJCard> useJokerMJCardList = new ArrayList<>(); //使用到的鬼牌
}
