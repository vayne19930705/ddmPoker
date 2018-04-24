package business.global.poker;

import java.util.ArrayList;
import java.util.List;

//一组牌信息
public class PokerGroupCardInfo {
	public String cardName = "";
	public List<Integer> srcCardIDList = new ArrayList<>(); //完整的IDL列表 接牌后原始的卡牌ID(赖子未替换)
	public List<Integer> showCardIDList = new ArrayList<>(); //完整的IDL列表 接牌后显示的卡牌ID(赖子被替换)

}
