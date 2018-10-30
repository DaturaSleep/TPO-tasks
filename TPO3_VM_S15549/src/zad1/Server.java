/**
 *
 *  @author Volkov Maksym S15549
 *
 */

package zad1;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
	private Selector sel;
	private ServerSocketChannel serverChanel;

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		try {
			sel = Selector.open();
			serverChanel = ServerSocketChannel.open();
			serverChanel.configureBlocking(false);
			serverChanel.bind(new InetSocketAddress("localhost", 1337));

			serverChanel.register(sel, SelectionKey.OP_ACCEPT);
		} catch (Exception e) {

		}
		new Thread(() -> {
			while (true) {

				try {
					sel.select();

					Set<?> keys = sel.selectedKeys();
					Iterator<?> iterator = keys.iterator();
					while (iterator.hasNext()) {

						SelectionKey selectionKey = (SelectionKey) iterator.next();
						if (selectionKey.isReadable()) {
							SocketChannel cc = (SocketChannel) selectionKey.channel();
							String result_string = NIOClass.readFrom(cc);
							if (result_string.length() > 0) {
								result_string = result_string + "\n";
								this.toAll(result_string);
							}

						}

						if (selectionKey.isAcceptable()) {
							SocketChannel socketChannel = serverChanel.accept();
							if (socketChannel != null) {
								socketChannel.configureBlocking(false);
								socketChannel.register(sel, (SelectionKey.OP_READ | SelectionKey.OP_WRITE));
							}

						}

					}
				} catch (Exception exc) {

				}
			}
		}).start();

	}

	private void toAll(String msg) {
		try {
			sel.select();
			Set<?> k = sel.selectedKeys();
			Iterator<?> iter = k.iterator();
			while (iter.hasNext()) {
				SelectionKey key = (SelectionKey) iter.next();
				if (key.isWritable()) {
					SocketChannel cc = (SocketChannel) key.channel();
					NIOClass.writeTo(msg + '\n', cc);
				}
			}
		} catch (Exception exc) {

		}
	}

}
