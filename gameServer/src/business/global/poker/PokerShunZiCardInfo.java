package business.global.poker;

import java.util.ArrayList;
import java.util.List;

//顺子对象
public class PokerShunZiCardInfo {
	
	public String cardName = "";//顺子牌型

	public List<Integer> srcCardIDList = new ArrayList<>(); //筛选出来的顺子(鬼牌未替换)
	public List<Integer> showCardIDList = new ArrayList<>();//显示的顺子,鬼牌被替换为指定cardID
	
	public List<Integer> useJokerIDList = new ArrayList<>();//使用到的鬼牌ID列表
}
