package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.List;

import business.global.mjcard.MJCard;

//�����㷨ɸѡ�м����
public class MJMatchCardInfo {
	public MJGroupCardInfo info = new MJGroupCardInfo();           	//��������������
	public List<Integer> leftCardIDList = new ArrayList<>(); 	//ʣ�������
	public List<MJCard> useJokerMJCardList = new ArrayList<>(); //ʹ�õ��Ĺ���
}
