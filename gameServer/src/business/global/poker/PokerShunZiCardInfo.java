package business.global.poker;

import java.util.ArrayList;
import java.util.List;

//˳�Ӷ���
public class PokerShunZiCardInfo {
	
	public String cardName = "";//˳������

	public List<Integer> srcCardIDList = new ArrayList<>(); //ɸѡ������˳��(����δ�滻)
	public List<Integer> showCardIDList = new ArrayList<>();//��ʾ��˳��,���Ʊ��滻Ϊָ��cardID
	
	public List<Integer> useJokerIDList = new ArrayList<>();//ʹ�õ��Ĺ���ID�б�
}
