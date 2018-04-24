package business.global.poker;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HandPokerInfo {
	//--------原始数据---------
	//初始玩家手牌ID列表
	public List<Integer> srcCardIDList = new ArrayList<>();
	
	//玩法所有鬼牌字典
	public Hashtable<Integer, Poker> allJokerPokerInfo = new Hashtable<>();
	
	//---------中间数据-----------
	//不包含鬼牌的color字典{color:HandPokerColorInfo}
	public Hashtable<Integer, HandPokerColorInfo> color2CardInfo = new Hashtable<>();
	
	//不包含鬼牌的value字典{value:HandPokerColorInfo}
	public Hashtable<Integer, HandPokerValueInfo> value2CardInfo = new Hashtable<>();
	
	//手牌拥有鬼牌列表
	public List<Poker> handJokerPokerList = new ArrayList<>();
	//手牌拥有鬼牌列表
	public List<Integer> handJokerCardIDList = new ArrayList<>();
	
	//不是鬼牌的ID列表
	public List<Integer> handNotJokerCardIDList = new ArrayList<>();
}
