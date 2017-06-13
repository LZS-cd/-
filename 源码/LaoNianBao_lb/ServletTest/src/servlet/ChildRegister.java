package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import java.util.*;
import java.text.*;

/**
 * Servlet implementation class ChildRegister
 */
@WebServlet("/ChildRegister")
public class ChildRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChildRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader=request.getReader();
		String requestStr=reader.readLine();
		HashMap<String,String> responseMap=parseToMap(requestStr);
		String account = responseMap.get("account"); // �� request �л�ȡ��Ϊ account �Ĳ�����ֵ  
        String password = responseMap.get("password"); // �� request �л�ȡ��Ϊ password �Ĳ�����ֵ  
        String resCode = "";  
        String resMsg = "";  
        String userId = "";  
        Date date=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	Connection connect = JdbcTest.getConnect(); 
			Statement statement = connect.createStatement();
			 ResultSet result; 
			 String sqlQuery = "select * from " + JdbcTest.TABLE_PASSWORD + " where userAccount='" + account + "'"; 
			 result = statement.executeQuery(sqlQuery); // �Ȳ�ѯͬ�����˺ţ������ֻ��ţ��Ƿ����  
			 if(result.next()){ // �Ѵ���  
	                resCode = "200";  
	                resMsg = "���˺���ע�ᣬ��ʹ�ô��˺�ֱ�ӵ�¼��ʹ�������˺�ע��";  
	                userId = "";  
			 }else{
				 String sqlInsert="insert into "+JdbcTest.TABLE_PASSWORD+"(userAccount,userPassword,registerTime,type)"
						 +" values('"+account+"','"+password+"','"+simpleDateFormat.format(date)+"','child')";
				 int row1 = statement.executeUpdate(sqlInsert); // �����ʺ�����  
				 if(row1 == 1){  
					 resCode = "100";  
                     resMsg = "ע��ɹ�"; 
				 }else{
					 resCode = "201";  
	                 resMsg = "�û���Ϣ��������";  
	                 userId = "";  
				 }
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��  
       
        response.setContentType("text/html;charset=utf-8"); // ������Ӧ���ĵı����ʽ  
        PrintWriter pw = response.getWriter(); // ��ȡ response �������  
        pw.print(resCode); // ͨ���������ҵ���߼��Ľ�����  
        pw.flush();  
	}
	private HashMap<String,String> parseToMap(String str)
	{
		HashMap<String,String> result=new HashMap();
		String[] parm1=str.split("&");
		String[] parm2;
		for(String a:parm1)
		{
			 parm2=a.split("=");
			result.put(parm2[0], parm2[1]);
		}
		return result;
	}

}
