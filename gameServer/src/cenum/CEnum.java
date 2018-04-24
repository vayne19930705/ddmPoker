package cenum;

public class CEnum {
	
	//���ƶ���
	public static enum OpType {
		None(0),
		//�齫
		Hu(1), // ��
		Peng(2), // ��
		Gang(3), // ����
		JieGang(4), // �Ӹ�
		AnGang(5), // ����
		Chi(6), // ��
		Out(7), // ����
		Pass(8), // ��
		QiangGangHu(9), // ���ܺ�(����)
		
		//msy
		CuoPai(10), //����
		KaiPai(11), //����
		BuPai(12), //����
		BiPai(13), //����
		
		//nn
		Ready(14),//�ƺ�����
		//bj
		TouXiang(15),
		
		NotBuPai(16), //������
		CuoPiaFinish(17), //���ƽ���
		
		//����
		JieCard(18),//����
		ShowCard(19),//����
		TianHu(20), // ���
		
		//�����˿�
		Discard(21),//����
		Follow(22),	//��ע
		AddBet(23),	//��ע
		AllIn(24),	//Allin
		AutoPay(25),	//ׯǰע����Сä
		
		Gamble(26),//����С��
		
		//Ȫ���齫
		YouJoker(27), //ѡ���ν�(����)
		//�����齫
		QiangJoker(28),	//����
		;
		private int value;
		private OpType(int value) {this.value = value;}
		public int value() {return value;}
		public static OpType valueOf(int value) {
			for (OpType flow : OpType.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return OpType.Out;
		}
	};
	
	//room_pos״̬
	public enum PosState {
		Init(0), //��ʼ��
		WaitTakein(1), //��������δ�ڵ���ʱ����ɴ��뱻T��λ��
		UnReady(2),//δ׼��
		Ready(3),//׼����
		Playing(4),//�����ƾ���
		LeaveTable(5),//���������� �������ƾ�
		;
		private int value;
		private PosState(int value) {this.value = value;}
		public int value() {return value;}
		public static PosState valueOf(int value) {
			for (PosState flow : PosState.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return PosState.Init;
		}
	}
	
	//setPos ״̬
	public enum SetPosState {
		Init(0), //��ʼ������ׯ����ע
		Playing(1), //��ִ�ж���,��:���ƣ�����,��,��,...
		CuoPai(2),//������
		CuoPaiEnd(3),//������
		Wait(4),//�ȴ�����ִ��
	    End(5),//λ�ñ��ֶ���ִ�����
	    QiangZhuang(7), // ��ׯ
		XiaZhu(8), // ��ע
		JinGong(9),//����
		HuanGong(10),//����
		JieFeng(11),//�ӷ�
		Discard(12),//����
		AllIn(13),//ALLIN
		;
		private int value;
		private SetPosState(int value) {this.value = value;}
		public int value() {return value;}
		public static SetPosState valueOf(int value) {
			for (SetPosState flow : SetPosState.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return SetPosState.Init;
		}
	}
	
	//room ״̬
	public enum RoomState {
		Init(0), // ׼���׶�
		Playing(1), // ��Ϸ�׶�
		Prepare(2), // ÿ��׼���׶�
		Pause(3), //��ͣ״̬
		End(4), // ��Ϸ���㣬���޽׶�

		;
		private int value;
		private RoomState(int value) {this.value = value;}
		public int value() {return value;}
		public static RoomState valueOf(int value) {
			for (RoomState flow : RoomState.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return RoomState.Init;
		}
	};
	
	//set ״̬
	public enum SetState {
		Init(0), // ��ʼ������
		Playing(1), // ִ�в�����
		End(2), // ��Ϸ�ѽ���
		;
		private int value;
		private SetState(int value) {this.value = value;}
		public int value() {return value;}
		public static SetState valueOf(int value) {
			for (SetState flow : SetState.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return SetState.Init;
		}
	};
	
	//round ״̬
	public enum SetRoundState {
		Init(0), //��ʼ��
		QiangZhuang(1), // ��ׯround
		XiaZhu(2), // ��עround
		DealHandCard(3),//����round
		RandJoker(4), //�������round
		Playing(5), // ����round
		Waiting(6),//�ȴ���
		End(7),//����round
		OpenCard(8),//ѡ������round
		JinGong(9),//����round
		FlopPoker(10),	//����
		TurnPoker(11),	//ת��
		RiverPoker(12),	//����
		DardPoker(13),	//����
		HuanGong(14),//����
		WaitAction(15),//�ȴ����ѡ��
		BuHua(16),//����
		LastMoCard(17),//�������
		GuangYouMoCard(18),//��������
		;
		private int value;
		private SetRoundState(int value) {this.value = value;}
		public int value() {return value;}
		public static SetRoundState valueOf(int value) {
			for (SetRoundState flow : SetRoundState.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return SetRoundState.Init;
		}
	};

	public enum RoundWaitState {
		None(0), // 
		QiangJoker(1), // ����
		ThreeJoker(2), // ����
		;
		private int value;
		private RoundWaitState(int value) {this.value = value;}
		public int value() {return value;}
		public static RoundWaitState valueOf(int value) {
			for (RoundWaitState flow : RoundWaitState.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return RoundWaitState.None;
		}
	};
	
	public enum HuType {
		NotHu(0), // û��
		ZiMo(1), // ����
		JiePaoHu(2), // ���ں�
		;
		private int value;
		private HuType(int value) {this.value = value;}
		public int value() {return value;}
		public static HuType valueOf(int value) {
			for (HuType flow : HuType.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return HuType.NotHu;
		}
	};
	
	public enum FanType {
		NotFan(0),//û��
		QGH(1), // ���ܺ�
		FHZ(2), // 4����
		OneColor(3),//��һɫ
		QiDui(4),//7�Ժ�
		WHG(5),	//�޻���
		YaJue(6),	//ѹ��
		HunYiSe(7),	//��һɫ
		QQDD(8),	//ȫ�����
		HHQiDui(9),	//�����߶�
		CHHQiDui(10),	//�������߶�
		CCHHQiDui(11),	//���������߶�
		MenQing(12),	//����
		XGKH(13),	//С�ܿ���
		GSKH(14),	//���Ͽ���
		DuiDuiHu(15),	//�ԶԺ�
		TongTian(16), //ͨ��
		SiHe(17), //�ĺ�
		ShuangBaZhi(18),//˫��֧
		ShuangSiHe(19),//˫�ĺ�
		TongTianSiHe(20),//ͨ���ĺ�
		TianHu(21),//���
		DianPaoYaDang(23),//����ѹ��
		ZiMoPingHu(24),//����ƽ��
		ZiMoYaDang(25),//����ѹ��
		ThreeJoker(26),//3��
		AnYou1Joker(27),//���ε��κ�
		AnYou2Joker(28),//����˫�κ�
		AnYou3Joker(29),//�������κ�
		GuangYouHu(30),//���κ�
		JiePaoPingHu(31),//����ƽ��
		XiaoMenQing(32),//С����
		HDLY(33),//��������
		JinLong(34),//����
		JinQue(35),//��ȸ
		QiangJin(36),//����
		WuHuaWuGang(37),//�޻��޸�
		OneHua(38),//һ�Ż�
		;
		private int value;
		private FanType(int value) {this.value = value;}
		public int value() {return value;}
		public static FanType valueOf(int value) {
			for (FanType flow : FanType.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return FanType.JiePaoPingHu;
		}
	};

	public enum SSSType {
		WuLong(0), // ����
		YiDui(1), // һ��
		LiangDui(2), // ����
		SanTiao(3), // ����
		ShunZi(4), // ˳��
		TongHua(5), // ͬ��
		HuLu(6), // ��«
		TieZhi(7), // ��֧
		TongHuaShun(8), // ͬ��˳
		WuTong(9), // ��ͬ
		LiuDuiBan(10), // ���԰�
		SanShunZi(11), // ��˳��
		SanTongHua(12), // ��ͬ��
		SiTaoSanTiao(13), // ��������
		SanFenTianXia(14), // ��������
		SanTongHuaShun(15), // ��ͬ��˳
		YiTiaoLong(16), // һ����
		ZhiZunQingLong(17), // ��������

		;
		private int value;
		private SSSType(int value) {this.value = value;}
		public int value() {return value;}
		public static SSSType valueOf(int value) {
			for (SSSType flow : SSSType.values()) {
				if (flow.value == value) {
					return flow;
				}
			}
			return SSSType.WuLong;
		}
	};
	

    
    public enum WHCalcType{
    	OnePay(1),//�ź�����
        AllPay(2), //�ź�ȫ��
        
        ;
        private int value;
        private WHCalcType(int value) {this.value = value;}
        public int value() {return value;}
        public static WHCalcType valueOf(int value) {
            for (WHCalcType flow : WHCalcType.values()) {
                if (flow.value == value) {
                    return flow;
                }
            }
            return WHCalcType.OnePay;
        }
    }

}