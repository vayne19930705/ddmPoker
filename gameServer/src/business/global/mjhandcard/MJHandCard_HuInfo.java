package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.List;

//玩家麻将手牌胡牌信息
public class MJHandCard_HuInfo {
	
	//最终胡牌 的牌型组合
	public MJGroupCardInfo duiZiCardInfo = new MJGroupCardInfo();
	public List<MJGroupCardInfo> keziCardInfoList = new ArrayList<>();
	public List<MJGroupCardInfo> shunziCardInfoList = new ArrayList<>();
	
	//构成胡牌时每组牌的组合信息[[1,2,3],[3,4,5]]
	public List<List<Integer>> huGroupCardIDList = new ArrayList<>();
	
	//胡牌的完整ID列表[1,2,3,4,5] 鬼牌没有被替换
	public List<Integer> huEndCardIDList = new ArrayList<>();
	
	//胡牌的完整ID列表[1,2,3,4,5] 鬼牌被替换
	public List<Integer> huEndShowCardIDList = new ArrayList<>();
}
