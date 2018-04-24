package core.server;

import java.io.File;

import BaseCommon.CommLog;
import business.global.mjcard.MJCardMgr;
import business.global.poker.PokerManager;
import core.game.poker_nn.NNCardUtil;
import core.game.poker_sss.SSSPokerCardUtil;

public class GameServer {
	public static GameServer app = null;

    public static void main(String[] args){
        app = new GameServer();
    }
    
    public GameServer(){
    	
        String logbackdir = "conf" + File.separator + "logback_game.xml";
        System.setProperty("logback.configurationFile", logbackdir);
        System.setProperty("logback.configurationFile_bak", logbackdir);
        System.setProperty("game_sid", "");
        
    	CommLog.initLog();
    	
    	this.initLogic();
    	
    	this.afterInit();
    }
    
    private void initLogic(){
    	MJCardMgr.getInstance().init();
    	PokerManager.getInstance().init();
    }
    
    private void afterInit(){
    	
    	//FZCardUtil.getInstance().onStart();
    	
    	//SSSPokerCardUtil.getInstance().onStart();
    	
    	NNCardUtil.getInstance().onStart();
    
    }
}
