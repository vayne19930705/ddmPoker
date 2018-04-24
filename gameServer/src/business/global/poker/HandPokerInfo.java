package business.global.poker;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HandPokerInfo {
	//--------ԭʼ����---------
	//��ʼ�������ID�б�
	public List<Integer> srcCardIDList = new ArrayList<>();
	
	//�淨���й����ֵ�
	public Hashtable<Integer, Poker> allJokerPokerInfo = new Hashtable<>();
	
	//---------�м�����-----------
	//���������Ƶ�color�ֵ�{color:HandPokerColorInfo}
	public Hashtable<Integer, HandPokerColorInfo> color2CardInfo = new Hashtable<>();
	
	//���������Ƶ�value�ֵ�{value:HandPokerColorInfo}
	public Hashtable<Integer, HandPokerValueInfo> value2CardInfo = new Hashtable<>();
	
	//����ӵ�й����б�
	public List<Poker> handJokerPokerList = new ArrayList<>();
	//����ӵ�й����б�
	public List<Integer> handJokerCardIDList = new ArrayList<>();
	
	//���ǹ��Ƶ�ID�б�
	public List<Integer> handNotJokerCardIDList = new ArrayList<>();
}
