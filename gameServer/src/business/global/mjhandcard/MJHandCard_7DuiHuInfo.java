package business.global.mjhandcard;

import java.util.ArrayList;
import java.util.List;

//7�Ժ��ṹ��
public class MJHandCard_7DuiHuInfo {
	//��ɶ��ӵ��������
	public List<MJGroupCardInfo> duiziCardInfoList = new ArrayList<>();
	
	//���ɺ���ʱÿ���Ƶ������Ϣ[[1,1],[3,3]]
	public List<List<Integer>> huGroupCardIDList = new ArrayList<>();
	
	//���Ƶ�����ID�б�[1,2,3,4,5] ����û�б��滻
	public List<Integer> huEndCardIDList = new ArrayList<>();
	
	//���Ƶ�����ID�б�[1,2,3,4,5] ���Ʊ��滻
	public List<Integer> huEndShowCardIDList = new ArrayList<>();
}
