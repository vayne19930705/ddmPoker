package core.game.poker_sss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SSSPointCardInfo {
	public List<String> cardName = new ArrayList<>(); 									//牌型名字 c_0_n-41
	public List<Integer> endCardIDList = new ArrayList<>();			//最终确认的牌型ID列表
	public String useSpecial = "";
	public int specialPoint = 0; // 特殊牌型点数
	public List<Integer> points = Arrays.asList(0,0,0); //3道的基础分数
	public List<Integer> addPoints = Arrays.asList(0,0,0); //3道的附加分数
	public List<Integer> dqPos = new ArrayList<>();//打枪pos
	public List<Integer> dqPosValue = new ArrayList<>();//打枪加的pos分数  0、1、翻倍
}
