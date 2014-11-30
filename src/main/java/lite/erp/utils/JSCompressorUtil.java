package lite.erp.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import lite.erp.exception.JSCompressorException;

import org.apache.commons.io.FileUtils;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * js文件合并工具类
 * 
 * @author mazhaoyong
 */
public final class JSCompressorUtil {

	private JSCompressorUtil() {

	}

	private static final Logger logger = LoggerFactory
			.getLogger(JSCompressorUtil.class);

	/**
	 * 压缩js文件
	 * 
	 * @param target
	 *            要生成的目标文件
	 * @param files
	 *            要压缩的js文件
	 * @throws IOException
	 */
	public static void compressor(String allfile, String minfile, String[] files)
			throws IOException {
		// 未压缩的文件
		File all = new File(allfile);
		if (!all.exists()) {
			all.createNewFile();
		}
		// 压缩后的文件
		File min = new File(minfile);
		if (!min.exists()) {
			min.createNewFile();
		}
		// 1启动新的线程,生成压缩文件
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				logger.debug("启动了js文件压缩线程,压缩js文件");
				BufferedOutputStream outputStream = null;
				try {
					outputStream = new BufferedOutputStream(
							new FileOutputStream(all));
					for (String file : files) {
						File tempFile = new File(file);
						// 读取文件的中信息
						byte[] bytes = FileUtils.readFileToByteArray(tempFile);
						// 向目标文件中写入数据
						outputStream.write(bytes);
						// 刷新缓冲区
						outputStream.flush();
					}// end of for loop
				} catch (Exception e) {
					logger.error("压缩Js文件出现错误!");
					logger.error(e.getMessage());
					throw new JSCompressorException("压缩js文件出现异常", e);
				} finally {
					if (outputStream != null) {
						try {
							outputStream.close();
						} catch (IOException e) {
							logger.error("关闭outputstream时出现异常!");
							logger.error(e.getMessage());
							throw new JSCompressorException("压缩js文件出现异常", e);
						}
					}
				}// end finally

				// 1.2 使用yui压缩js文件
				try {
					yuicompressor(all,min);
				} catch (Exception e) {
					logger.error("压缩js文件出现异常!");
					logger.error(e.getMessage());
					throw new JSCompressorException("压缩js文件出现异常", e);
				}
			}
		};
		Thread thread = new Thread(runner);
		thread.start();

	}

	public static void yuicompressor(File allfile, File minfile)
			throws EvaluatorException, IOException {
		Reader reader = new InputStreamReader(new FileInputStream(
				allfile), "UTF-8");
		Writer writer = new OutputStreamWriter(new FileOutputStream(
				minfile), "UTF-8");
		JavaScriptCompressor compressor = new JavaScriptCompressor(reader,
				new ErrorReporter() {
					public void warning(String message, String sourceName,
							int line, String lineSource, int lineOffset) {
						if (line < 0) {
							logger.error(message);
						} else {
							logger.error(line + ':' + lineOffset + ':'
									+ message);
						}
					}

					public void error(String message, String sourceName,
							int line, String lineSource, int lineOffset) {
						if (line < 0) {
							logger.error(message);
						} else {
							logger.error(line + ':' + lineOffset + ':'
									+ message);
						}
					}

					public EvaluatorException runtimeError(String message,
							String sourceName, int line, String lineSource,
							int lineOffset) {
						error(message, sourceName, line, lineSource, lineOffset);
						return new EvaluatorException(message);
					}
				});
		compressor.compress(writer, -1, true, false, false, false);
		reader.close();
		writer.close();
	}

}
