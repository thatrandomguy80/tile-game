package game.gfx;

import game.Screen;

public class Font {

	private static String chars =""+"ABCDEFGHIJKLNMOPQRSTUVWXYZ      "+ "0123456789.,:;'\"!?$%()-=+/";
	
	public static void render(String msg, Screen screen, int x, int y, int colour,int scale){
		msg = msg.toUpperCase();
		
		for(int i=0;i<msg.length();i++){
			int charindex= chars.indexOf(msg.charAt(i));
			if(charindex>=0) screen.render(x+(i*8), y, charindex+30*32, colour,0x00,scale);
		}
	}
}
