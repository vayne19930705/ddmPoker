package business.global.poker;

import business.global.log.BaseLog;


public class Poker extends BaseLog{
	
	public int id; //101-113,201-213,301-313,401-413,500,600
	public int value; // 数值1-13
	public String show; // 展示 : "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "S", "W"
	public String stringValue; //牌的string值: "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "s", "w"
	public int color = 0; // 1:♦,2:♣,3:♥,4:♠
	public String img = "";
	public String stringValueColor = ""; //this.stringValue+this.color  方块A:11, 方块K:d1, 红桃K:d3  小鬼:s0 大鬼:w0 (唯一值,花色比大小用)
	
	//扑克对象
	public Poker(int id){

		this.id = id;
		//小鬼
		if(id == PokerDefine.sJokerValue){
			this.value = PokerDefine.sJokerValue;
			this.stringValue = PokerDefine.sJokerStringValue;
			this.show = PokerDefine.sJokerShow;
			this.color = 0;
			this.img = "$";
		}
		//大鬼
		else if(id == PokerDefine.bJokerValue){
			this.value = PokerDefine.bJokerValue;
			this.stringValue = PokerDefine.bJokerStringValue;
			this.show = PokerDefine.bJokerShow;
			this.color = 0;
			this.img = "$";
		}
		else{
			
			this.value = id % 100;
			int index = PokerDefine.ValueList.indexOf(this.value);
			if(index == -1){
				this.error("Poker:{} ValueList not find", this.value);
				throw new RuntimeException("Poker id: " + id);
			}
			this.show = PokerDefine.ShowList.get(index);
			this.stringValue = PokerDefine.StringValueList.get(index);
			this.color = id / 100;
			this.img = PokerDefine.ImgList.get(this.color - 1);
		}
		
		this.stringValueColor = this.stringValue + this.color;
	}

	public String getLogString(){
		return String.format("[Poker_%s]\t", this.id);
	}
	
	public String toString(){
		return this.img + this.show;
	}
	  //A最大的 poker玩法
	  public String getStringValue(){
	    //A单张最大,使用e返回
	    if (this.value == 1){
	      return "e";
	    }
	    else {
	      return this.stringValue;
	    }
	  }
}
