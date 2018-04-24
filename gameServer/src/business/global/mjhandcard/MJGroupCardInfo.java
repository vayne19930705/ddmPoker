package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.List;

import business.global.log.BaseLog;
import business.global.mjcard.MJCard;

public class MJGroupCardInfo extends BaseLog{
	public List<Integer> useHandCardIDList = new ArrayList<>(); //����ʱ,ʹ�õ�������
	public List<Integer> srcCardIDList = new ArrayList<>(); //������IDL�б� ���ƺ�ԭʼ�Ŀ���ID(����δ�滻)
	public List<Integer> srcCardTypeValueList = new ArrayList<>(); //ƾ�����͵���ϵ�cardTypeValue�б�(����δ�滻)
	
	public List<Integer> showCardTypeValueList = new ArrayList<>(); //ƾ�����͵���ϵ�cardTypeValue�б�(���ӱ��滻)
	public List<Integer> showCardIDList = new ArrayList<>(); //������IDL�б� ���ƺ���ʾ�Ŀ���ID(���ӱ��滻)
	
	
	public String getLogString(){
		return "[MJGroupCardInfo]\t";
	}
	
	//������ʾ�Ŀ���
	public void addShowCard(MJCard cardObj){
		int cardID = cardObj.cardID;
		int cardTypeValue = cardObj.cardTypeValue;
		
		this.useHandCardIDList.add(cardID);
		this.srcCardIDList.add(cardID);
		this.srcCardTypeValueList.add(cardTypeValue);
		
		//ʹ�������cardID
		this.showCardIDList.add(cardID);
		this.showCardTypeValueList.add(cardTypeValue);
	}
	
	public void addShowCardEx(MJCard cardObj){
		int cardID = cardObj.cardID;
		int cardTypeValue = cardObj.cardTypeValue;
		
		this.useHandCardIDList.add(0, cardID);
		this.srcCardIDList.add(0, cardID);
		this.srcCardTypeValueList.add(0, cardTypeValue);
		
		//ʹ�������cardID
		this.showCardIDList.add(0, cardID);
		this.showCardTypeValueList.add(0, cardTypeValue);
	}
	
	//���ӱ������滻����ʾ����
	public void addJokerShowCard(MJCard jokerCardObj, MJCard showCardObj){
		int cardID = jokerCardObj.cardID;
		int cardTypeValue = jokerCardObj.cardTypeValue;
		
		this.useHandCardIDList.add(cardID);
		this.srcCardIDList.add(cardID);
		this.srcCardTypeValueList.add(cardTypeValue);
		
		int showCardTypeValue = showCardObj.cardTypeValue;
		int showCardID = showCardObj.cardID;
		//�洢������ʾ�Ŀ���ID������
		this.showCardIDList.add(showCardID);
		this.showCardTypeValueList.add(showCardTypeValue);

	}
	
	public void addJokerShowCardEx(MJCard jokerCardObj, MJCard showCardObj){
		int cardID = jokerCardObj.cardID;
		int cardTypeValue = jokerCardObj.cardTypeValue;
		
		this.useHandCardIDList.add(0, cardID);
		
		this.srcCardIDList.add(0, cardID);
		this.srcCardTypeValueList.add(0, cardTypeValue);
		
		int showCardTypeValue = showCardObj.cardTypeValue;
		int showCardID = showCardObj.cardID;
		//�洢������ʾ�Ŀ���ID������
		this.showCardIDList.add(0, showCardID);
		this.showCardTypeValueList.add(0, showCardTypeValue);

	}
	
	public void replaceJoker(int jokerCardID, MJCard jokerCardObj){
		
		int useIndex = this.useHandCardIDList.indexOf(jokerCardID);
		if(useIndex == -1){
			this.error("replaceJoker useHandCardIDList not find jokerCardID:{}", jokerCardID);
			return;
		}
		
		
		int newJokerCardID = jokerCardObj.cardID;
		int newJokerCardTypeValue = jokerCardObj.cardTypeValue;
		
		this.useHandCardIDList.set(useIndex, newJokerCardID);
		this.srcCardIDList.set(useIndex, newJokerCardID);
		this.srcCardTypeValueList.set(useIndex, newJokerCardTypeValue);
	}
	
	public MJGroupCardInfo copy(){
		MJGroupCardInfo copyInfo = new MJGroupCardInfo();
		copyInfo.useHandCardIDList = new ArrayList<>(useHandCardIDList);
		copyInfo.srcCardIDList = new ArrayList<>(srcCardIDList);
		copyInfo.srcCardTypeValueList = new ArrayList<>(srcCardTypeValueList);
		
		copyInfo.showCardTypeValueList = new ArrayList<>(showCardTypeValueList);
		copyInfo.showCardIDList = new ArrayList<>(showCardIDList);
		
		return copyInfo;
	}
}
