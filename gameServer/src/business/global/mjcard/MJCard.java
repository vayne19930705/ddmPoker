package business.global.mjcard;

import business.global.log.BaseLog;

public class MJCard extends BaseLog{
	
	public int cardID = 0;//卡牌唯一ID 1101   cardType+cardValue+0+下标(1,2,3,4)
	
	public int cardTypeValue = 0;//牌面值:cardType+cardValue, 11:1万  61:梅  
	
	public int cardType = 0;//牌类型,1:万,2:条,3:筒,4:风牌,5:箭牌,6:花牌,7:季牌,8:百搭,9:特殊牌
	
	public int cardValue = 0;//牌值,1,2,3,4,5,6,7,8,9
	
	public String show = "";//展示:1万,2万,红中,西,北
	
	public MJCard(int cardID){
		this.cardID = cardID;
		this.cardTypeValue = cardID/100;
		this.cardType = this.cardTypeValue/10;
		this.cardValue = this.cardTypeValue - this.cardType*10;
		
		
		if(this.cardType == MJCardDefine.WAN){
			this.show = this.cardValue + MJCardDefine.WANString;
		}
		else if(this.cardType == MJCardDefine.TIAO){
			this.show = this.cardValue + MJCardDefine.TIAOString;
		}
		else if(this.cardType == MJCardDefine.TONG){
			this.show = this.cardValue + MJCardDefine.TONGString;
		}
		else if(this.cardType == MJCardDefine.FENG){
			this.show = MJCardDefine.FENGStingInfo.get(this.cardValue);
		}
		else if(this.cardType == MJCardDefine.JIAN){
			this.show = MJCardDefine.JIANStingInfo.get(this.cardValue);
		}
		else if(this.cardType == MJCardDefine.HUA){
			this.show = MJCardDefine.HUAStingInfo.get(this.cardValue);
		}
		else if(this.cardType == MJCardDefine.JI){
			this.show = MJCardDefine.JIStingInfo.get(this.cardValue);
		}
		else if(this.cardType == MJCardDefine.BAIDA){
			this.show = MJCardDefine.BAIDAString;
		}
		else if(this.cardType == MJCardDefine.SPE){
			this.show = MJCardDefine.SPEStingInfo.get(this.cardValue);
		}
		else{
			this.error("cardType:{} error", this.cardType);
		}
		
		if(this.show == null){
			this.error("cardType:{} cardValue:{} erro", this.cardType, this.cardValue);
			this.show = "Error";
		}
		
	}
	
	public String getLogString(){
		return String.format("[MJCard_%s]\t", cardID);
	}

	public String toString(){
		return this.show;
	}
	
}
