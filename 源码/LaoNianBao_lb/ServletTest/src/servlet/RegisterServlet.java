package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import com.mysql.jdbc.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
        String name=responseMap.get("name");
        String age=responseMap.get("age");
        String tel_number=responseMap.get("telephone");
        String resCode = "";  
        String resMsg = "";  
        String userId = "";  
        Date date=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {  
            Connection connect = JdbcTest.getConnect();  
            Statement statement = connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��  
            ResultSet result;  
              
            String sqlQuery = "select * from " + JdbcTest.TABLE_PASSWORD + " where userAccount='" + account + "'";  
              
            // ��ѯ���������һ��ResultSet���ϣ�û�в鵽���ʱResultSet�ĳ���Ϊ0  
            result = statement.executeQuery(sqlQuery); // �Ȳ�ѯͬ�����˺ţ������ֻ��ţ��Ƿ����  
            if(result.next()){ // �Ѵ���  
                resCode = "200";  
                resMsg = "���˺���ע�ᣬ��ʹ�ô��˺�ֱ�ӵ�¼��ʹ�������˺�ע��";  
                userId = "";  
            } else { // ������  
                String sqlInsertPass = "insert into " + JdbcTest.TABLE_PASSWORD + "(userAccount,userPassword,registerTime,type) "
                		+ "values('"+account+"','"+password+"','"+simpleDateFormat.format(date)+"','old')";  
                // �������������һ��int���͵�ֵ������ò���Ӱ�쵽������  
                int row1 = statement.executeUpdate(sqlInsertPass); // �����ʺ�����  
                if(row1 == 1){  
                    String sqlQueryId = "select userId from " + JdbcTest.TABLE_PASSWORD + " where userAccount='" + account + "'";  
                    ResultSet result2 = statement.executeQuery(sqlQueryId); // ��ѯ������¼��userId  
                    if(result2.next()){  
                        userId = result2.getInt("userId") + "";  
                    }  
                   String sqlInsertInfo = "insert into " + JdbcTest.TABLE_USERINFO + "(userId,userName,userAge,telephone) "
                   		+ "values("+Integer.parseInt(userId)+",'"+name+"',"+Integer.parseInt(age)+",'"+tel_number+"'"+")";  
                   int row2 = statement.executeUpdate(sqlInsertInfo); // ���û���Ϣ���в����ע���Id  
                    if(row2 == 1){ // �������ж�����ɹ�����ҵ��Ƕ��϶�Ϊע��ɹ�  
                        resCode = "100";  
                        resMsg = "ע��ɹ�";  
                    }  
                } else {  
                    resCode = "201";  
                    resMsg = "�û���Ϣ��������";  
                    userId = "";  
                }  
            }  
              
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
          
        
          
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
