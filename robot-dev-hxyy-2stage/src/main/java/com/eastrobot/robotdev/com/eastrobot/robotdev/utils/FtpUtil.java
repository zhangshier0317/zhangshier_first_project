//package com.eastrobot.robotdev.com.eastrobot.robotdev.utils;
//
//import java.io.BufferedWriter;
//import java.io.DataInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.StringTokenizer;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//import sun.net.TelnetInputStream;
//import sun.net.TelnetOutputStream;
//import sun.net.ftp.FtpClient;
//
///**
// *
// * @dec :   ftp上传，下载
// * @Date: 2020/3/6
// * @Auther: pengbo.zhao
// * @version: 1.0
// */
//public class FtpUtil {
//
//	private static Logger log = LoggerFactory.getLogger(FtpUtil.class);
//	private String ip = "";
//	private String username = "";
//	private String password = "";
//	private int port = 21;
//	private String path = "/";
//	FtpClient ftpClient = null;
//	OutputStream os = null;
//	FileInputStream is = null;
//
//	public FtpUtil(String serverIP, String username, String password) {
//		this.ip = serverIP;
//		this.username = username;
//		this.password = password;
//	}
//
//	public FtpUtil(String serverIP, int port, String username, String password) {
//		this.ip = serverIP;
//		this.username = username;
//		this.password = password;
//		this.port = port;
//	}
//
//	public boolean FtpStr(String str, String path) {
//		try {
//			ftpClient.sendServer("quote PASV");
//			os = ftpClient.put(path);
//			os.write(str.getBytes("GBK"));
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * 连接ftp服务器
//	 *
//	 * @throws IOException
//	 */
//	public boolean connectServer() {
//		ftpClient = new FtpClient();
//		try {
//			if (this.port != -1) {
//				ftpClient.openServer(this.ip, this.port);
//			} else {
//				ftpClient.openServer(this.ip);
//			}
//			ftpClient.login(this.username, this.password);
//			if (this.path.length() != 0) {
//				ftpClient.cd(this.path);// path是ftp服务下主目录的子目录
//			}
//			ftpClient.binary();// 用2进制上传、下载
//			return true;
//		} catch (IOException e) {
//			log.debug("FtpUtil->connectServer: "+e);
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * 断开与ftp服务器连接
//	 *
//	 * @throws IOException
//	 */
//	public boolean closeServer() {
//		try {
//			if (is != null) {
//				is.close();
//			}
//			if (os != null) {
//				os.close();
//			}
//			if (ftpClient != null) {
//				ftpClient.closeServer();
//			}
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * 检查文件夹在当前目录下是否存在
//	 *
//	 * @param dir
//	 * @return
//	 */
//	private boolean isDirExist(String dir) {
//		String pwd = "";
//		try {
//			pwd = ftpClient.pwd();
//			ftpClient.cd(dir);
//			ftpClient.cd(pwd);
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 在当前目录下创建文件夹
//	 *
//	 * @param dir
//	 * @return
//	 * @throws Exception
//	 */
//	private boolean createDir(String dir) {
//		try {
//			ftpClient.ascii();
//			StringTokenizer s = new StringTokenizer(dir, "/"); // sign
//			s.countTokens();
//			String pathName = ftpClient.pwd();
//			while (s.hasMoreElements()) {
//				pathName = pathName + "/" + (String) s.nextElement();
//				try {
//					ftpClient.sendServer("MKD " + pathName + "\r\n");
//				} catch (Exception e) {
//					e = null;
//					return false;
//				}
//				ftpClient.readServerResponse();
//			}
//			ftpClient.binary();
//			return true;
//		} catch (IOException e1) {
//			e1.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * ftp上传 如果服务器段已存在名为filename的文件夹，该文件夹中与要上传的文件夹中同名的文件将被替换
//	 *
//	 * @param filename
//	 *            要上传的文件（或文件夹）名
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean upload(String filename) {
//		String newname = "";
//		if (filename.indexOf("/") > -1) {
//			newname = filename.substring(filename.lastIndexOf("/") + 1);
//		} else {
//			newname = filename;
//		}
//		return upload(filename, newname);
//	}
//
//	private String getUploadDir(String newname) {
//		String uploadDir = "";
//		if (newname.indexOf("/") > -1) {
//			uploadDir = newname.substring(0, newname.lastIndexOf("/") + 1);
//		}
//		return uploadDir;
//	}
//
//	/**
//	 * ftp上传 如果服务器段已存在名为newName的文件夹，该文件夹中与要上传的文件夹中同名的文件将被替换
//	 *
//	 * @param fileName
//	 *            要上传的文件（或文件夹）名
//	 * @param newName
//	 *            服务器段要生成的文件（或文件夹）名
//	 * @return
//	 */
//	public boolean upload(String fileName, String newName) {
//		try {
//			String savefilename = new String(fileName.getBytes("GBK"), "GBK");
//			File file_in = new File(savefilename);// 打开本地待长传的文件
//			if (!file_in.exists()) {
//				throw new Exception("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
//			}
//			if (file_in.isDirectory()) {
//				upload(file_in.getPath(), newName, ftpClient.pwd());
//			} else {
//				String uploadDir = getUploadDir(newName);
//				if (!isDirExist(uploadDir)) {
//					createDir(uploadDir);
//				}
//				ftpClient.cd(uploadDir);
//				uploadFile(file_in.getPath(), newName);
//			}
//			if (is != null) {
//				is.close();
//			}
//			if (os != null) {
//				os.close();
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.println("Exception e in Ftp upload(): " + e.toString());
//			return false;
//		} finally {
//			try {
//				if (is != null) {
//					is.close();
//				}
//				if (os != null) {
//					os.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 真正用于上传的方法
//	 *
//	 * @param fileName
//	 * @param newName
//	 * @param path
//	 * @throws Exception
//	 */
//	private void upload(String fileName, String newName, String path)
//			throws Exception {
//		String savefilename = new String(fileName.getBytes("ISO-8859-1"),
//				"UTF-8");
//		File file_in = new File(savefilename);// 打开本地待长传的文件
//		if (!file_in.exists()) {
//			throw new Exception("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
//		}
//		if (file_in.isDirectory()) {
//			if (!isDirExist(newName)) {
//				createDir(newName);
//			}
//			ftpClient.cd(newName);
//			File sourceFile[] = file_in.listFiles();
//			for (int i = 0; i < sourceFile.length; i++) {
//				if (!sourceFile[i].exists()) {
//					continue;
//				}
//				if (sourceFile[i].isDirectory()) {
//					this.upload(sourceFile[i].getPath(), sourceFile[i]
//							.getName(), path + "/" + newName);
//				} else {
//					this.uploadFile(sourceFile[i].getPath(), sourceFile[i]
//							.getName());
//				}
//			}
//		} else {
//			uploadFile(file_in.getPath(), newName);
//		}
//		ftpClient.cd(path);
//	}
//
//	/**
//	 * upload 上传文件
//	 *
//	 * @param filename
//	 *            要上传的文件名
//	 * @param newname
//	 *            上传后的新文件名
//	 * @return -1 文件不存在 >=0 成功上传，返回文件的大小
//	 * @throws Exception
//	 */
//	public long uploadFile(String filename, String newname) throws Exception {
//		long result = 0;
//		TelnetOutputStream os = null;
//		FileInputStream is = null;
//		try {
//			File file_in = new File(filename);
//			if (!file_in.exists())
//				return -1;
//			ftpClient.sendServer("quote PASV");
//			os = ftpClient.put(newname);
//			result = file_in.length();
//			is = new FileInputStream(file_in);
//			byte[] bytes = new byte[1024];
//			int c;
//			while ((c = is.read(bytes)) != -1) {
//				os.write(bytes, 0, c);
//			}
//		} finally {
//			if (is != null) {
//				is.close();
//			}
//			if (os != null) {
//				os.close();
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 从ftp下载文件到本地
//	 *
//	 * @param filename
//	 *            服务器上的文件名
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public TelnetInputStream downloadFile(String filename/*
//															 * , String
//															 * newfilename
//															 */) {
//		// long
//		long result = 0;
//		TelnetInputStream is = null;
//		FileOutputStream os = null;
//		try {
//			is = ftpClient.get(filename);
//			// java.io.File outfile = new java.io.File(newfilename);
//			// os = new FileOutputStream(outfile);
//			// byte[] bytes = new byte[1024];
//			// int c;
//			// while ((c = is.read(bytes)) != -1) {
//			// os.write(bytes, 0, c);
//			// result = result + c;
//			// }
//		} catch (Exception e) {
//		} finally {
//			// try {
//			// if (is != null) {
//			// is.close();
//			// }
//			// if (os != null) {
//			// os.close();
//			// }
//			// } catch (IOException e) {
//			// e.printStackTrace();
//			// }
//		}
//		// return result;
//		return is;
//	}
//
//	/**
//	 * 取得相对于当前连接目录的某个目录下所有文件列表
//	 *
//	 * @param path
//	 * @return
//	 */
//	public List getFileList(String path) {
//		List list = new ArrayList();
//		DataInputStream dis;
//		try {
//			dis = new DataInputStream(ftpClient.nameList(this.path + path));
//			String filename = "";
//			while ((filename = dis.readLine()) != null) {
//				list.add(filename);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//
//	public static String contentToTxt(String filePath, String content) {
//		// CCIS_V0010_YYYYMMDDHHMMSS.bin
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String specifiedDay = sdf.format(date);
//		try {
//			File f = new File(filePath + "\\CCIS_V0010_" + specifiedDay
//					+ ".bin");
//			f.deleteOnExit();
//			f.createNewFile();// 不存在则创建
//			BufferedWriter output = new BufferedWriter(new FileWriter(f));
//			output.write(content);
//			output.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return filePath + "\\CCIS_V0010_" + specifiedDay + ".bin";
//	}
//
//	public static void main(String[] args) {
//		FtpUtil ftp = new FtpUtil("122.19.157.62", "administrator",
//				"Passw0rd123!");
//		ftp.connectServer();
//		boolean result = ftp.upload("D:/a.txt", "/abcd/xiaoi.txt");
//		System.out.println(result ? "上传成功！" : "上传失败！");
//		ftp.closeServer();
//		/**
//		 * FTP远程命令列表 USER PORT RETR ALLO DELE SITE XMKD CDUP FEAT PASS PASV STOR
//		 * REST CWD STAT RMD XCUP OPTS ACCT TYPE APPE RNFR XCWD HELP XRMD STOU
//		 * AUTH REIN STRU SMNT RNTO LIST NOOP PWD SIZE PBSZ QUIT MODE SYST ABOR
//		 * NLST MKD XPWD MDTM PROT
//		 * 在服务器上执行命令,如果用sendServer来执行远程命令(不能执行本地FTP命令)的话，所有FTP命令都要加上\r\n
//		 * ftpclient.sendServer("XMKD /test/bb\r\n"); //执行服务器上的FTP命令
//		 * ftpclient.readServerResponse一定要在sendServer后调用
//		 * nameList("/test")获取指目录下的文件列表 XMKD建立目录，当目录存在的情况下再次创建目录时报错 XRMD删除目录
//		 * DELE删除文件
//		 */
//	}
//}
