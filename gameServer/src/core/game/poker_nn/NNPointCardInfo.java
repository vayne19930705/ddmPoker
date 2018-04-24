package core.game.poker_nn;

import java.util.ArrayList;
import java.util.List;

public class NNPointCardInfo {
	public String cardName = ""; 									//牌型名字 c_0_n
	public List<Integer> endCardIDList = new ArrayList<>();			//最终确认的牌型ID列表 前3张为构成的牛牌 后2张为点数
}
