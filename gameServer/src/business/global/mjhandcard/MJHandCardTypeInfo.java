package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.List;

import business.global.mjcard.MJCard;

//�������һ��cardType��Ӧ��������Ϣ
public class MJHandCardTypeInfo {
	//��������
	public int cardType = 0;
	
	//���ܴ���һ����cardID(���Ƽ���ʱ) ��Ӧ��ʹ��Hashtable,ʹ��2���б�洢
	public List<Integer> cardIDList = new ArrayList<>();
	public List<MJCard> mjCardList = new ArrayList<>();

}
