package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.List;

//����齫���ƺ�����Ϣ
public class MJHandCard_HuInfo {
	
	//���պ��� ���������
	public MJGroupCardInfo duiZiCardInfo = new MJGroupCardInfo();
	public List<MJGroupCardInfo> keziCardInfoList = new ArrayList<>();
	public List<MJGroupCardInfo> shunziCardInfoList = new ArrayList<>();
	
	//���ɺ���ʱÿ���Ƶ������Ϣ[[1,2,3],[3,4,5]]
	public List<List<Integer>> huGroupCardIDList = new ArrayList<>();
	
	//���Ƶ�����ID�б�[1,2,3,4,5] ����û�б��滻
	public List<Integer> huEndCardIDList = new ArrayList<>();
	
	//���Ƶ�����ID�б�[1,2,3,4,5] ���Ʊ��滻
	public List<Integer> huEndShowCardIDList = new ArrayList<>();
}
