/**
 *
 *  @author Volkov Maksym S15549
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
	String nickName;
	SocketChannel clientSocket;

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		new LoginGui();
	}

	public Client(String nickName) {
		this.nickName = nickName;
		ChatGUI chat = new ChatGUI(this);
		SocketChannel socketChannel;

		try {
			socketChannel = SocketChannel.open();
			socketChannel.connect(new InetSocketAddress("localhost", 1337));
			clientSocket = socketChannel;
		} catch (IOException e) {

		}
		new Thread(() -> {
			while (true) {

				String result = NIOClass.readFrom(this.clientSocket);
				if (result.length() > 1) {
					chat.addText(result);
				}
			}
		}).start();

	}

	public void sendMessage(String text) {
		NIOClass.writeTo((this.nickName + ": " + text), this.clientSocket);
	}

}
