	//------------------------------普通牌型---------------------------------
	//获取乌龙牌型
	public SSSDaoCardInfo c_0_WuLong(List<Integer> cardIDList){
		List<Integer> tempCardIDList = new ArrayList<>(cardIDList);
		
		//直接排序,AKQJ...2
		this.pokerManager.sortCardIDListMaxA(tempCardIDList, false);
		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = tempCardIDList;
		daoCardInfo.showCardIDList = tempCardIDList;
		daoCardInfo.cardName = CardName_WuLong + "_" + this.getSSSDaoStringValue(tempCardIDList);
		return daoCardInfo;
	}
	
	//获取一对牌型
	public SSSDaoCardInfo c_1_YiDui(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		//鬼牌列表会被修改需要拷贝一份
		List<Integer> tempCardIDList = new ArrayList<>(cardIDList);
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);

		PokerGroupCardInfo groupCardInfo = this.getHandMaxSameValueCardIDList(value2CardInfo, tempHandJokerCardIDList, 2, true);
		if(groupCardInfo == null){
			return null;
		}
		
		//删除对子
		this.removeCardByCardIDList(tempCardIDList, groupCardInfo.srcCardIDList);
		
		//最佳剩余的一张牌,或者3张 A-K-2
		this.pokerManager.sortCardIDListMaxA(tempCardIDList, false);
		
		groupCardInfo.srcCardIDList.addAll(tempCardIDList);
		groupCardInfo.showCardIDList.addAll(tempCardIDList);
		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = groupCardInfo.srcCardIDList;
		daoCardInfo.showCardIDList = groupCardInfo.showCardIDList;
		daoCardInfo.cardName = CardName_YiDui + "_" + this.getSSSDaoStringValue(groupCardInfo.showCardIDList);
		return daoCardInfo;
	}
	
	//获取2对
	public SSSDaoCardInfo c_2_LiangDui(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		//鬼牌列表会被修改需要拷贝一份
		List<Integer> tempCardIDList = new ArrayList<>(cardIDList);
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);
		
		PokerGroupCardInfo firstGroupCardInfo = this.getHandMaxSameValueCardIDList(value2CardInfo, tempHandJokerCardIDList, 2, true);
		if(firstGroupCardInfo == null){
			return null;
		}
		
		//删除第一个对子
		this.removeCardByCardIDList(tempCardIDList, firstGroupCardInfo.srcCardIDList);
		
		HandPokerInfo tempHandPokerInfo = this.pokerManager.getHandInfo(tempCardIDList, allJokerIDList);
		
		PokerGroupCardInfo secondGroupCardInfo = this.getHandMaxSameValueCardIDList(tempHandPokerInfo.value2CardInfo, tempHandPokerInfo.handJokerCardIDList, 2, true);
		if(secondGroupCardInfo == null){
			return null;
		}
		//删除第2个对子
		this.removeCardByCardIDList(tempCardIDList, secondGroupCardInfo.srcCardIDList);
		
		//第一个对子
		List<Integer> srcCardIDList = new ArrayList<>(firstGroupCardInfo.srcCardIDList);
		List<Integer> showCardIDList = new ArrayList<>(firstGroupCardInfo.showCardIDList);
		
		//第2个对子
		srcCardIDList.addAll(secondGroupCardInfo.srcCardIDList);
		showCardIDList.addAll(secondGroupCardInfo.showCardIDList);
		
		//最后一张牌
		srcCardIDList.addAll(tempCardIDList);
		showCardIDList.addAll(tempCardIDList);
		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = srcCardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		daoCardInfo.cardName = CardName_LiangDui + "_" + this.getSSSDaoStringValue(showCardIDList);
		return daoCardInfo;
	}
	
	//获取3条,头道是3尖刀
	public SSSDaoCardInfo c_3_SanTiao(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		//鬼牌列表会被修改需要拷贝一份
		List<Integer> tempCardIDList = new ArrayList<>(cardIDList);
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);
		
		PokerGroupCardInfo groupCardInfo = this.getHandMaxSameValueCardIDList(value2CardInfo, tempHandJokerCardIDList, 3, true);
		if(groupCardInfo == null){
			return null;
		}
		//删除3条
		this.removeCardByCardIDList(tempCardIDList, groupCardInfo.srcCardIDList);
		
		//如果是中道或者尾道,最佳剩余的2张 A-K-2
		this.pokerManager.sortCardIDListMaxA(tempCardIDList, false);
		
		groupCardInfo.srcCardIDList.addAll(tempCardIDList);
		groupCardInfo.showCardIDList.addAll(tempCardIDList);
				
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = groupCardInfo.srcCardIDList;
		daoCardInfo.showCardIDList = groupCardInfo.showCardIDList;
		daoCardInfo.cardName = CardName_SanTiao + "_" + this.getSSSDaoStringValue(groupCardInfo.showCardIDList);
		return daoCardInfo;
	}
	
	//获取顺子
	public SSSDaoCardInfo c_4_ShunZi(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		//鬼牌列表会被修改需要拷贝一份
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);
				
		PokerGroupCardInfo groupCardInfo = this.getHandMaxShunZiCardIDList(cardIDList, value2CardInfo, tempHandJokerCardIDList, 5);
		if(groupCardInfo == null){
			return null;
		}	
		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = groupCardInfo.srcCardIDList;
		daoCardInfo.showCardIDList = groupCardInfo.showCardIDList;
		daoCardInfo.cardName = CardName_ShunZi + "_" + this.getShunZiSSSDaoStringValue(groupCardInfo.showCardIDList);
		return daoCardInfo;
	}
	
	//获取同花  
	public SSSDaoCardInfo c_5_TongHua(List<Integer>cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		//鬼牌列表会被修改需要拷贝一份
		List<Integer> tempCardIDList = new ArrayList<>(cardIDList);
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);
		
		int colorCount = color2CardInfo.size();
		//如果有2种颜色
		if(colorCount > 1){
			return null;
		}
		
		
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		PokerGroupCardInfo santiaoGroupCardInfo = this.getHandMaxSameValueCardIDList(value2CardInfo, tempHandJokerCardIDList, 3, true);
		//如果是3条对子
		if(santiaoGroupCardInfo != null){
			srcCardIDList = santiaoGroupCardInfo.srcCardIDList;
			showCardIDList = santiaoGroupCardInfo.showCardIDList;
			//删除对子
			this.removeCardByCardIDList(tempCardIDList, santiaoGroupCardInfo.srcCardIDList);
			
			//A-K-2
			this.pokerManager.sortCardIDListMaxA(tempCardIDList, false);
			
			srcCardIDList.addAll(tempCardIDList);
			showCardIDList.addAll(tempCardIDList);
			
			daoCardInfo.cardName = CardName_TongHuaSanTiao + "_" + this.getSSSDaoStringValue(showCardIDList);
		}
		else{
			PokerGroupCardInfo firstDuiZiGroupCardInfo = this.getHandMaxSameValueCardIDList(value2CardInfo, tempHandJokerCardIDList, 2, true);
			//如果有对子
			if(firstDuiZiGroupCardInfo != null){
				
				List<Integer> tempCardIDList2 = new ArrayList<>(tempCardIDList);
				//删除第一个对子
				this.removeCardByCardIDList(tempCardIDList2, firstDuiZiGroupCardInfo.srcCardIDList);
				
				HandPokerInfo tempHandPokerInfo = this.pokerManager.getHandInfo(tempCardIDList2, allJokerIDList);
				
				PokerGroupCardInfo secondDuiZiGroupCardInfo = this.getHandMaxSameValueCardIDList(tempHandPokerInfo.value2CardInfo, tempHandPokerInfo.handJokerCardIDList, 2, true);
				//如果2对
				if(secondDuiZiGroupCardInfo != null){
					srcCardIDList = firstDuiZiGroupCardInfo.srcCardIDList;
					showCardIDList = firstDuiZiGroupCardInfo.showCardIDList;
					
					srcCardIDList.addAll(secondDuiZiGroupCardInfo.srcCardIDList);
					showCardIDList.addAll(secondDuiZiGroupCardInfo.showCardIDList);
					
					//删除2个对子
					this.removeCardByCardIDList(tempCardIDList, srcCardIDList);
					
					//追加剩余的一张牌
					srcCardIDList.addAll(tempCardIDList);
					showCardIDList.addAll(tempCardIDList);
					
					daoCardInfo.cardName = CardName_TongHuaLiangDui + "_" + this.getSSSDaoStringValue(showCardIDList);
				}
				else{
					
					srcCardIDList = firstDuiZiGroupCardInfo.srcCardIDList;
					showCardIDList = firstDuiZiGroupCardInfo.showCardIDList;
					//删除对子
					this.removeCardByCardIDList(tempCardIDList, firstDuiZiGroupCardInfo.srcCardIDList);
					
					//A-K-2
					this.pokerManager.sortCardIDListMaxA(tempCardIDList, false);
					
					srcCardIDList.addAll(tempCardIDList);
					showCardIDList.addAll(tempCardIDList);
					
					daoCardInfo.cardName = CardName_TongHuaYiDui + "_" + this.getSSSDaoStringValue(showCardIDList);
					
				}
				
			}
			else{
				//排序  A-K-2 不可能有鬼牌
				this.pokerManager.sortCardIDListMaxA(tempCardIDList, false);
				
				srcCardIDList = tempCardIDList;
				showCardIDList = tempCardIDList;
				
				daoCardInfo.cardName = CardName_TongHua + "_" + this.getSSSDaoStringValue(showCardIDList);
			}
		}
		
		daoCardInfo.srcCardIDList = srcCardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		
		return daoCardInfo;
	}
	
	//获取葫芦
	public SSSDaoCardInfo c_6_HuLu(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, List<Integer> allJokerIDList){
		//鬼牌列表会被修改需要拷贝一份
		List<Integer> tempCardIDList = new ArrayList<>(cardIDList);
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);
		
		PokerGroupCardInfo firstGroupCardInfo = this.getHandMaxSameValueCardIDList(value2CardInfo, tempHandJokerCardIDList, 3, true);
		if(firstGroupCardInfo == null){
			return null;
		}
		
		List<Integer> srcCardIDList = firstGroupCardInfo.srcCardIDList;
		List<Integer> showCardIDList = firstGroupCardInfo.showCardIDList;
		
		//删除3条
		this.removeCardByCardIDList(tempCardIDList, srcCardIDList);
		
		HandPokerInfo tempHandPokerInfo = this.pokerManager.getHandInfo(tempCardIDList, allJokerIDList);
		
		//选一个对子
		PokerGroupCardInfo secondGroupCardInfo = this.getHandMaxSameValueCardIDList(tempHandPokerInfo.value2CardInfo, tempHandPokerInfo.handJokerCardIDList, 2, true);
		if(secondGroupCardInfo == null){
			return null;
		}
		srcCardIDList.addAll(secondGroupCardInfo.srcCardIDList);
		showCardIDList.addAll(secondGroupCardInfo.showCardIDList);
		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = srcCardIDList;
		daoCardInfo.showCardIDList = showCardIDList;
		daoCardInfo.cardName = CardName_HuLu + "_" + this.getSSSDaoStringValue(showCardIDList);
		return daoCardInfo;
	}
	
	//获取手牌中最大的4条
	public SSSDaoCardInfo c_7_TieZhi(List<Integer>cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		//鬼牌列表会被修改需要拷贝一份
		List<Integer> tempCardIDList = new ArrayList<>(cardIDList);
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);
		
		PokerGroupCardInfo groupCardInfo = this.getHandMaxSameValueCardIDList(value2CardInfo, tempHandJokerCardIDList, 4, true);
		if(groupCardInfo == null){
			return null;
		}
		
		//删除铁支
		this.removeCardByCardIDList(tempCardIDList, groupCardInfo.srcCardIDList);
		
		//最佳剩余的一张牌
		groupCardInfo.srcCardIDList.addAll(tempCardIDList);
		groupCardInfo.showCardIDList.addAll(tempCardIDList);
				
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		
		daoCardInfo.srcCardIDList = groupCardInfo.srcCardIDList;
		daoCardInfo.showCardIDList = groupCardInfo.showCardIDList;
		daoCardInfo.cardName = CardName_TieZhi + "_" + this.getSSSDaoStringValue(groupCardInfo.showCardIDList);
		
		return daoCardInfo;
		
	}
	//同花顺
	public SSSDaoCardInfo c_8_TongHuaShun(List<Integer> cardIDList, Hashtable<Integer, HandPokerColorInfo> color2CardInfo, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		//鬼牌列表会被修改需要拷贝一份
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);
		
		int colorCount = color2CardInfo.size();
		//如果有2种颜色
		if(colorCount > 1){
			return null;
		}
		
		PokerGroupCardInfo groupCardInfo = this.getHandMaxShunZiCardIDList(cardIDList, value2CardInfo, tempHandJokerCardIDList, 5);
		if(groupCardInfo == null){
			return null;
		}

		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = groupCardInfo.srcCardIDList;
		daoCardInfo.showCardIDList = groupCardInfo.showCardIDList;
		daoCardInfo.cardName = CardName_TongHuaShun + "_" + this.getShunZiSSSDaoStringValue(groupCardInfo.showCardIDList);
		return daoCardInfo;
	}
	
	//获取手牌中最大的5条
	public SSSDaoCardInfo c_9_WuTong(Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList){
		
		PokerGroupCardInfo groupCardInfo = this.getHandMaxSameValueCardIDList(value2CardInfo, handJokerCardIDList, 5, true);
		if(groupCardInfo == null){
			return null;
		}
		
		SSSDaoCardInfo daoCardInfo = new SSSDaoCardInfo();
		daoCardInfo.srcCardIDList = groupCardInfo.srcCardIDList;
		daoCardInfo.showCardIDList = groupCardInfo.showCardIDList;
		daoCardInfo.cardName = CardName_WuTong + "_" + this.getSSSDaoStringValue(groupCardInfo.showCardIDList);
		return daoCardInfo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//获取5张牌组成的最大顺子(该算法只适合3,5张)  筛选结果 按 A-K 牌型完成了
	public PokerGroupCardInfo getHandMaxShunZiCardIDList(List<Integer> cardIDList, Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, int maxCount){
	
		List<Integer> tempCardIDList = new ArrayList<>(cardIDList);
		
		int cardCount = tempCardIDList.size();
		
		if(cardCount != 5 && cardCount != 3){
			this.error("getHandMaxShunZiCardIDList tempCardIDList:{} not allow", tempCardIDList);
			return null;
		}
		
		List<Integer> valueList = new ArrayList<>(value2CardInfo.keySet());
		//TODO:5鬼有BUG
		if(valueList.size() == 0){
			this.error("getHandMaxShunZiCardIDList tempCardIDList:{} not value2CardInfo", tempCardIDList);
			return null;
		}
		
		//取最大的牌面值
		int maxValue = Collections.max(valueList);			
		//取最大牌面值对应的ID队列
		HandPokerValueInfo maxValueInfo = value2CardInfo.get(maxValue);
		//取最大牌面值对应的ID队列的首个ID
		int maxCardID = maxValueInfo.cardIDList.get(0);		
		//顺子向右增长
		List<PokerShunZiCardInfo> allRightShunZiList = this.getAllRightShunZi(value2CardInfo, handJokerCardIDList, maxCardID, maxCount);	
		
		String maxResultString = "0";
		
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		//顺子向右增长后，顺子的张数
		int findRightCount = allRightShunZiList.size();
		
		for(int index=0; index<findRightCount; index++){
			PokerShunZiCardInfo rightShunZiCardInfo = allRightShunZiList.get(index);				
			
			PokerShunZiCardInfo resultShunZiInfo = this.addLeftShunZi(value2CardInfo, handJokerCardIDList, rightShunZiCardInfo, maxCount);
			
			//如果没有满足长度的顺子过滤
			if(resultShunZiInfo.srcCardIDList.size() != maxCount){
				continue;
			}
			
			String resultString = this.getShunZiSSSDaoStringValue(resultShunZiInfo.showCardIDList);
			
			if(resultString.compareTo(maxResultString) > 0){
				maxResultString = resultString;
				srcCardIDList = resultShunZiInfo.srcCardIDList;
				showCardIDList = resultShunZiInfo.showCardIDList;
			}
		}
				
		if(srcCardIDList.size() == maxCount){
			PokerGroupCardInfo groupCardInfo = new PokerGroupCardInfo();
			groupCardInfo.srcCardIDList = srcCardIDList;
			groupCardInfo.showCardIDList = showCardIDList;
			//只单顺子处理不考虑是否是通话顺
			groupCardInfo.cardName = this.CardName_ShunZi + "_" + this.getShunZiSSSDaoStringValue(showCardIDList);
			return groupCardInfo;
		}
				
		return null;
	}
	
	//获取所有用鬼牌追加右边的  半个顺子
	public List<PokerShunZiCardInfo> getAllRightShunZi(Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, int startCardID, int maxCount){
		
		List<PokerShunZiCardInfo> rightShunZiList = new ArrayList<>();
		
		int jokerCount = handJokerCardIDList.size();
		
		//根据鬼牌的数量 筛选可能的组合
		for(int index=0; index<jokerCount; index++){
			
			List<Integer> useJokerIDList = new ArrayList<>(handJokerCardIDList.subList(index, jokerCount));
			
			PokerShunZiCardInfo shunZiCardInfo = this.getRightShunZi(value2CardInfo, useJokerIDList, startCardID, maxCount);
			
			rightShunZiList.add(shunZiCardInfo);
		}
		
		//追加一个没有鬼牌的查找
		PokerShunZiCardInfo shunZiCardInfo = this.getRightShunZi(value2CardInfo, new ArrayList<>(), startCardID, maxCount);
		rightShunZiList.add(shunZiCardInfo);
		
		return rightShunZiList;
	}
	
	
	//找出卡牌右边值拼接的顺子  A K Q J ... 2
	public PokerShunZiCardInfo getRightShunZi(Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, int startCardID, int maxCount){
		PokerShunZiCardInfo shunZiCardInfo = new PokerShunZiCardInfo();
		
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);		
		
		shunZiCardInfo.srcCardIDList.add(startCardID);
		shunZiCardInfo.showCardIDList.add(startCardID);	
		
		Poker startPoker = this.pokerManager.getPokerByCardID(startCardID);	
		int startValue = startPoker.value;
		
		int needCount = maxCount - 1;
		
		//查找顺子左边一个数字
		for(int index=1; index <= needCount; index++){
			
			int rightValue = startValue + index;
			
			int rightCardID = 0;
			
			//如果最后一个了
			if(rightValue >= 14){
				rightValue = 1;
			}
			
			//找到后一个value
			if(value2CardInfo.containsKey(rightValue)){
				HandPokerValueInfo rightValueInfo = value2CardInfo.get(rightValue);
				rightCardID = rightValueInfo.cardIDList.get(0);
				
				shunZiCardInfo.srcCardIDList.add(0, rightCardID);
				shunZiCardInfo.showCardIDList.add(0, rightCardID);			

			}
			//如果有鬼牌 用鬼牌代替
			else if(tempHandJokerCardIDList.size() > 0){
				int rightJokerCardID = tempHandJokerCardIDList.get(0);
				tempHandJokerCardIDList.remove(0);
				
				//获取鬼牌替换的cardID
				rightCardID = this.pokerManager.getSameValueCardIDList(rightValue).get(0);
				
				shunZiCardInfo.srcCardIDList.add(0, rightJokerCardID);
				shunZiCardInfo.showCardIDList.add(0, rightCardID);
				
				shunZiCardInfo.useJokerIDList.add(rightJokerCardID);
			}
			else{
				break;
			}
			
			if(rightValue == 1){
				break;
			}
		}
		
		
		return shunZiCardInfo;
	}
	
	
	//获取左边值的顺子 
	public PokerShunZiCardInfo addLeftShunZi(Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, PokerShunZiCardInfo rightShunZiCardInfo, int maxCount){
		
		List<Integer> tempJokerCardIDList = new ArrayList<>(handJokerCardIDList);
		
		PokerShunZiCardInfo shunZiCardInfo = new PokerShunZiCardInfo();
		
		List<Integer> showCardIDList = rightShunZiCardInfo.showCardIDList;
		List<Integer> srcCardIDList = rightShunZiCardInfo.srcCardIDList;
		List<Integer> useJokerIDList = rightShunZiCardInfo.useJokerIDList;
		
		//移除掉右边已经使用的鬼牌
		this.removeCardByCardIDList(tempJokerCardIDList, useJokerIDList);
		
		//因为顺子是倒序显示的 从末尾取开始的卡牌ID
		int startCardID = srcCardIDList.get(srcCardIDList.size() - 1);		
		
		Poker poker = this.pokerManager.getPokerByCardID(startCardID);
		
		int needCount = maxCount - srcCardIDList.size();
		
		//已经满足需求
		if(needCount == 0){
			shunZiCardInfo = rightShunZiCardInfo;
		}
		//已经超过长度
		else if(needCount < 0){
			this.error("addLeftShunZi srcCardIDList:{},{}", srcCardIDList, showCardIDList);
		}
		else{
			shunZiCardInfo = this.getLeftShunZi(value2CardInfo, tempJokerCardIDList, rightShunZiCardInfo, poker.value, needCount);
		}		
		
		return shunZiCardInfo;
	}
	
	//获取左边值的顺子  
	public PokerShunZiCardInfo getLeftShunZi(Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, PokerShunZiCardInfo rightShunZiCardInfo, int maxValue, int needCount){

		PokerShunZiCardInfo shunZiCardInfo = new PokerShunZiCardInfo();
		
		shunZiCardInfo.srcCardIDList = new ArrayList<>(rightShunZiCardInfo.srcCardIDList);
		shunZiCardInfo.showCardIDList = new ArrayList<>(rightShunZiCardInfo.showCardIDList);
		shunZiCardInfo.useJokerIDList = new ArrayList<>(rightShunZiCardInfo.useJokerIDList);
	
		List<Integer> tempHandJokerCardIDList = new ArrayList<>(handJokerCardIDList);
		
		//查找顺子左边一个数字
		for(int index=1; index <= needCount; index++){
			
			int leftValue = maxValue - index;
			if(leftValue < 1){
				break;
			}
			
			//找到前一个value
			if(value2CardInfo.containsKey(leftValue)){
				HandPokerValueInfo leftValueInfo = value2CardInfo.get(leftValue);
				int leftCardID = leftValueInfo.cardIDList.get(0);
				
				shunZiCardInfo.srcCardIDList.add(leftCardID);
				shunZiCardInfo.showCardIDList.add(leftCardID);			

			}
			//如果有鬼牌 用鬼牌代替
			else if(tempHandJokerCardIDList.size() > 0){
				int leftJokerCardID = tempHandJokerCardIDList.get(0);
				tempHandJokerCardIDList.remove(0);
				
				//获取鬼牌替换的cardID
				int leftCardID = this.pokerManager.getSameValueCardIDList(leftValue).get(0);
				
				shunZiCardInfo.srcCardIDList.add(leftJokerCardID);
				shunZiCardInfo.showCardIDList.add(leftCardID);
				
				shunZiCardInfo.useJokerIDList.add(leftJokerCardID);
			}
			else{
				break;
			}
		}		
		
		return shunZiCardInfo;
	}
	
	
	
		//获取一手牌中最大 相同牌面值的CardIDList(可以超过5张的手牌)
	public PokerGroupCardInfo getHandMaxSameValueCardIDList(Hashtable<Integer, HandPokerValueInfo> value2CardInfo, List<Integer> handJokerCardIDList, int findCardCount, boolean isFindMax){
		
		List<Integer> valueList = new ArrayList<>(value2CardInfo.keySet());
		int count = valueList.size();
		//TODO:5鬼有BUG
		if(count == 0){
			this.error("getHandMaxSameValueCardIDList not find value2CardInfo handJokerCardIDList:{}", handJokerCardIDList);
			return null;
		}
		
		this.pokerManager.sortCardValueList(valueList);
		
		//如果是找最小的,倒序
		if(!isFindMax){
			Collections.reverse(valueList);
		}
		
		PokerGroupCardInfo groupCardInfo = null;
		
		//从大到小查询满足条件的id列表 A最大
		for(int index=0; index < count; index++){
			int value = valueList.get(index);
			
			HandPokerValueInfo valuePokerInfo = value2CardInfo.get(value);
			groupCardInfo = this.getSameValuePokerGroupCardInfo(valuePokerInfo, handJokerCardIDList, findCardCount);
			if(groupCardInfo != null){
				return groupCardInfo;
			}
		}
		
		return null;
	}
	
	
	//查找指定数量的 相同value的 卡牌信息
	public PokerGroupCardInfo getSameValuePokerGroupCardInfo(HandPokerValueInfo valuePokerInfo, List<Integer> handJokerCardIDList, int findCardCount){
		
		List<Integer> srcCardIDList = new ArrayList<>();
		List<Integer> showCardIDList = new ArrayList<>();
		
		int jokerCount = handJokerCardIDList.size();
		
		
		List<Integer> cardIDList = valuePokerInfo.cardIDList;
		
		if(cardIDList.size() >= findCardCount){
			srcCardIDList = new ArrayList<>(cardIDList.subList(0, findCardCount));
			showCardIDList = new ArrayList<>(srcCardIDList);
		}
		else if(cardIDList.size() + jokerCount >= findCardCount){
			
			srcCardIDList = new ArrayList<>(cardIDList);
			showCardIDList = new ArrayList<>(cardIDList);
			int needCount = findCardCount - cardIDList.size();
			
			for(int index_2=0; index_2<needCount; index_2++){
				int jokerCardID = handJokerCardIDList.get(index_2);
				
				srcCardIDList.add(jokerCardID);
				//追加鬼牌替换的牌
				showCardIDList.add(cardIDList.get(0));
			}
		}
		//如果没有找到
		if(srcCardIDList.size() == 0){
			return null;
		}
		PokerGroupCardInfo groupCardInfo = new PokerGroupCardInfo();
		groupCardInfo.srcCardIDList = srcCardIDList;
		groupCardInfo.showCardIDList = showCardIDList;
		return groupCardInfo;
		
	}
	
	