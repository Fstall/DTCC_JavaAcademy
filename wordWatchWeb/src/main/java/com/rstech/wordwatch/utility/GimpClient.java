package com.rstech.wordwatch.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.Socket;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
 

public class GimpClient {
 
	private String host = "localhost";
	private int port = 10008;

	private void run(Reader r) throws Exception {
		StringBuilder schemeScriptBuilder = new StringBuilder();
		int c;
		while ((c = r.read()) != -1)
			schemeScriptBuilder.append((char) c);
		String schemeScript = schemeScriptBuilder.toString();
		if (schemeScript.length() > Short.MAX_VALUE)
			throw new IOException("Script is too large ("
					+ schemeScript.length() + ")");
		byte input[] = schemeScript.getBytes();
		OutputStream out = null;
		InputStream in = null;
		Socket socket = null;
		try {
			socket = new Socket(this.host, this.port);
			// socket.setSoTimeout(10*1000);
			out = socket.getOutputStream();
			in = socket.getInputStream();
			out.write('G');
			short queryLength = (short) input.length;
			out.write(queryLength / 256);
			out.write(queryLength % 256);
			out.write(input);
			out.flush();
			int G = in.read();
			if (G != 'G')
				throw new IOException("Expected first byte as G");
			int code = in.read();
			if (code != 0)
				throw new IOException("Error code:" + code);
			int contentLength = in.read() * 255 + in.read();
			byte array[] = new byte[contentLength];
			int nRead = 0;
			int n = 0;
			while ((n = in.read(array, nRead, array.length - nRead)) > 0) {
				nRead += n;
			}
			if (nRead != contentLength)
				throw new IOException("expected " + contentLength + " but got "
						+ nRead + " bytes");
			System.out.println(new String(array));
		} catch (Exception err) {
			throw new IOException(err);
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (socket != null)
				socket.close();
		}
	}
	
	public void drawWord(String aWord) throws Exception { 
		GimpClient app = new GimpClient();
		String script = "(draw_word_image \""+ aWord +
				"\"  \"Georgia Bold Italic\"  40  \"40\"    '(255 255 255))";

		StringReader r = new StringReader(script);
		app.run(r);
		r.close();
	}
	
	private static String readFile(String path) throws IOException {
		  FileInputStream stream = new FileInputStream(new File(path));
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    return Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		}
	

	public static void main(String[] args) {
		try {
			GimpClient app = new GimpClient();
			String script = "(draw_word_image \""+ "test123" +
					"\"  \"Georgia Bold Italic\"  40  \"40\"    '(255 255 255))";
 
				StringReader r = new StringReader(script);
				app.run(r);
				r.close();
			 
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}