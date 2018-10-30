package zad1;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.FileVisitResult.*;
import java.io.IOException;

public class Futil extends SimpleFileVisitor<Path> {
	
	private Charset in = Charset.forName("Cp1250");
	private Charset out = Charset.forName("UTF-8");
	
	private static FileChannel chanelOut;
	
	private static Path sPath;
	private static Path outPath;
	
	private ByteBuffer buff;

	public Futil(Path outputPath2) {
		try {
			chanelOut = FileChannel.open(outputPath2, EnumSet.of(CREATE, APPEND));
		} catch (IOException e) {
		}
	}
	public static void processDir(String dirName, String resultFileName) {
		sPath = Paths.get(dirName);
		outPath = Paths.get(resultFileName);

		
		try {
			Files.walkFileTree(sPath,new Futil(outPath));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	@Override
	public FileVisitResult visitFile(Path fPath, BasicFileAttributes attribute) {
		try {
			this.recreation(FileChannel.open(fPath), attribute.size());
		} catch (IOException ex) {
		}
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path fPath, IOException exc) {
		return CONTINUE;
	}
	
	private void recreation(FileChannel inputChanel, long sizeOfBuf) {
		buff = ByteBuffer.allocate((int) sizeOfBuf + 1);
		buff.clear();

		try {

			inputChanel.read(buff);
			buff.flip();
			CharBuffer charBuffer = in.decode(buff);
			ByteBuffer byteBuffer = out.encode(charBuffer);

			while (byteBuffer.hasRemaining()) {
				Futil.chanelOut.write(byteBuffer);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	

}
