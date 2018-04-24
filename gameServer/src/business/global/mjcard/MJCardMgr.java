package business.global.mjcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import business.global.log.BaseLog;

public class MJCardMgr extends BaseLog {
	
	protected static MJCardMgr Instance = new MJCardMgr();
	
	public static MJCardMgr getInstance(){
		return Instance;
	}
	
	public String getLogString(){
		return "[MJCardMgr]\t";
	}
	
	//����
	protected Hashtable<Integer, MJCard> wanCardDict = new Hashtable<>();
	
	//����
	protected Hashtable<Integer, MJCard> tiaoCardDict = new Hashtable<>();
	
	//Ͳ��
	protected Hashtable<Integer, MJCard> tongCardDict = new Hashtable<>();
	
	//���� 1,2,3����
	protected Hashtable<Integer, MJCard> ziCardDict = new Hashtable<>();
	
	//���� 4����
	protected Hashtable<Integer, MJCard> fengCardDict = new Hashtable<>();
	
	//���� 5����
	protected Hashtable<Integer, MJCard> jianCardDict = new Hashtable<>();
	
	//���� 6:����
	protected Hashtable<Integer, MJCard> huaCardDict = new Hashtable<>();
	
	//���� 7:����
	protected Hashtable<Integer, MJCard> jiCardDict = new Hashtable<>();
	
	//���ӵļ���
	protected Hashtable<Integer, MJCard> ji2CardDict = new Hashtable<>();
	
	//���� 8:�ٴ�
	protected Hashtable<Integer, MJCard> baidaCardDict = new Hashtable<>();
	
	//���� 9:������
	protected Hashtable<Integer, MJCard> speCardDict = new Hashtable<>();
	
	//���п����ֵ�
	protected Hashtable<Integer, MJCard> allCardDict = new Hashtable<>();
	
	//{cardTypeValue:[cardID,cardID]}
	protected Hashtable<Integer, List<Integer>> cardTypeValue2CardIDListInfo = new Hashtable<>();
	
	public void init(){
		
		//��ʼ�� �� �� Ͳ
		for(int index=1; index<=9; index++){
			
			for(int index_j = 1; index_j <= 4; index_j++){
				
				//һ��:1101 2��:1201
				int cardTypeValue = 10 + index;
				//û�����cardTypeValue
				if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue)){
					this.cardTypeValue2CardIDListInfo.put(cardTypeValue, new ArrayList<>());
				}
				List<Integer> cardIDList = this.cardTypeValue2CardIDListInfo.get(cardTypeValue);
				
				int cardID = cardTypeValue*100 + index_j;
				MJCard cardObj1 = new MJCard(cardID);
				this.wanCardDict.put(cardID, cardObj1);
				this.ziCardDict.put(cardID, cardObj1);
				this.allCardDict.put(cardID, cardObj1);
				cardIDList.add(cardID);
				
				//��
				int cardTypeValue2 = 20 + index;
				//û�����cardTypeValue
				if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue2)){
					this.cardTypeValue2CardIDListInfo.put(cardTypeValue2, new ArrayList<>());
				}
				List<Integer> cardIDList2 = this.cardTypeValue2CardIDListInfo.get(cardTypeValue2);
				
				int cardID2 = cardTypeValue2*100 + index_j;
				MJCard cardObj2 = new MJCard(cardID2);
				this.tiaoCardDict.put(cardID2, cardObj2);
				this.ziCardDict.put(cardID2, cardObj2);
				this.allCardDict.put(cardID2, cardObj2);
				cardIDList2.add(cardID2);
				
				//Ͳ
				int cardTypeValue3 = 30 + index;
				//û�����cardTypeValue
				if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue3)){
					this.cardTypeValue2CardIDListInfo.put(cardTypeValue3, new ArrayList<>());
				}
				List<Integer> cardIDList3 = this.cardTypeValue2CardIDListInfo.get(cardTypeValue3);
				
				int cardID3 = cardTypeValue3*100 + index_j;
				MJCard cardObj3 = new MJCard(cardID3);
				this.tongCardDict.put(cardID3, cardObj3);
				this.ziCardDict.put(cardID3, cardObj3);
				this.allCardDict.put(cardID3, cardObj3);
				cardIDList3.add(cardID3);
			}
		}
		
		//��������
		for(int index=1; index<=4; index++){
			
			for(int index_j = 1; index_j <= 4; index_j++){
				int cardTypeValue = 40 + index;
				//û�����cardTypeValue
				if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue)){
					this.cardTypeValue2CardIDListInfo.put(cardTypeValue, new ArrayList<>());
				}
				List<Integer> cardIDList = this.cardTypeValue2CardIDListInfo.get(cardTypeValue);
				
				int cardID = cardTypeValue*100 + index_j;
				MJCard cardObj = new MJCard(cardID);
				this.fengCardDict.put(cardID, cardObj);
				this.allCardDict.put(cardID, cardObj);
				
				cardIDList.add(cardID);
			}
		}
		
		//�з���
		for(int index=1; index<=3; index++){
			
			for(int index_j = 1; index_j <= 4; index_j++){
				int cardTypeValue = 50 + index;
				//û�����cardTypeValue
				if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue)){
					this.cardTypeValue2CardIDListInfo.put(cardTypeValue, new ArrayList<>());
				}
				List<Integer> cardIDList = this.cardTypeValue2CardIDListInfo.get(cardTypeValue);
				
				int cardID = cardTypeValue*100 + index_j;
				MJCard cardObj = new MJCard(cardID);
				this.jianCardDict.put(cardID, cardObj);
				this.allCardDict.put(cardID, cardObj);
				
				cardIDList.add(cardID);
			}
		}
		
		//÷�����
		for(int index=1; index<=4; index++){
			int cardTypeValue = 60 + index;
			
			//û�����cardTypeValue
			if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue)){
				this.cardTypeValue2CardIDListInfo.put(cardTypeValue, new ArrayList<>());
			}
			List<Integer> cardIDList = this.cardTypeValue2CardIDListInfo.get(cardTypeValue);
			
			int cardID = cardTypeValue*100 + 1;
			MJCard cardObj = new MJCard(cardID);
			this.huaCardDict.put(cardID, cardObj);
			this.allCardDict.put(cardID, cardObj);
			
			cardIDList.add(cardID);
		}
		
		//�����ﶬ
		for(int index=1; index<=4; index++){
			int cardTypeValue = 70 + index;
			//û�����cardTypeValue
			if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue)){
				this.cardTypeValue2CardIDListInfo.put(cardTypeValue, new ArrayList<>());
			}
			List<Integer> cardIDList = this.cardTypeValue2CardIDListInfo.get(cardTypeValue);
			
			int cardID = cardTypeValue*100 + 1;
			MJCard cardObj = new MJCard(cardID);
			this.jiCardDict.put(cardID, cardObj);
			this.allCardDict.put(cardID, cardObj);
			cardIDList.add(cardID);
		}
		
		//��һ�� �����ﶬ  7002,7102,7202,7302(�����齫��Ҫ)
		for(int index=1; index<=4; index++){
			int cardTypeValue = 70 + index;
			int cardID = cardTypeValue*100 + 2;
			
			//��һ�ݲ���׷��cardTypeValue2CardIDListInfo
//			//û�����cardTypeValue
//			if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue)){
//				this.cardTypeValue2CardIDListInfo.put(cardTypeValue, new ArrayList<>());
//			}
//			List<Integer> cardIDList = this.cardTypeValue2CardIDListInfo.get(cardTypeValue);
//			cardIDList.add(cardID);
			
			MJCard cardObj = new MJCard(cardID);
			//���Ӽ�����Ϣ
			this.ji2CardDict.put(cardID, cardObj);
			this.allCardDict.put(cardID, cardObj);
			
			
		}
		
		//�ٴ���
		for(int index=1; index<=4; index++){
			int cardTypeValue = 81;
			
			//û�����cardTypeValue
			if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue)){
				this.cardTypeValue2CardIDListInfo.put(cardTypeValue, new ArrayList<>());
			}
			List<Integer> cardIDList = this.cardTypeValue2CardIDListInfo.get(cardTypeValue);
			
			int cardID = cardTypeValue*100 + index;
			MJCard cardObj = new MJCard(cardID);
			this.baidaCardDict.put(cardID, cardObj);
			this.allCardDict.put(cardID, cardObj);
			
			cardIDList.add(cardID);
		}
		
		//������
		for(int index=1; index<=4; index++){
			int cardTypeValue = 90 + index;
			
			//û�����cardTypeValue
			if(!this.cardTypeValue2CardIDListInfo.containsKey(cardTypeValue)){
				this.cardTypeValue2CardIDListInfo.put(cardTypeValue, new ArrayList<>());
			}
			List<Integer> cardIDList = this.cardTypeValue2CardIDListInfo.get(cardTypeValue);
			
			int cardID = cardTypeValue*100 + 1;
			MJCard cardObj = new MJCard(cardID);
			this.speCardDict.put(cardID, cardObj);
			this.allCardDict.put(cardID, cardObj);
			
			cardIDList.add(cardID);
		}
		
		
		//this.outPutAllCardString(this.getLAMIMJAllCardInfo());
		
		//this.outPutAllCardString(this.getHZMJAllCardInfo());
	}
	
	//-------------debug���-------------------
	
	//��������Ƶ���Ϣ
	public void outPutAllCardString(Hashtable<Integer, MJCard> allCardDict){
		
		List<Integer> allCardIDList = new ArrayList<>(allCardDict.keySet());
		Collections.sort(allCardIDList);
		
		int count = allCardIDList.size();
		
		StringBuffer cardString = new StringBuffer();
		
		for(int index=0; index<count; index++){
			int cardID = allCardIDList.get(index);
			MJCard cardObj = allCardDict.get(cardID);
			
			cardString.append(cardObj.toString());
			cardString.append(",");
			if(index%4 == 3){
				cardString.append("\n");
			}
		}
		
		this.info("��������Ϣ:\n{}", cardString.toString());
	}
	
	//�������ID��Ӧ��������Ϣ
	public void outPutCardStringByCardIDList(List<Integer> allCardIDList){
		int count = allCardIDList.size();
		
		StringBuffer cardString = new StringBuffer();
		
		for(int index=0; index<count; index++){
			int cardID = allCardIDList.get(index);
			MJCard cardObj = this.allCardDict.get(cardID);
			if(cardObj == null){
				this.error("outPutCardStringByCardIDList not find cardID:{}", cardID);
				continue;
			}
			cardString.append(cardObj.toString());
			cardString.append(",");
		}
		
		this.info("allCardIDList:{},{}", allCardIDList, cardString.toString());
	}
	
	public String getOutPutCardStringByMJCardList(List<MJCard> allMJCardList){
		int count = allMJCardList.size();
		
		StringBuffer cardString = new StringBuffer();
		
		for(int index=0; index<count; index++){
			MJCard cardObj = allMJCardList.get(index);
			cardString.append(cardObj.toString());
			cardString.append(",");
		}
		
		return cardString.toString();
	}
	
	//------------------��ȡ����------------------------
	
	//��ȡ���ƶ���
	public MJCard getCardByCardID(int cardID){
		
		MJCard cardObj = this.allCardDict.get(cardID);
		if(cardObj == null){
			this.error("getCardByCardID({}) not find", cardID);
			return null;
		}
		
		return cardObj;
	}
	
	public MJCard getCardByCardTypeAndValue(int cardType, int value){
		
		int cardID = (cardType*10 + value)*100 + 1;
		MJCard cardObj = this.allCardDict.get(cardID);
		if(cardObj == null){
			this.error("getCardByCardTypeAndValue({},{}) not find", cardType, value);
			return null;
		}
		
		return cardObj;
	}
	
	public MJCard getCardByCardTypeValue(int cardTypeValue){
		int cardID = cardTypeValue*100 + 1;
		MJCard cardObj = this.allCardDict.get(cardID);
		if(cardObj == null){
			this.error("getCardByCardTypeValue({}) not find", cardTypeValue);
			return null;
		}
		
		return cardObj;
	}
	
	//ͨ��cardID�б��ȡMJCard�����б�
	public List<MJCard> getMJCardListByCardIDList(List<Integer> cardIDList){
	
		List<MJCard> cardObjList = new ArrayList<>();
		
		int count = cardIDList.size();
		
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			MJCard cardObj = this.allCardDict.get(cardID);
			if(cardObj == null){
				this.error("getMJCardListByCardIDList({}) not find", cardID);
				return new ArrayList<>();
			}
			cardObjList.add(cardObj);
		}
		
		return cardObjList;
	}
	
	//��ȡcardID��ӦshowList
	public String getMJCardShowListByCardIDList(List<Integer> cardIDList){
		StringBuffer ret = new StringBuffer();
		int count = cardIDList.size();
		
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			MJCard cardObj = this.allCardDict.get(cardID);
			if(cardObj == null){
				this.error("getMJCardListByCardIDList({}) not find", cardID);
				return "getMJCardShowListByCardIDList error";
			}
			ret.append(cardObj.toString() + " ");
		}
		return ret.toString();
	}
	
	//��ȡcardID��ӦcardValue�б�
	public List<Integer> getCardValueList(List<Integer> cardIDList){
		
		List<Integer> cardValueList = new ArrayList<>();
		
		int count = cardIDList.size();
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			MJCard mjCard = this.getCardByCardID(cardID);
			if(mjCard == null){
				this.error("getCardValueList not find cardID:{}", cardID);
				continue;
			}
			cardValueList.add(mjCard.cardValue);
		}
		return cardValueList;
	}
	
	//��ȡcardID��ӦcardValue�б�
	public List<Integer> getCardTypeValueList(List<Integer> cardIDList){
		
		List<Integer> cardValueList = new ArrayList<>();
		
		int count = cardIDList.size();
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			MJCard mjCard = this.getCardByCardID(cardID);
			if(mjCard == null){
				this.error("getCardTypeValueList not find cardID:{}", cardID);
				continue;
			}
			cardValueList.add(mjCard.cardTypeValue);
		}
		return cardValueList;
	}
	
	//��ȡcardID��ӦcardValue�б�
	public List<Integer> getHaveFakeCardTypeValueList(List<Integer> cardIDList, Hashtable<Integer, MJCard> fakeJokerMJCardInfo){
		
		List<Integer> cardValueList = new ArrayList<>();
		
		int count = cardIDList.size();
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			
			MJCard mjCard = null;
			
			if(fakeJokerMJCardInfo.containsKey(cardID)){
				mjCard = fakeJokerMJCardInfo.get(cardID);
			}
			else{
				mjCard = this.getCardByCardID(cardID);
			}
			
			if(mjCard == null){
				this.error("getCardTypeValueList not find cardID:{}", cardID);
				continue;
			}
			
			cardValueList.add(mjCard.cardTypeValue);
		}
		return cardValueList;
	}
	
	//��ȡcardID��ӦcardType�б�
	public List<Integer> getCardTypeList(List<Integer> cardIDList){
		List<Integer> cardTypeList = new ArrayList<>();
		
		int count = cardIDList.size();
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			MJCard mjCard = this.getCardByCardID(cardID);
			if(mjCard == null){
				this.error("getCardTypeList not find cardID:{}", cardID);
				continue;
			}
			cardTypeList.add(mjCard.cardType);
		}
		return cardTypeList;
	}
	
	
	//��ȡcardType��Ӧ��cardID�б� {cardType:[cardID,cardID]}
	public Hashtable<Integer, List<Integer>> getCardType2CardIDListInfo(List<Integer> cardIDList){
		Hashtable<Integer, List<Integer>> cardType2CardIDListInfo = new Hashtable<>();
		
		int count = cardIDList.size();
		
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			
			MJCard cardObj = this.getCardByCardID(cardID);
			if(cardObj == null){
				this.error("getCardType2CardIDListInfo not find cardID:{}", cardID);
				continue;
			}
			int cardType = cardObj.cardType;
			
			if(!cardType2CardIDListInfo.containsKey(cardType)){
				cardType2CardIDListInfo.put(cardType, new ArrayList<>());
			}
			
			List<Integer> typeCardIDList = cardType2CardIDListInfo.get(cardType);
			
			typeCardIDList.add(cardID);
		}
		
		return cardType2CardIDListInfo;
	}
	
	public Hashtable<Integer, List<Integer>> getCardTypeValue2CardIDListInfo(List<Integer> cardIDList){
		return this.getCardTypeValue2CardIDListInfo(cardIDList, new Hashtable<>());
	}
	
	//��ȡcardTypeValue��Ӧ��cardID�б� {cardTypeValue:[cardID,cardID]}
	public Hashtable<Integer, List<Integer>> getCardTypeValue2CardIDListInfo(List<Integer> cardIDList, Hashtable<Integer, MJCard> fakeJokerCardInfo){
		Hashtable<Integer, List<Integer>> cardTypeValue2CardIDListInfo = new Hashtable<>();
		
		int count = cardIDList.size();
		
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			
			MJCard cardObj = null;
			if(fakeJokerCardInfo.containsKey(cardID)){
				cardObj = fakeJokerCardInfo.get(cardID);
			}
			else{
				cardObj = this.getCardByCardID(cardID);
			}
			if(cardObj == null){
				this.error("getCardTypeValue2CardIDListInfo not find :{}", cardID);
				continue;
			}
			int cardTypeValue = cardObj.cardTypeValue;
			
			if(!cardTypeValue2CardIDListInfo.containsKey(cardTypeValue)){
				cardTypeValue2CardIDListInfo.put(cardTypeValue, new ArrayList<>());
			}
			
			List<Integer> typeCardIDList = cardTypeValue2CardIDListInfo.get(cardTypeValue);
			
			typeCardIDList.add(cardID);
		}
		
		return cardTypeValue2CardIDListInfo;
	}
	
	//��ȡ�������Ͷ�Ӧ�Ŀ��ƶ����б� {cardType:[MJCard,MJCard]}
	public Hashtable<Integer, List<MJCard>> getCardType2CardObjListInfo(List<Integer> cardIDList){
		
		Hashtable<Integer, List<MJCard>> cardType2CardObjListInfo = new Hashtable<>();
		
		List<MJCard> handMJCardList = this.getMJCardListByCardIDList(cardIDList);
		int count = handMJCardList.size();
		
		for(int index=0; index<count; index++){
			MJCard cardObj = handMJCardList.get(index);
			
			int cardType = cardObj.cardType;
			List<MJCard> cardObjList = cardType2CardObjListInfo.get(cardType);
			if(cardObjList == null){
				cardObjList = new ArrayList<>();
				cardType2CardObjListInfo.put(cardType, cardObjList);
			}
			
			cardObjList.add(cardObj);
		}
		
		return cardType2CardObjListInfo;
	}
	
	
	
	//��ȡһ����cardTypevalue��ɵ�cardIDlist
	public List<Integer> getSameCardTypeValueCardIDList(List<Integer> handCardIDList, int targetCardID){
		int count = handCardIDList.size();
		
		List<Integer> findCardIDList = new ArrayList<>();
		
		MJCard targetMJCard = this.getCardByCardID(targetCardID);
		if(targetMJCard == null){
			this.error("getSameCardTypeValueCardIDList not find targetCardID:{}", targetCardID);
			return findCardIDList;
		}
		
		for(int index=0; index<count; index++){
			int cardID = handCardIDList.get(index);
			MJCard mjCard = this.getCardByCardID(cardID);
			if(mjCard == null){
				this.error("getSameCardTypeValueCardIDList not find :{}", cardID);
				return new ArrayList<>();
			}
			
			if(mjCard.cardTypeValue == targetMJCard.cardTypeValue){
				findCardIDList.add(cardID);
			}
		}
		
		return findCardIDList;
	}

	
	//----------------------�жϽӿ�-------------------------
	
	//�Ƿ���ͬ��cardTypeValue 
	public boolean isSameCardTypeValue(List<Integer> cardIDList){
		int count = cardIDList.size();
		
		int cardTypeValue = -1;
		
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			MJCard mjCard = this.getCardByCardID(cardID);
			if(mjCard == null){
				this.error("isSameCardTypeValue not find :{}", cardID);
				return false;
			}
			
			if(cardTypeValue == -1){
				cardTypeValue = mjCard.cardTypeValue;
			}
			else if(cardTypeValue != mjCard.cardTypeValue){
				return false;
			}
		}
		
		if(cardTypeValue == -1){
			return false;
		}
		
		return true;
	}
	
	public boolean isShunZi(List<Integer> cardIDList){
		return this.isShunZi(cardIDList, new Hashtable<>());
	}
	
	//�Ƿ�������˳��
	public boolean isShunZi(List<Integer> cardIDList, Hashtable<Integer, MJCard> fakeJokerMJCardInfo){
		
		int count = cardIDList.size();
		if(count < 3){
			this.error("isShunZi cardIDList:{} error", cardIDList);
			return false;
		}
		int findCardType = -1;
		Set<Integer> valueSet = new HashSet<>();
		
		for(int index=0; index<count; index++){
			int cardID = cardIDList.get(index);
			MJCard mjCardObj = null;
			if(fakeJokerMJCardInfo.containsKey(cardID)){
				mjCardObj = fakeJokerMJCardInfo.get(cardID);
			}
			else{
				mjCardObj = this.getCardByCardID(cardID);
			}
			
			if(mjCardObj == null){
				this.error("isShunZi not find :{}", cardID);
				return false;
			}
			int cardType = mjCardObj.cardType;
			int cardValue = mjCardObj.cardValue;
			
			//�����û��ʼ�����͵���������
			if(findCardType == -1){
				findCardType = cardType;
			}
			//������2������,����
			else if(findCardType != cardType){
				return false;
			}
			
			//���value�Ѿ�����
			if(valueSet.contains(cardValue)){
				return false;
			}
			valueSet.add(cardValue);
		}
		
		int firstCardID = cardIDList.get(0);
		
		MJCard firstMJCard = null;
		if(fakeJokerMJCardInfo.containsKey(firstCardID)){
			firstMJCard = fakeJokerMJCardInfo.get(firstCardID);
		}
		else{
			firstMJCard = this.getCardByCardID(firstCardID);
		}
		
		int cardType = firstMJCard.cardType;
		
		List<Integer> allValueList = this.getAllValueListByCardType(cardType);
		if(allValueList == null){
			this.error("isShunZi:{},{}", firstCardID, cardType);
			return false;
		}
		
		int valueCount = allValueList.size();
		
		int minValue = Collections.min(valueSet);
		int maxValue = Collections.max(valueSet);
		
		//��С�������value
		for(int index=0; index<valueCount; index++){
			int value = allValueList.get(index);
			
			if(value < minValue){
				continue;
			}
			
			//�������
			if(valueSet.contains(value)){
				//����Ѿ�ѭ�������
				if(maxValue == value){
					return true;
				}
				continue;
			}
			//ȱ�����value
			else{
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param allHandCardIDList
	 * @param jokerMJCardInfo	���ֵ�
	 * @param fakeJokerMJCardInfo �ٽ��ֵ�
	 */
	public void sortCardIDList(List<Integer> allHandCardIDList, Hashtable<Integer, MJCard> jokerMJCardInfo, Hashtable<Integer, MJCard> fakeJokerMJCardInfo){
		
		MJCardMgr cardMgr = this;
		
		Collections.sort(allHandCardIDList, new Comparator<Integer>() {
            @Override
            public int compare(Integer firstCardID, Integer secondCardID) {
            	
            	MJCard firstMJCard = cardMgr.getCardByCardID(firstCardID);
            	MJCard secondMJCard = cardMgr.getCardByCardID(secondCardID);
            	if(firstMJCard == null){
            		MJCardMgr.getInstance().error("sortCardIDList not find firstCardID:{}", firstCardID);
            		return 1;
            	}
            	if(secondMJCard == null){
            		MJCardMgr.getInstance().error("sortCardIDList not find secondCardID:{}", secondCardID);
            		return 1;
            	}
            	
            	//������ǰ��
            	if(jokerMJCardInfo.containsKey(secondCardID) && !jokerMJCardInfo.containsKey(firstCardID)){
            		return 1;
            	}
            	
            	if(!jokerMJCardInfo.containsKey(secondCardID) && jokerMJCardInfo.containsKey(firstCardID)){
            		return -1;
            	}
            	
            	if(fakeJokerMJCardInfo.containsKey(firstCardID)){
            		firstCardID = fakeJokerMJCardInfo.get(firstCardID).cardID;
            	}
            	
            	if(fakeJokerMJCardInfo.containsKey(secondCardID)){
            		secondCardID = fakeJokerMJCardInfo.get(secondCardID).cardID;
            	}
            	
            	// ��С����
                return firstCardID - secondCardID;
            }
        });
	}
	
	//------------------------------------�������ýӿ�------------------------------------------------------
	
	//��ȡָ�����͵����п���
	public Hashtable<Integer, MJCard> getMJCardDictByCardType(int cardType){
		
		if(cardType == MJCardDefine.WAN){
			return this.wanCardDict;
		}
		else if(cardType == MJCardDefine.TIAO){
			return this.tiaoCardDict;
		}
		else if(cardType == MJCardDefine.TONG){
			return this.tongCardDict;
		}
		else if(cardType == MJCardDefine.FENG){
			return this.fengCardDict;
		}
		else if(cardType == MJCardDefine.JIAN){
			return this.jianCardDict;
		}
		else if(cardType == MJCardDefine.HUA){
			return this.huaCardDict;
		}
		else if(cardType == MJCardDefine.JI){
			return this.jiCardDict;
		}			
		else if(cardType == MJCardDefine.BAIDA){
			return this.baidaCardDict;
		}
		else if(cardType == MJCardDefine.SPE){
			return this.speCardDict;
		}
		else{
			this.error("cardType:{} error", cardType);
		}
		
		return new Hashtable<>();
	}
	
	//��ȡ���п������Ͷ�Ӧ��valueֵ�б�
	public List<Integer> getAllValueListByCardType(int cardType){
		List<Integer> allValueList = null;
		if(cardType == MJCardDefine.WAN || cardType == MJCardDefine.TIAO || cardType == MJCardDefine.TONG){
			allValueList = MJCardDefine.ZiPaiValueList;
		}
		else if(cardType == MJCardDefine.FENG){
			allValueList = MJCardDefine.FengPaiValueList;
		}
		else if(cardType == MJCardDefine.JIAN){
			allValueList = MJCardDefine.JianPaiValueList;
		}
		else if(cardType == MJCardDefine.HUA){
			allValueList = MJCardDefine.HuaPaiValueList;
		}
		else if(cardType == MJCardDefine.JI){
			allValueList = MJCardDefine.JiPaiValueList;
		}
		//���������쳣
		else{
			this.error("getAllValueListByCardType cardType:{} error",cardType);
			return new ArrayList<>();
		}
		return allValueList;
	}
	
	public List<Integer> getAllCardTypeValueListByCardType(int cardType){
		List<Integer> allValueList = null;
		if(cardType == MJCardDefine.WAN){
			allValueList = MJCardDefine.WANCardTypeValueList;
		}
		else if(cardType == MJCardDefine.TIAO){
			allValueList = MJCardDefine.TIAOCardTypeValueList;
		}
		else if(cardType == MJCardDefine.TONG){
			allValueList = MJCardDefine.TONGCardTypeValueList;
		}
		else if(cardType == MJCardDefine.FENG){
			allValueList = MJCardDefine.FengPaiValueList;
		}
		else if(cardType == MJCardDefine.JIAN){
			allValueList = MJCardDefine.JianCardTypeValueList;
		}
		else if(cardType == MJCardDefine.HUA){
			allValueList = MJCardDefine.HuaCardTypeValueList;
		}
		else if(cardType == MJCardDefine.JI){
			allValueList = MJCardDefine.JiCardTypeValueList;
		}
		//���������쳣
		else{
			this.error("getAllValueListByCardType cardType:{} error",cardType);
			return new ArrayList<>();
		}
		return allValueList;
	}
	
	//��ȡ����(����+����)
	public Hashtable<Integer, MJCard> getAllZiCardInfo(){
		Hashtable<Integer, MJCard> allCardDict = new Hashtable<>();
		
		allCardDict.putAll(this.fengCardDict);
		allCardDict.putAll(this.jianCardDict);
		return allCardDict;
	}
	
	public List<Integer> getAllCardIDListByCardTypeValue(int cardTypeValue){
		List<Integer> cardIDList = this.cardTypeValue2CardIDListInfo.get(cardTypeValue);
		if(cardIDList == null){
			this.error("getAllCardIDListByCardTypeValue not find cardTypeValue:{}", cardTypeValue);
			return new ArrayList<>();
		}
		//����һ��
		return new ArrayList<>(cardIDList);
	}
	//------------------------------------��ȡһ���齫------------------------------------------------------
	
	//-------------��������齫��������----------------
	public Hashtable<Integer, MJCard> getLAMIMJAllCardInfo(){
		Hashtable<Integer, MJCard> allCardDict = new Hashtable<>(this.ziCardDict);
		
		//2��ȥ��2��
		allCardDict.remove(1201);
		allCardDict.remove(1202);
		
		//2��ȥ��1��
		allCardDict.remove(2201);
		
		//2Ͳȥ��1��
		allCardDict.remove(3201);
		
		allCardDict.putAll(this.fengCardDict);
		allCardDict.putAll(this.jianCardDict);
		
		allCardDict.putAll(this.huaCardDict);
		allCardDict.putAll(this.jiCardDict);
		
		//���һ������
		allCardDict.putAll(this.ji2CardDict);
		
				
		return allCardDict;
	}
	
	//�����齫���й���
	public Hashtable<Integer, MJCard> getLAMIMJAllJokerCardInfo(){
		
		Hashtable<Integer, MJCard> jokerMJCardInfo = new Hashtable<>();
		
		jokerMJCardInfo.putAll(this.huaCardDict);
		jokerMJCardInfo.putAll(this.jiCardDict);
		jokerMJCardInfo.putAll(this.ji2CardDict);
		
		return jokerMJCardInfo;
	}
	
	//�����齫�ǹ����������
	
	public List<Integer> getALMIMJAllNotJokerCardTypeList(){
		List<Integer> cardTypeList = new ArrayList<>();
		
		cardTypeList.add(MJCardDefine.WAN);
		cardTypeList.add(MJCardDefine.TIAO);
		cardTypeList.add(MJCardDefine.TONG);
		cardTypeList.add(MJCardDefine.FENG);
		cardTypeList.add(MJCardDefine.JIAN);
		
		return cardTypeList;
	}
	
	
	//---------------��ȡ�����齫���п���--------------------
	public Hashtable<Integer, MJCard> getHZMJAllCardInfo(){
		Hashtable<Integer, MJCard> allCardDict = new Hashtable<>(this.ziCardDict);
		
		for(MJCard cardObj:this.jianCardDict.values()){
			
			//׷��4�ź���
			if(cardObj.cardTypeValue == 51){
				allCardDict.put(cardObj.cardID, cardObj);
			}
		}
		
		return allCardDict;
	}
	
	//�����齫���й���
	public Hashtable<Integer, MJCard> getHZMJAllJokerCardInfo(){
		
		Hashtable<Integer, MJCard> jokerMJCardInfo = new Hashtable<>();
		
		for(MJCard cardObj:this.jianCardDict.values()){
			
			//׷��4�ź���
			if(cardObj.cardTypeValue == 51){
				jokerMJCardInfo.put(cardObj.cardID, cardObj);
			}
		}
		
		return jokerMJCardInfo;
	}
	
	//------------------��ȡȪ���齫--------------------------
	//�����齫
	public Hashtable<Integer, MJCard> getQuanZhouMJAllCardInfo(){
		Hashtable<Integer, MJCard> allCardDict = new Hashtable<>(this.ziCardDict);
		
		allCardDict.putAll(this.fengCardDict);
		
		allCardDict.putAll(this.jianCardDict);
		
		allCardDict.putAll(this.huaCardDict);
		
		allCardDict.putAll(this.jiCardDict);
		
		
		return allCardDict;
	}
	
	//���л���
	public Hashtable<Integer, MJCard> getQuanZhouMJAllHuaCardInfo(){
		Hashtable<Integer, MJCard> huaMJCardInfo = new Hashtable<>();
		
		huaMJCardInfo.putAll(this.huaCardDict);
		
		huaMJCardInfo.putAll(this.jiCardDict);
		
		return huaMJCardInfo;
	}
	
	//---------------------�Ͼ��齫----------------------------------
	//���л���(���� ���� ����)
	public Hashtable<Integer, MJCard> getNanJingMJAllHuaCardInfo(){
		Hashtable<Integer, MJCard> huaMJCardInfo = new Hashtable<>();
		
		huaMJCardInfo.putAll(this.huaCardDict);
		
		huaMJCardInfo.putAll(this.jiCardDict);
		
		huaMJCardInfo.putAll(this.jianCardDict);
		
		return huaMJCardInfo;
	}
	
	//---------------------�����齫----------------------------------
	//������(zi ���� ���� ���� �ٴ� 4�Ŵ�װ�)
	public Hashtable<Integer, MJCard> getSuZhouMJAllCardInfo(){
		Hashtable<Integer, MJCard> huaMJCardInfo = new Hashtable<>();
		
		huaMJCardInfo.putAll(this.ziCardDict);
		
		huaMJCardInfo.putAll(this.huaCardDict);
		
		huaMJCardInfo.putAll(this.jiCardDict);
		
		huaMJCardInfo.putAll(this.jianCardDict);
		
		huaMJCardInfo.putAll(this.baidaCardDict);
		//��װ�
		return huaMJCardInfo;
	}
	
	public Hashtable<Integer, MJCard> getSuZhouMJAllHuaCardInfo(){
		Hashtable<Integer, MJCard> huaMJCardInfo = new Hashtable<>();
		
		huaMJCardInfo.putAll(this.huaCardDict);
		
		huaMJCardInfo.putAll(this.jiCardDict);
		
		huaMJCardInfo.putAll(this.jianCardDict);
		
		return huaMJCardInfo;
	}
	
	//---------------------�����齫----------------------------------
	//���л���(���� ���� ����)
	public Hashtable<Integer, MJCard> getFuZhouMJAllHuaCardInfo(){
		Hashtable<Integer, MJCard> huaMJCardInfo = new Hashtable<>();
		
		huaMJCardInfo.putAll(this.fengCardDict);
		
		huaMJCardInfo.putAll(this.huaCardDict);
		
		huaMJCardInfo.putAll(this.jiCardDict);
		
		huaMJCardInfo.putAll(this.jianCardDict);
		
		return huaMJCardInfo;
	}
	
	//�����齫
	public Hashtable<Integer, MJCard> getFuZhouMJAllCardInfo(){
		Hashtable<Integer, MJCard> allCardDict = new Hashtable<>(this.ziCardDict);
		
		allCardDict.putAll(this.fengCardDict);
		
		allCardDict.putAll(this.jianCardDict);
		
		allCardDict.putAll(this.huaCardDict);
		
		allCardDict.putAll(this.jiCardDict);
		
		
		return allCardDict;
	}
	
}
