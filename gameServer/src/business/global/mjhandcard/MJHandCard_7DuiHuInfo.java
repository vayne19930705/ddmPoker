package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.List;

//7对胡结构体
public class MJHandCard_7DuiHuInfo {
	//组成对子的牌型组合
	public List<MJGroupCardInfo> duiziCardInfoList = new ArrayList<>();
	
	//构成胡牌时每组牌的组合信息[[1,1],[3,3]]
	public List<List<Integer>> huGroupCardIDList = new ArrayList<>();
	
	//胡牌的完整ID列表[1,2,3,4,5] 鬼牌没有被替换
	public List<Integer> huEndCardIDList = new ArrayList<>();
	
	//胡牌的完整ID列表[1,2,3,4,5] 鬼牌被替换
	public List<Integer> huEndShowCardIDList = new ArrayList<>();
}
