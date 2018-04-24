package core.game.poker_nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import business.global.log.BaseLog;
import business.global.poker.Poker;
import business.global.poker.PokerManager;

public class NNCardUtil extends BaseLog {

	// ��������-����ֵ("1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
	// "s", "w")_maxStringValueCardID

	// �������Ͷ�Ӧ�������ַ���:

	// ��Сţ s_x_n
	public String CardName_FiveLittleNiu = "s_x_n";
	// ��ը
	public String CardName_BoomNiu = "s_t_n";
	// �廨ţ
	public String CardName_FlowerNiu = "s_h_n";
	// ţţ c_a_n_19 c_a_n_29
	public String CardName_NiuNiu = "c_a_n";
	// ţ9-ţ1 c_9_n- c_1_n
	public String CardName_NiuPoint = "c_%s_n";
	// ��ţ
	public String CardName_NotNiu = "c_0_n";

	private static NNCardUtil instance = new NNCardUtil();

	public PokerManager pokerManager = null;

	protected ArrayList<Integer> allJokerCardIDList = new ArrayList<>(); // ���й���

	public static NNCardUtil getInstance() {
		return instance;
	}

	public NNCardUtil() {
		this.init();
	}

	public String getLogString() {
		return "[NNCardUtil]\t";
	}

	public void init() {
		this.pokerManager = PokerManager.getInstance();

		// ��ʼ������
		ArrayList<Integer> jokerCardIDList = pokerManager.getJokerCardIDList();

		this.allJokerCardIDList.addAll(jokerCardIDList);
	}

	// -------------���---------------
	// �ⲿ�������
	public void onStart() {

		// �������5��
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(110, 111, 112, 113, 213));

		// TODO:��ȡ������ţ��
		NNPointCardInfo cardInfo = this.getCardInfo(handCardIDList, this.allJokerCardIDList);

		this.info(":{} ���:{},{}", handCardIDList, cardInfo.cardName, cardInfo.endCardIDList);
	}

	// ��ȡ���ƽ��
	public NNPointCardInfo getCardInfo(List<Integer> cardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();

		if (cardIDList.size() != 5) {
			this.error("getCardInfo cardIDList:{} error", cardIDList);
			return cardInfo;
		}
		// ����һ��
		List<Integer> allCardIDList = new ArrayList<>(cardIDList);
		allCardIDList.sort((x, y) -> {
			return y - x;
		});

		List<Poker> pokerList = this.pokerManager.getPokerList(allCardIDList);

		// �Ƿ���5Сţ
		cardInfo = this.s1_FiveLittleNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// �Ƿ��� ��ը
		cardInfo = this.s2_BoomNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// �廨ţ
		cardInfo = this.s3_FlowerNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// ��ȡ���Ϳ��ܵĵ���
		cardInfo = this.c1_point(pokerList, allCardIDList, jokerIDList);

		return cardInfo;
	}

	// ��Сţ
	public NNPointCardInfo s1_FiveLittleNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		int sumPoint = 0;
		Boolean isLessThanFive = true;
		Boolean isFiveLittleNiu = false;

		for (Poker poker : cards) {
			if (jokerIDList.contains(poker.id)) {
				sumPoint++; // ��ʱ��Ĭ�Ϲ�������ֵΪ1
				continue;
			} else if (poker.value < 5) // ����ֵС��5�������ܺͣ���������ѭ��
			{
				sumPoint += poker.value;
			} else // ����ֵ����5���Ҳ��ǹ��ƣ��ж��޷�������Сţ,����ѭ��
			{
				isLessThanFive = false;
				break;
			}
		}

		if (isLessThanFive) {
			if (sumPoint > 10)
				isFiveLittleNiu = false;
			else
				isFiveLittleNiu = true;
		}

		if (isFiveLittleNiu) // �������Сţ�������Ƶ�������Ϣ & ������ID��Ϣ
		{
			cardInfo.cardName = CardName_FiveLittleNiu;
			cardInfo.endCardIDList.addAll(allCardIDList);
			cardInfo.endCardIDList.sort((x, y) -> {
				if (jokerIDList.contains(x) && jokerIDList.contains(y)) // ���Ź���
				{
					return y - x;
				} else if (jokerIDList.contains(x) || jokerIDList.contains(y)) // һ�Ź���
				{
					return y - x;
				} else if (x % 100 == y % 100) // ����ֵ���
				{
					return y - x;
				} else // ����ֵ����
				{
					return y % 100 - x % 100;
				}

			});
		}

		return null;
	}

	// ��ը
	public NNPointCardInfo s2_BoomNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		Boolean isBoomNiu = false;
		int sumSame = 0;
		int sumJoker = 0;
		int sameValue = 0;

		List<Poker> jokerPokerList = new ArrayList<>();
		List<Poker> commonPokerList = new ArrayList<>();

		for (Poker poker : cards) // ͳ�ƹ�����
		{
			if (jokerIDList.contains(poker.id)) {
				sumJoker++; // ������+1
			}
		}

		// �ж��Ƿ���ը��ţ
		for (int i = 0; i < cards.size() - 2; i++) {
			if (jokerIDList.contains(cards.get(i).id)) // ����ǹ��ƣ�����
				continue;
			for (int j = i + 1; j < cards.size() - 1; j++) // ���򣬱��������ƣ������ͬ�Ƶ�����
			{
				if (jokerIDList.contains(cards.get(i).id)) // ����ǹ��ƣ�����
				{
					continue;
				} else if (cards.get(i).value == cards.get(j).value) {
					sumSame++;
				}
			}
			if ((sumJoker + sumSame) >= 4) // �����ը��ţ������ѭ��
			{
				isBoomNiu = true;
				sameValue = cards.get(i).value;
				break;
			} else // ����ը��ţ��sumSame���㣬����ͳ��
			{
				sumSame = 0;
			}
		}

		if (isBoomNiu) {
			cardInfo.cardName = CardName_BoomNiu;
			Integer[] temp = allCardIDList.toArray(new Integer[allCardIDList.size()]);
			for (int i = 0; i < temp.length; i++) // �ҳ�Ψһһ�Ų�ͬ���ƣ�������õ����һ���Ƶ�λ��
			{
				if (jokerIDList.contains(temp[i])) // ����ǹ��ƣ�����
					continue;
				if (sameValue != temp[i] % 100) // ���ǹ��ƣ������ж���λ��
				{
					if (i == temp.length - 1) // ���ǹ��ƣ����Ѿ������һ���Ƶ�λ�ã���ʲô������������ѭ��
					{
						break;
					} else // ���ǹ��ƣ��Ҳ������һ�ŵ�λ�ã�������õ����һ���ƣ���ԭ����λ�÷���sameValue
					{
						temp[temp.length - 1] = temp[i];
						temp[i] = sameValue;
					}
				}
			}
			for (int i = 0; i < temp.length; i++) {
				cardInfo.endCardIDList.add(temp[i]);
			}
		}

		return cardInfo;

	}

	// �廨ţ
	public NNPointCardInfo s3_FlowerNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		Boolean isFlowerNiu = true;

		for (Poker poker : cards) {
			if (jokerIDList.contains(poker.id)) {
				continue;
			} else if (poker.value < 11) {
				isFlowerNiu = false;
				break;
			}
		}

		if (isFlowerNiu) {
			cardInfo.cardName = CardName_FlowerNiu;
			cardInfo.endCardIDList.addAll(allCardIDList);
			cardInfo.endCardIDList.sort((x, y) -> {
				if (jokerIDList.contains(x) && jokerIDList.contains(y)) // ���Ź���
				{
					return y - x;
				} else if (jokerIDList.contains(x) || jokerIDList.contains(y)) // һ�Ź���
				{
					return y - x;
				} else if (x % 100 == y % 100) // ����ֵ���
				{
					return y - x;
				} else // ����ֵ����
				{
					return y % 100 - x % 100;
				}

			});
		}

		return cardInfo;
	}

	// ��ȡ���Ϳ��ܵĵ���
	public NNPointCardInfo c1_point(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		int sumJoker = 0;

		for (Poker poker : cards) // ͳ�ƹ�������
		{
			if (jokerIDList.contains(poker.id)) {
				sumJoker++;
			}
		}

		if (sumJoker == 2) // 2�Ź��ƣ��ض�Ϊţţ��ֱ��������С�ǰ������һ�Ź��ƣ���������һ�Ź��ƣ���Ϊ�������
		{
			cardInfo.cardName = CardName_NiuNiu;
			Integer[] tempList = allCardIDList.toArray(new Integer[allCardIDList.size()]);
			int count = 0;
			for (int i = 0; i < tempList.length; i++) // ��1�ź͵�����Ϊ���ƣ���������
			{
				if (jokerIDList.contains(tempList[i])) {
					if (count == 0) {
						if (i < 3) {
							continue;
						} else {
							int tempa = tempList[i];
							tempList[i] = tempList[0];
							tempList[0] = tempa;
						}
						count++;
					} else {
						if (i >= 3) {
							continue;
						} else {
							int tempa = tempList[i];
							tempList[i] = tempList[3];
							tempList[3] = tempa;
						}
					}
				}

			}

			for (int i = 0; i < tempList.length; i++) {
				cardInfo.endCardIDList.add(tempList[i]);
			}

		} else if (sumJoker == 1) // 1�Ź��ƣ�����Ϊţţ��Ҳ����ΪţX
		{
			int[] index3 = new int[3];
			index3 = existNumber(cards, jokerIDList);
			if (index3 != null) // ţţ
			{
				cardInfo.cardName = CardName_NiuNiu;
				for (int i : index3) {
					cardInfo.endCardIDList.add(i);
				}
				for (int j : allCardIDList) {
					if (cardInfo.endCardIDList.contains(j)) {
						continue;
					} else {
						cardInfo.endCardIDList.add(j);
					}
				}

			} else // ţX
			{
				int[] index2 = new int[2];
				index2 = maxNiu(cards, jokerIDList);
				if (index2 != null) {
					cardInfo.cardName = CardName_NiuPoint;
					for (int i : allCardIDList) {
						if (i != index2[0] && i != index2[1]) {
							cardInfo.endCardIDList.add(i);
						}
					}
					cardInfo.endCardIDList.add(index2[0]);
					cardInfo.endCardIDList.add(index2[1]);
				}
			}

		} else if (sumJoker == 0) {
			int[] index3 = new int[3];
			index3 = existNumber(cards, jokerIDList);
			if (index3 != null) // ţx
			{
				cardInfo.cardName = String.format(CardName_NiuPoint, 9);
				for (int i : index3) {
					cardInfo.endCardIDList.add(i);
				}
				for (int j : allCardIDList) {
					if (cardInfo.endCardIDList.contains(j)) {
						continue;
					} else {
						cardInfo.endCardIDList.add(j);
					}
				}

			} else // ��ţ
			{
				cardInfo.cardName = CardName_NotNiu;
				cardInfo.endCardIDList.addAll(allCardIDList);
			}
		}
		return cardInfo;

	}

	public int[] existNumber(List<Poker> cards, List<Integer> jokerIDList) {
		int[] index = { -1, -1, -1 };
		int sumPoint3 = 0; // 3���Ƶĵ���֮��
		int[] tempValue = new int[cards.size()]; // ��ʱ��������
		int i = 0;
		for (Poker poker : cards) {
			tempValue[i++] = poker.value;
		}

		for (int j = 0; j < tempValue.length - 2; j++) // Ѱ�ҵ�����Ϊ10�ı�����3����
		{
			if (jokerIDList.contains(cards.get(j).id)) {
				continue;
			}
			for (int m = j + 1; m < tempValue.length - 1; m++) {
				if (jokerIDList.contains(cards.get(m).id)) {
					continue;
				}
				for (int n = m + 1; n < tempValue.length; n++) {
					if (jokerIDList.contains(cards.get(n).id)) {
						continue;
					} else {
						// j
						if (tempValue[j] < 11) {
							sumPoint3 += tempValue[j];
						} else {
							sumPoint3 += 10;
						}
						// m
						if (tempValue[m] < 11) {
							sumPoint3 += tempValue[m];
						} else {
							sumPoint3 += 10;
						}
						// n
						if (tempValue[n] < 11) {
							sumPoint3 += tempValue[n];
						} else {
							sumPoint3 += 10;
						}
					}

					if (sumPoint3 % 10 == 0) // �������3���ƣ�������ֵ֮��Ϊ10�ı��������¼��i��
					{
						index[0] = cards.get(j).id;
						index[1] = cards.get(m).id;
						index[2] = cards.get(n).id;
					} else {
						sumPoint3 = 0;
					}
				}
			}
		}
		if (index[0] == -1) // �����ڣ��򷵻�null
			index = null;
		return index;

	}

	public int[] maxNiu(List<Poker> cards, List<Integer> jokerIDList) {
		int[] index = { -1, -1 };
		int maxNiu = 0;
		int sumPoint2 = 0; // 2���Ƶĵ���֮��
		int[] tempValue = new int[cards.size()]; // ��ʱ��������
		int i = 0;
		for (Poker poker : cards) {
			tempValue[i++] = poker.value;
		}
		for (int m = 0; m < tempValue.length - 1; m++) {
			if (jokerIDList.contains(cards.get(m).id)) {
				continue;
			}
			for (int n = m + 1; n < tempValue.length; n++) {
				if (jokerIDList.contains(cards.get(n).id)) {
					continue;
				} else {
					// m
					if (tempValue[m] < 11) {
						sumPoint2 += tempValue[m];
					} else {
						sumPoint2 += 10;
					}
					// n
					if (tempValue[n] < 11) {
						sumPoint2 += tempValue[n];
					} else {
						sumPoint2 += 10;
					}
				}

				if (sumPoint2 % 10 > maxNiu) // Ѱ�����ţX
				{
					maxNiu = sumPoint2 % 10;
					index[0] = cards.get(m).id;
					index[1] = cards.get(n).id;
				} else {
					sumPoint2 = 0;
				}
			}
		}
		if (index[0] == -1) {
			index = null;
		}

		return index;
	}

}