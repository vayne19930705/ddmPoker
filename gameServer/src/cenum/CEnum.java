package cenum;

public class CEnum {
	
	//出牌动作
	public static enum OpType {
		None(0),
		//麻将
		Hu(1), // 胡
		Peng(2), // 碰
		Gang(3), // 补杠
		JieGang(4), // 接杠
		AnGang(5), // 暗杠
		Chi(6), // 吃
		Out(7), // 出牌
		Pass(8), // 过
		QiangGangHu(9), // 抢杠胡(无用)
		
		//msy
		CuoPai(10), //搓牌
		KaiPai(11), //开牌
		BuPai(12), //补牌
		BiPai(13), //比牌
		
		//nn
		Ready(14),//牌好牌了
		//bj
		TouXiang(15),
		
		NotBuPai(16), //不补牌
		CuoPiaFinish(17), //搓牌结束
		
		//拉米
		JieCard(18),//接牌
		ShowCard(19),//亮牌
		TianHu(20), // 天胡
		
		//德州扑克
		Discard(21),//弃牌
		Follow(22),	//跟注
		AddBet(23),	//加注
		AllIn(24),	//Allin
		AutoPay(25),	//庄前注，大小盲
		
		Gamble(26),//赌五小龙
		
		//泉州麻将
		YouJoker(27), //选择游金(光游)
		//福州麻将
		QiangJoker(28),	//抢金
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
	
	//room_pos状态
	public enum PosState {
		Init(0), //初始化
		WaitTakein(1), //待定带入未在倒计时中完成带入被T出位置
		UnReady(2),//未准备
		Ready(3),//准备中
		Playing(4),//正常牌局中
		LeaveTable(5),//留座离桌中 不参与牌局
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
	
	//setPos 状态
	public enum SetPosState {
		Init(0), //初始化可抢庄，下注
		Playing(1), //可执行动作,如:补牌，搓牌,胡,碰,...
		CuoPai(2),//搓牌中
		CuoPaiEnd(3),//搓牌中
		Wait(4),//等待别人执行
	    End(5),//位置本轮动作执行完成
	    QiangZhuang(7), // 抢庄
		XiaZhu(8), // 下注
		JinGong(9),//进贡
		HuanGong(10),//还贡
		JieFeng(11),//接风
		Discard(12),//弃牌
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
	
	//room 状态
	public enum RoomState {
		Init(0), // 准备阶段
		Playing(1), // 游戏阶段
		Prepare(2), // 每局准备阶段
		Pause(3), //暂停状态
		End(4), // 游戏结算，点赞阶段

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
	
	//set 状态
	public enum SetState {
		Init(0), // 开始发牌中
		Playing(1), // 执行操作中
		End(2), // 游戏已结束
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
	
	//round 状态
	public enum SetRoundState {
		Init(0), //初始化
		QiangZhuang(1), // 抢庄round
		XiaZhu(2), // 下注round
		DealHandCard(3),//发牌round
		RandJoker(4), //随机鬼牌round
		Playing(5), // 打牌round
		Waiting(6),//等待中
		End(7),//结束round
		OpenCard(8),//选择明牌round
		JinGong(9),//进贡round
		FlopPoker(10),	//翻牌
		TurnPoker(11),	//转牌
		RiverPoker(12),	//河牌
		DardPoker(13),	//暗牌
		HuanGong(14),//还贡
		WaitAction(15),//等待玩家选择
		BuHua(16),//补花
		LastMoCard(17),//最后摸牌
		GuangYouMoCard(18),//光游摸牌
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
		QiangJoker(1), // 抢金
		ThreeJoker(2), // 三金倒
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
		NotHu(0), // 没胡
		ZiMo(1), // 自摸
		JiePaoHu(2), // 接炮胡
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
		NotFan(0),//没番
		QGH(1), // 抢杠胡
		FHZ(2), // 4红中
		OneColor(3),//清一色
		QiDui(4),//7对胡
		WHG(5),	//无花果
		YaJue(6),	//压绝
		HunYiSe(7),	//混一色
		QQDD(8),	//全球独钓
		HHQiDui(9),	//豪华七对
		CHHQiDui(10),	//超豪华七对
		CCHHQiDui(11),	//超超豪华七对
		MenQing(12),	//门清
		XGKH(13),	//小杠开花
		GSKH(14),	//杠上开花
		DuiDuiHu(15),	//对对胡
		TongTian(16), //通天
		SiHe(17), //四核
		ShuangBaZhi(18),//双八支
		ShuangSiHe(19),//双四核
		TongTianSiHe(20),//通天四核
		TianHu(21),//天胡
		DianPaoYaDang(23),//点炮压档
		ZiMoPingHu(24),//自摸平胡
		ZiMoYaDang(25),//自摸压档
		ThreeJoker(26),//3金倒
		AnYou1Joker(27),//暗游单游胡
		AnYou2Joker(28),//暗游双游胡
		AnYou3Joker(29),//暗游三游胡
		GuangYouHu(30),//光游胡
		JiePaoPingHu(31),//接炮平胡
		XiaoMenQing(32),//小门清
		HDLY(33),//海底捞月
		JinLong(34),//金龙
		JinQue(35),//金雀
		QiangJin(36),//抢金
		WuHuaWuGang(37),//无花无杠
		OneHua(38),//一张花
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
		WuLong(0), // 乌龙
		YiDui(1), // 一对
		LiangDui(2), // 两对
		SanTiao(3), // 三条
		ShunZi(4), // 顺子
		TongHua(5), // 同花
		HuLu(6), // 葫芦
		TieZhi(7), // 铁支
		TongHuaShun(8), // 同花顺
		WuTong(9), // 五同
		LiuDuiBan(10), // 六对半
		SanShunZi(11), // 三顺子
		SanTongHua(12), // 三同花
		SiTaoSanTiao(13), // 四套三条
		SanFenTianXia(14), // 三分天下
		SanTongHuaShun(15), // 三同花顺
		YiTiaoLong(16), // 一条龙
		ZhiZunQingLong(17), // 至尊青龙

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
    	OnePay(1),//放胡单赔
        AllPay(2), //放胡全陪
        
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