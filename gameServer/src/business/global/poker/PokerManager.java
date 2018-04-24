package business.global.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import business.global.log.BaseLog;


public class PokerManager extends BaseLog{

	protected static PokerManager Instance = new PokerManager();
	
	public Hashtable<Integer, Poker> cardDict = new Hashtable<>();
	public Hashtable<Integer, Poker> paiJiuCardDict = new Hashtable<>();
	public Hashtable<Integer, Poker> ebgCardDict = new Hashtable<>();
	public Hashtable<Integer, Poker> nnmjCardDict = new Hashtable<>();
	
	public List<Integer> jokerCardIDList = new ArrayList<>();
	
	
	public static PokerManager getInstance(){
		return Instance;
	}
	
	public void init(){
		
		this.jokerCardIDList.clear();
		this.cardDict.clear();
		this.paiJiuCardDict.clear();
		
		//方 梅 红 黑
		for(int color=1; color<=4; color++){
			for(int value=1; value <= 13; value++){
				
				int cardID = color*100 + value;
				Poker poker = new Poker(cardID);
				this.cardDict.put(cardID, poker);
			}
		}
		//小王
		this.jokerCardIDList.add(PokerDefine.sJokerValue);
		//大王
		this.jokerCardIDList.add(PokerDefine.bJokerValue);
	
		int count = this.jokerCardIDList.size();
		
		for(int index=0; index <count; index++){
			int cardID = this.jokerCardIDList.get(index);
			Poker poker = new Poker(cardID);
			this.cardDict.put(cardID, poker);
		}
		
		//方 梅 红 黑
		for(int color=1; color<=4; color++){
			for(int value=1; value <= 13; value++){
				
				int cardID = color*100 + value;
				Poker poker = new Poker(cardID);
				if(value == 10 || value == 8 || value == 7 || value == 6 || value ==4){
					this.paiJiuCardDict.put(cardID, poker);
				}
				
				if((value == 2 || value == 5 || value == 9 || value == 11 || value == 12) && (color == 1 || color == 3)){
					this.paiJiuCardDict.put(cardID, poker);
				}
				
				if(value == 3 && color == 3){
					this.paiJiuCardDict.put(cardID, poker);
				}
				
			}
		}
		
		int bigJoker = 600;
		Poker bigJokerPoker = new Poker(bigJoker);
		this.paiJiuCardDict.put(bigJoker, bigJokerPoker);
		
		this.initEBGCardDict();
		this.initNNMJCardDict();
		
	}
	
	public String getLogString(){
		return String.format("[PokerManager]\t");
	}
	
	//初始化二八杠牌
	private void initEBGCardDict(){
		//方 梅 红 黑
		for(int color=1; color<=4; color++){
			for(int value=1; value <= 13; value++){
				int cardID = color*100 + value;
				Poker poker = new Poker(cardID);
				
				if(value < 10 || value == 13){
					this.ebgCardDict.put(cardID, poker);
				}
			}
		}
		
	}
	
	//初始化麻将牛牛牌
	public void initNNMJCardDict(){
		for(int color=1; color<=4; color++){
			for(int value=1; value<=10; value++){
				int cardID = color*100 + value;
				Poker poker = new Poker(cardID);
				this.nnmjCardDict.put(cardID, poker);
			}
		}
	}
	
	//获取鬼牌
	public ArrayList<Integer> getJokerCardIDList(){
		return new ArrayList<Integer>(this.jokerCardIDList);
	}
	
	//获取52张卡牌,不包含大小鬼
	public ArrayList<Integer> getCardIDList(){
		ArrayList<Integer> cardIDList = new ArrayList<Integer>(this.cardDict.keySet());
		cardIDList.removeAll(this.getJokerCardIDList());
		return cardIDList;	
	}
	
	//获取54张卡牌
	public ArrayList<Integer> getAllCardIDList(){
		return new ArrayList<Integer>(this.cardDict.keySet());
	}
	
	public ArrayList<Integer> getPaiJiuCardIDList(){
		return new ArrayList<Integer>(this.paiJiuCardDict.keySet());
	}
	
	public ArrayList<Integer> getEBGCardIDList(){
		return new ArrayList<Integer>(this.ebgCardDict.keySet());
	}
	
	public ArrayList<Integer> getNNMJCardIDList(){
		return new ArrayList<Integer>(this.nnmjCardDict.keySet());
	}
	
	//获取同一个牌值得4张卡牌ID
	public ArrayList<Integer> getSameValueCardIDList(int value){
		
		ArrayList<Integer> cardIDList = new ArrayList<>();
				
		for(int color=1; color<=4; color++){
			int cardID = color*100 + value;
			cardIDList.add(cardID);
		}
		
		return cardIDList;
	}
	
	public Poker getPokerByCardID(int cardID){
		Poker poker = this.cardDict.get(cardID);
		if(poker == null){
			this.error("getPokerByCardID not find cardID:{}", cardID);
			return null;
		}
		
		return poker;
	}
	
	//获取扑克value 对应的 stringvalue  
	//1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 500, 600 - > "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "s", "w"
	public String getPokerStringValueByValue(int value){
		
		int index = PokerDefine.ValueList.indexOf(value);
		if(index == -1){
			this.error("getPokerStringValueByValue:{} ValueList not find", value);
			return "0";
		}
		String stringValue = PokerDefine.StringValueList.get(index);
		if(stringValue == null){
			this.error("getPokerStringValueByValue StringValueList:{} not find:{}", PokerDefine.StringValueList, index);
			return "0";
		}
		
		return "";
	}	
	
	
	//获取所有poker牌对象
	public List<Poker> getPokerList(List<Integer> carIDList){
		List<Poker> pokerList = new ArrayList<>();
		for (int id : carIDList){
			
			Poker poker = this.cardDict.get(id);
			if(poker == null){
				this.error("getPokerCard id:{} not exist!", id);
				throw new RuntimeException("getPokerList card " + id);
			}
			
			pokerList.add(poker);
		}
		return pokerList;
	}
	//加色牌0不加色1加一色
	public List<Integer> getAddColorPokerList(int addColor){
		List<Integer> pokerList = new ArrayList<>();
		for(int color=4; color>(4-addColor); color--){
			for(int value=1; value <= 13; value++){
				
				int cardID = color*100 + value;
				pokerList.add(cardID);
			}
		}
		return pokerList;
	}
	
	//一色牌13张
	public List<Integer> getOneColorPokerList(int color){
		List<Integer> pokerList = new ArrayList<>();
		for(int value=1; value <= 13; value++){
			
			int cardID = color*100 + value;
			pokerList.add(cardID);
		}
		return pokerList;
	}
	
	//获取牌型的字符串显示
	public String getShowString(List<Poker> pokerList){
		int count = pokerList.size();
		List<String> showStringList = new ArrayList<>();
		
		for(int index=0; index<count; index++){
			Poker poker = pokerList.get(index);
			
			showStringList.add(poker.toString());
		}
		
		return this.getJoinString(showStringList);
	}
	
	//获取列表拼接的字符串
	public String getJoinString(List<String> stringList){
		
		String joinString = "";
		
		int count = stringList.size();
		for(int index=0; index<count; index++){
			
			joinString += stringList.get(index);
			
			if(index + 1 != count){
				joinString += ";";
			}
		}

		return joinString;
	}
	
	//获取手牌对象
	public HandPokerInfo getHandInfo(List<Integer> cardIDList, List<Integer> allJokerIDList){
		HandPokerInfo handPokerInfo = new HandPokerInfo();
		
		int allJoerCount = allJokerIDList.size();
		Hashtable<Integer, Poker> allJokerPokerInfo = new Hashtable<>();
		
		for(int index=0; index<allJoerCount; index++){
			int cardID = allJokerIDList.get(index);
			Poker poker = this.getPokerByCardID(cardID);
			if(poker == null){
				this.error("getHandInfo not find jokerID:{}",cardID);
				continue;
			}
			allJokerPokerInfo.put(cardID, poker);
		}
		
		handPokerInfo.srcCardIDList = new ArrayList<>(cardIDList);
		handPokerInfo.allJokerPokerInfo = allJokerPokerInfo;
				
		//不包含鬼牌的color字典{color:HandPokerColorInfo}
		Hashtable<Integer, HandPokerColorInfo> color2CardInfo = new Hashtable<>();
		
		//不包含鬼牌的value字典{value:HandPokerColorInfo}
		Hashtable<Integer, HandPokerValueInfo> value2CardInfo = new Hashtable<>();
		
		//手牌拥有鬼牌列表
		List<Poker> handJokerPokerList = new ArrayList<>();
		//手牌拥有鬼牌列表
		List<Integer> handJokerCardIDList = new ArrayList<>();
		
		//不是鬼牌的ID列表
		List<Integer> handNotJokerCardIDList = new ArrayList<>();
		
		int count = cardIDList.size();
		
		//重小到大排序,101,102,103,104,105,106,...201,...
		Collections.sort(cardIDList);
		
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			
			Poker poker = this.getPokerByCardID(cardID);
			if(poker == null){
				this.error("getHandInfo not find cardID:{}", cardID);
				continue;
			}
			
			//如果是鬼牌
			if(allJokerPokerInfo.containsKey(cardID)){
				handJokerCardIDList.add(cardID);
				handJokerPokerList.add(poker);
				continue;
			}
			
			//101,102,103,.....201,...301,....401
			handNotJokerCardIDList.add(cardID);
			
			int color = poker.color;
			int value = poker.value;
			
			if(!color2CardInfo.containsKey(color)){
				HandPokerColorInfo newColorInfo = new HandPokerColorInfo();
				newColorInfo.color = color;
				color2CardInfo.put(color, newColorInfo);
			}
			HandPokerColorInfo handPokerColorInfo = color2CardInfo.get(color);
			//101,102,103,104,105,106,107,108,109,110,111,112,113
			handPokerColorInfo.cardIDList.add(cardID);
			handPokerColorInfo.pokerList.add(poker);
			
			if(!value2CardInfo.containsKey(value)){
				HandPokerValueInfo newValueInfo = new HandPokerValueInfo();
				newValueInfo.value = value;
				value2CardInfo.put(value, newValueInfo);
			}
			HandPokerValueInfo valueInfo = value2CardInfo.get(value);
			//101,201,301,401
			valueInfo.cardIDList.add(cardID);
			valueInfo.pokerList.add(poker);
		}
		
		handPokerInfo.color2CardInfo = color2CardInfo;
		handPokerInfo.value2CardInfo = value2CardInfo;
		handPokerInfo.handJokerCardIDList = handJokerCardIDList;
		handPokerInfo.handJokerPokerList = handJokerPokerList;
		handPokerInfo.handNotJokerCardIDList = handNotJokerCardIDList;
		
		return handPokerInfo;
	}
	
	//从大到小排序,A最大排前面  AKQJ...2
	//是否倒序 isReverse  true:2,3,...JQKA  false:AKQJ...2
	public void sortCardIDListMaxA(List<Integer> cardIDList, boolean isReverse){
		
		PokerManager pokerMgr = this;
		
		Collections.sort(cardIDList, new Comparator<Integer>() {
            @Override
            public int compare(Integer firstCardID, Integer secondCardID) {
            	
            	Poker firstPoker = pokerMgr.getPokerByCardID(firstCardID);
            	Poker secondPoker = pokerMgr.getPokerByCardID(secondCardID);
            	
            	int firstValue = firstPoker.value;
            	int secondValue = secondPoker.value;            	
       	
            	//A排前面
               	if(firstValue == 1 && secondValue != 1){
            		return -1;
            	}
            	else if(firstValue != 1 && secondValue == 1){
            		return 1;
            	}
            	else{
            		//如果值一样,按花色大的排前面
            		if(secondValue == firstValue){
            			return secondPoker.color - firstPoker.color;
            		}
            		else{
            			// 从大到小
                        return secondValue - firstValue;
            		}            		
            	}
            }
        });
		
		//从小到大排序,A排最后面,2,3,...JQKA
		if(isReverse){
			Collections.reverse(cardIDList);
		}
		
	}
	
	
	//从大到小排序,K最大排前面,KQJ...2,A
	//是否倒序 isReverse true: A 2 3 4,.. J Q K false:K Q J ... 2 A
	public void sortCardIDListMaxK(List<Integer> cardIDList, boolean isReverse){
		
		PokerManager pokerMgr = this;
		
		Collections.sort(cardIDList, new Comparator<Integer>() {
            @Override
            public int compare(Integer firstCardID, Integer secondCardID) {
            	
            	Poker firstPoker = pokerMgr.getPokerByCardID(firstCardID);
            	Poker secondPoker = pokerMgr.getPokerByCardID(secondCardID);
            	
            	int firstValue = firstPoker.value;
            	int secondValue = secondPoker.value;

        		//如果值一样,按花色大的排前面
               	if(secondValue == firstValue){
        			return secondPoker.color - firstPoker.color;
        		}
        		else{
        			// 从大到小
                    return secondValue - firstValue;
        		}              		     
            }
        });
		
		//从小到大排序 通过value牌型  A 2 3 4,.. J Q K
		if(isReverse){
			Collections.reverse(cardIDList);
		}
	}
		
	//排序 value的列表  1在前面  1 13 12 11 10 9 8 7 6 5 4 3 2 不包含鬼的value
	public void sortCardValueList(List<Integer> valueList){
		
		//从小到大
		Collections.sort(valueList);
		
		//把A放在末尾
		if(valueList.contains(PokerDefine.A_Value)){
			valueList.remove(PokerDefine.A_Value);
			valueList.add(PokerDefine.A_Value);
		}
		
		//倒序
		Collections.reverse(valueList);
	}
	
	
	//检测是否是同花列表
	public boolean isTongHua(List<Integer> cardIDList, List<Integer> allJokerCardIDList){
		int count = cardIDList.size();
		
		int findColor = -1;
		
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			Poker poker = this.getPokerByCardID(cardID);
			if(poker == null){
				this.error("isTongHua not find cardID:{}", cardID);
				continue;
			}
			
			//如果是鬼牌
			if(allJokerCardIDList.contains(poker.id)){
				continue;
			}
			
			if(findColor == -1){
				findColor = poker.color;
			}
			else if(findColor != poker.color){
				return false;
			}
		}
		
		return true;
	}
	
	//一手牌中存在鬼牌的数量
	public int getCardIDListJokerCount(List<Integer> cardIDList, List<Integer> allJokerCardIDList){
		
		int jokerCount = 0;
		int count = cardIDList.size();
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			if(allJokerCardIDList.contains(cardID)){
				jokerCount += 1;
			}
		}
		
		return jokerCount;
	}
}
