package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NIOClass {

	private static Charset charset = Charset.forName("ISO-8859-2");

	public static String readFrom(SocketChannel channel) {
		if (!channel.isOpen())
			return "";
		StringBuilder result = new StringBuilder();
		result.setLength(0);

		ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
		byteBuffer.clear();

		try {
			channel.read(byteBuffer);
		} catch (IOException e) {

		}
		byteBuffer.flip();
		CharBuffer wrt = charset.decode(byteBuffer);
		char c;

		while (wrt.hasRemaining()) {
			c = wrt.get();
			if (c == '\n' || c == '\r') {
				break;
			}

			result.append((char) c);
		}

		return result.toString();
	}

	public static void writeTo(String text, SocketChannel channel) {

		ByteBuffer buffer = charset.encode(text);
		try {
			channel.write(buffer);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
