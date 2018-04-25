package core.game.poker_nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import business.global.log.BaseLog;
import business.global.poker.Poker;
import business.global.poker.PokerManager;

public class NNCardUtil extends BaseLog {

	// 特殊牌名-牌面值("1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
	// "s", "w")_maxStringValueCardID

	// 各种牌型对应牌型名字符串:

	// 五小牛 s_x_n
	public String CardName_FiveLittleNiu = "s_x_n";
	// 四炸
	public String CardName_BoomNiu = "s_t_n";
	// 五花牛
	public String CardName_FlowerNiu = "s_h_n";
	// 牛牛 c_a_n_19 c_a_n_29
	public String CardName_NiuNiu = "c_a_n";
	// 牛9-牛1 c_9_n- c_1_n
	public String CardName_NiuPoint = "c_%s_n";
	// 无牛
	public String CardName_NotNiu = "c_0_n";

	private static NNCardUtil instance = new NNCardUtil();

	public PokerManager pokerManager = null;

	protected ArrayList<Integer> allJokerCardIDList = new ArrayList<>(); // 所有鬼牌

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

		// 初始化鬼牌
		ArrayList<Integer> jokerCardIDList = pokerManager.getJokerCardIDList();

		this.allJokerCardIDList.addAll(jokerCardIDList);
	}

	// -------------入口---------------
	// 外部调用入口
	public void onStart() {

		// 玩家手牌5张
		List<Integer> handCardIDList = new ArrayList<>(Arrays.asList(110, 111, 112, 113, 213));

		// TODO:获取手牌是牛几
		NNPointCardInfo cardInfo = this.getCardInfo(handCardIDList, this.allJokerCardIDList);

		this.info(":{} 结果:{},{}", handCardIDList, cardInfo.cardName, cardInfo.endCardIDList);
	}

	// 获取手牌结果
	public NNPointCardInfo getCardInfo(List<Integer> cardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();

		if (cardIDList.size() != 5) {
			this.error("getCardInfo cardIDList:{} error", cardIDList);
			return cardInfo;
		}
		// 拷贝一份
		List<Integer> allCardIDList = new ArrayList<>(cardIDList);
		allCardIDList.sort((x, y) -> {
			return y - x;
		});

		List<Poker> pokerList = this.pokerManager.getPokerList(allCardIDList);

		// 是否是5小牛
		cardInfo = this.s1_FiveLittleNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// 是否是 四炸
		cardInfo = this.s2_BoomNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// 五花牛
		cardInfo = this.s3_FlowerNiu(pokerList, allCardIDList, jokerIDList);
		if (cardInfo != null) {
			return cardInfo;
		}

		// 获取牌型可能的点数
		cardInfo = this.c1_point(pokerList, allCardIDList, jokerIDList);

		return cardInfo;
	}

	// 五小牛
	public NNPointCardInfo s1_FiveLittleNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		int sumPoint = 0;
		Boolean isLessThanFive = true;
		Boolean isFiveLittleNiu = false;

		for (Poker poker : cards) {
			if (jokerIDList.contains(poker.id)) {
				sumPoint++; // 此时，默认鬼牌牌面值为1
				continue;
			} else if (poker.value < 5) // 牌面值小于5，计入总和，跳过本次循环
			{
				sumPoint += poker.value;
			} else // 牌面值大于5，且不是鬼牌，判定无法构成五小牛,跳出循环
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

		if (isFiveLittleNiu) // 如果是五小牛，返回牌的类型信息 & 排序后的ID信息
		{
			cardInfo.cardName = CardName_FiveLittleNiu;
			cardInfo.endCardIDList.addAll(allCardIDList);
			cardInfo.endCardIDList.sort((x, y) -> {
				if (jokerIDList.contains(x) && jokerIDList.contains(y)) // 两张鬼牌
				{
					return y - x;
				} else if (jokerIDList.contains(x) || jokerIDList.contains(y)) // 一张鬼牌
				{
					return y - x;
				} else if (x % 100 == y % 100) // 牌面值相等
				{
					return y - x;
				} else // 牌面值不等
				{
					return y % 100 - x % 100;
				}

			});
		}

		return null;
	}

	// 四炸
	public NNPointCardInfo s2_BoomNiu(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		Boolean isBoomNiu = false;
		int sumSame = 0;
		int sumJoker = 0;
		int sameValue = 0;

		List<Poker> jokerPokerList = new ArrayList<>();
		List<Poker> commonPokerList = new ArrayList<>();

		for (Poker poker : cards) // 统计鬼牌数
		{
			if (jokerIDList.contains(poker.id)) {
				sumJoker++; // 鬼牌数+1
			}
		}

		// 判断是否是炸弹牛
		for (int i = 0; i < cards.size() - 2; i++) {
			if (jokerIDList.contains(cards.get(i).id)) // 如果是鬼牌，跳过
				continue;
			for (int j = i + 1; j < cards.size() - 1; j++) // 否则，遍历后续牌，检查相同牌的数量
			{
				if (jokerIDList.contains(cards.get(i).id)) // 如果是鬼牌，跳过
				{
					continue;
				} else if (cards.get(i).value == cards.get(j).value) {
					sumSame++;
				}
			}
			if ((sumJoker + sumSame) >= 4) // 如果是炸弹牛，跳出循环
			{
				isBoomNiu = true;
				sameValue = cards.get(i).value;
				break;
			} else // 不是炸弹牛，sumSame置零，继续统计
			{
				sumSame = 0;
			}
		}

		if (isBoomNiu) {
			cardInfo.cardName = CardName_BoomNiu;
			Integer[] temp = allCardIDList.toArray(new Integer[allCardIDList.size()]);
			for (int i = 0; i < temp.length; i++) // 找出唯一一张不同的牌，将其放置到最后一张牌的位置
			{
				if (jokerIDList.contains(temp[i])) // 如果是鬼牌，跳过
					continue;
				if (sameValue != temp[i] % 100) // 不是鬼牌，继续判断其位置
				{
					if (i == temp.length - 1) // 不是鬼牌，但已经在最后一张牌的位置，则什么都不做，跳出循环
					{
						break;
					} else // 不是鬼牌，且不在最后一张的位置，将其放置到最后一张牌，其原本的位置放置sameValue
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

	// 五花牛
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
				if (jokerIDList.contains(x) && jokerIDList.contains(y)) // 两张鬼牌
				{
					return y - x;
				} else if (jokerIDList.contains(x) || jokerIDList.contains(y)) // 一张鬼牌
				{
					return y - x;
				} else if (x % 100 == y % 100) // 牌面值相等
				{
					return y - x;
				} else // 牌面值不等
				{
					return y % 100 - x % 100;
				}

			});
		}

		return cardInfo;
	}

	// 获取牌型可能的点数
	public NNPointCardInfo c1_point(List<Poker> cards, List<Integer> allCardIDList, List<Integer> jokerIDList) {

		NNPointCardInfo cardInfo = new NNPointCardInfo();
		int sumJoker = 0;

		for (Poker poker : cards) // 统计鬼牌张数
		{
			if (jokerIDList.contains(poker.id)) {
				sumJoker++;
			}
		}

		if (sumJoker == 2) // 2张鬼牌，必定为牛牛，直接输出队列。前三张有一张鬼牌，后两张有一张鬼牌，作为输出队列
		{
			cardInfo.cardName = CardName_NiuNiu;
			Integer[] tempList = allCardIDList.toArray(new Integer[allCardIDList.size()]);
			int count = 0;
			for (int i = 0; i < tempList.length; i++) // 第1张和第四张为鬼牌，其余随意
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

		} else if (sumJoker == 1) // 1张鬼牌，可能为牛牛，也可能为牛X
		{
			int[] index3 = new int[3];
			index3 = existNumber(cards, jokerIDList);
			if (index3 != null) // 牛牛
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

			} else // 牛X
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
			if (index3 != null) // 牛x
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

			} else // 无牛
			{
				cardInfo.cardName = CardName_NotNiu;
				cardInfo.endCardIDList.addAll(allCardIDList);
			}
		}
		return cardInfo;

	}

	public int[] existNumber(List<Poker> cards, List<Integer> jokerIDList) {
		int[] index = { -1, -1, -1 };
		int sumPoint3 = 0; // 3张牌的点数之和
		int[] tempValue = new int[cards.size()]; // 临时牌面数组
		int i = 0;
		for (Poker poker : cards) {
			tempValue[i++] = poker.value;
		}

		for (int j = 0; j < tempValue.length - 2; j++) // 寻找点数和为10的倍数的3张牌
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

					if (sumPoint3 % 10 == 0) // 如果存在3张牌，其牌面值之和为10的倍数，则记录下i。
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
		if (index[0] == -1) // 不存在，则返回null
			index = null;
		return index;

	}

	public int[] maxNiu(List<Poker> cards, List<Integer> jokerIDList) {
		int[] index = { -1, -1 };
		int maxNiu = 0;
		int sumPoint2 = 0; // 2张牌的点数之和
		int[] tempValue = new int[cards.size()]; // 临时牌面数组
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

				if (sumPoint2 % 10 > maxNiu) // 寻找最大牛X
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
