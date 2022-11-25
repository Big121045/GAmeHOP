/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;
import game.gamerun.Charactor.*;
import game.gamerun.Charactor.Rabbit;
import game.gamerun.Charactor.Environment;
import game.gamerun.Charactor.Wave;


/**
 *
 * @author BIG GER
 */
public class Event {
			public static boolean checkHit(Rabbit rabbit,Wave wave,int rabbitSize,int waveHeight){
							if(rabbit.x+rabbitSize>wave.x&&rabbit.x<wave.x) {
								if(rabbit.y+rabbitSize>=wave.y-waveHeight) {
									return true;
								}
							}
							return false;
			}
			
			public static void gameStop(Wave[] wave,Environment[] env) {

			}

}
