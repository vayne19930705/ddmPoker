package business.global.mj;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import business.global.log.BaseLog;
import business.global.mjcard.MJCard;
import business.global.mjcard.MJCardMgr;
import business.global.mjhandcard.MJHandCardInfo;
import business.global.mjhandcard.MJHandCardTypeInfo;

public abstract class MJBaseUtil extends BaseLog{
	
	public MJCardMgr cardMgr = null;
	private Hashtable<Integer, MJCard> ziMJCardInfo = new Hashtable<>();

	public MJBaseUtil(){
		super();
		this.cardMgr = MJCardMgr.getInstance();
		this.ziMJCardInfo = this.cardMgr.getAllZiCardInfo();
	}
	
	public String getLogString(){
		return "[MJBaseUtil]\t";
	}
	
	//-------------必须实现的接口-----------
	
	public abstract void initCanUseCard();
	
	//玩法使用到的所有cardTypeValue 扣除花
	public abstract List<Integer> getAllCanUseCardTypeValueList();
	
	//所有允许使用的cardID列表
	public abstract List<Integer> getAllowCardIDList();
	
	//获取所有花牌
	public abstract Hashtable<Integer, MJCard> getAllHuaMJCardInfo();
	
	//-------------获取接口---------------------
	public MJCardMgr getCardMgr(){
		return this.cardMgr;
	}
	
	public Hashtable<Integer, MJCard> getAllZiMJCardInfo(){
		return this.ziMJCardInfo;
	}
	
	//排序玩家手牌
	public void sortCardIDList(List<Integer> allHandCardIDList, Hashtable<Integer, MJCard> jokerMJCardInfo){
		 this.cardMgr.sortCardIDList(allHandCardIDList, jokerMJCardInfo, new Hashtable<>());
	}
	
	
	//获取所有的鬼牌对象
	public Hashtable<Integer, MJCard> getAllJokerMJCardInfoByCardID(int cardID){
		
		Hashtable<Integer, MJCard> jokerMJCardInfo = new Hashtable<>();
		
		MJCard jokerMJCard = this.cardMgr.getCardByCardID(cardID);
		
		List<Integer> jokerCardIDList = this.cardMgr.getAllCardIDListByCardTypeValue(jokerMJCard.cardTypeValue);
		
		int count = jokerCardIDList.size();
		for(int index=0; index<count; index++){
			int tempCardID = jokerCardIDList.get(index);
			MJCard tempJokerMJCard = this.cardMgr.getCardByCardID(tempCardID);
			jokerMJCardInfo.put(tempCardID, tempJokerMJCard);
		}
		
		return jokerMJCardInfo;
	}
	
	
	public List<Integer> getJokerCardIDList(List<Integer> allHandCardIDList, Hashtable<Integer, MJCard> jokerMJCardInfo){
		int count = allHandCardIDList.size();
		
		List<Integer> handCardIDList = new ArrayList<>();
		
		for(int index=0; index<count; index++){
			
			int cardID = allHandCardIDList.get(index);
			if(jokerMJCardInfo.containsKey(cardID)){
				handCardIDList.add(cardID);
			}
		}
		
		return handCardIDList;
	}
	
	public List<Integer> getAllJokerCardTypeValueList(Hashtable<Integer, MJCard> jokerMJCardInfo){
		List<Integer> allCardTypeValueList = new ArrayList<>();
		
		for(int cardID:jokerMJCardInfo.keySet()){
			MJCard mjCard = jokerMJCardInfo.get(cardID);
		
			int cardTypeValue = mjCard.cardTypeValue;
			if(allCardTypeValueList.contains(cardTypeValue)){
				continue;
			}
			allCardTypeValueList.add(cardTypeValue);
		}
		
		return allCardTypeValueList;
	}
	


	//--------------手牌接口--------------
	
	//获取手牌对象
	public MJHandCardInfo getHandCardInfo(List<Integer> handCardIDList, Hashtable<Integer, MJCard> allJokerMJCardInfo){
		
		List<Integer> tempHandCardIDList = new ArrayList<>(handCardIDList);
		
		List<MJCard> jokerMJCardList = new ArrayList<>();
		List<Integer> jokerCardIDList = new ArrayList<>();
		
		int handCount = handCardIDList.size();
		for(int index=0; index<handCount; index++){
			
			Integer cardID = handCardIDList.get(index);
			MJCard jokerMJCard = allJokerMJCardInfo.get(cardID);
			
			if(jokerMJCard != null){
				jokerMJCardList.add(jokerMJCard);
				jokerCardIDList.add(cardID);	
				//移除掉鬼牌
				tempHandCardIDList.remove(cardID);
			}
		}
		
		List<Integer> notJokerCardIDList = new ArrayList<>();
		
		//获取移除掉鬼牌后的牌型类型对应对象
		Hashtable<Integer, List<MJCard>> typeCardObjListInfo = this.cardMgr.getCardType2CardObjListInfo(tempHandCardIDList);
		
		
		//{cardTpe:HZhTypeCardInfo}
		Hashtable<Integer, MJHandCardTypeInfo> cardType2CardInfo = new Hashtable<>();
		
		for(int cardType:typeCardObjListInfo.keySet()){
			
			MJHandCardTypeInfo typeCardInfo = new MJHandCardTypeInfo();
			
			List<Integer> typeCardIDList = new ArrayList<>();
			List<MJCard> typeMJCardList = new ArrayList<>();
			
			List<MJCard> mjCardList = typeCardObjListInfo.get(cardType);
			int cardCount = mjCardList.size();
			for(int index1=0; index1 < cardCount; index1++){
				MJCard mjCard = mjCardList.get(index1);
				int cardID = mjCard.cardID;
				
				typeCardIDList.add(cardID);
				typeMJCardList.add(mjCard);
				
				notJokerCardIDList.add(cardID);
			}
			typeCardInfo.cardType = cardType;
			typeCardInfo.cardIDList = typeCardIDList;
			typeCardInfo.mjCardList = typeMJCardList;
			
			cardType2CardInfo.put(cardType, typeCardInfo);
		}
		

		MJHandCardInfo handCardInfo = new MJHandCardInfo();
		
		handCardInfo.handCardIDList = handCardIDList;
		
		handCardInfo.cardType2CardInfo = cardType2CardInfo;
		handCardInfo.notJokerCardIDList = notJokerCardIDList;
		
		handCardInfo.jokerMJCardList = jokerMJCardList;
		handCardInfo.jokerCardIDList = jokerCardIDList;
		
		handCardInfo.allJokerMJCardInfo = allJokerMJCardInfo;
		
		return handCardInfo;
	}

}
