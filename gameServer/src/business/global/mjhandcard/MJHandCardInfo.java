package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import business.global.mjcard.MJCard;

//����齫����������Ϣ

public class MJHandCardInfo {
	//--------ԭʼ����---------
	//��ʼ�������ID�б�
	public List<Integer> handCardIDList = new ArrayList<>();
	
	//�淨���й����ֵ�
	public Hashtable<Integer, MJCard> allJokerMJCardInfo = new Hashtable<>();
	
	//---------�м�����-----------
	//���������Ƶ������ֵ�{cardType:LAMITypeCardInfo}
	public Hashtable<Integer, MJHandCardTypeInfo> cardType2CardInfo = new Hashtable<>();
	
	//����ӵ�й����б�
	public List<MJCard> jokerMJCardList = new ArrayList<>();
	//����ӵ�й����б�
	public List<Integer> jokerCardIDList = new ArrayList<>();
	
	//���ǹ��Ƶ�ID�б�
	public List<Integer> notJokerCardIDList = new ArrayList<>();
	
	//---------���ƽ������-----------
	public List<MJHandCard_HuInfo> allHuInfoList = new ArrayList<>();
}
