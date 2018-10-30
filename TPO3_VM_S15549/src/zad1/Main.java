/**
 *
 *  @author Volkov Maksym S15549
 *
 */

package zad1;


public class Main {

  public static void main(String[] args) {
	 
			new Thread()
			{
				public void run() {
					new Server();
				}
			}.start();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}

			new Thread()
			{
				public void run() {
					new Client("Virgil");
				}
			}.start();
			
			new Thread()
			{
				public void run() {
					new Client("Dante");
				}
			}.start();
	  
  }
}
