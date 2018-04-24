package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import business.global.mjcard.MJCard;

//玩家麻将手牌完整信息

public class MJHandCardInfo {
	//--------原始数据---------
	//初始玩家手牌ID列表
	public List<Integer> handCardIDList = new ArrayList<>();
	
	//玩法所有鬼牌字典
	public Hashtable<Integer, MJCard> allJokerMJCardInfo = new Hashtable<>();
	
	//---------中间数据-----------
	//不包含鬼牌的牌型字典{cardType:LAMITypeCardInfo}
	public Hashtable<Integer, MJHandCardTypeInfo> cardType2CardInfo = new Hashtable<>();
	
	//手牌拥有鬼牌列表
	public List<MJCard> jokerMJCardList = new ArrayList<>();
	//手牌拥有鬼牌列表
	public List<Integer> jokerCardIDList = new ArrayList<>();
	
	//不是鬼牌的ID列表
	public List<Integer> notJokerCardIDList = new ArrayList<>();
	
	//---------胡牌结果数据-----------
	public List<MJHandCard_HuInfo> allHuInfoList = new ArrayList<>();
}
