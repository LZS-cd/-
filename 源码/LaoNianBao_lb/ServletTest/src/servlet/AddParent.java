package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;

import xg.*;

import org.json.JSONObject;
import org.json.JSONTokener;
/**
 * Servlet implementation class AddParent
 */
@WebServlet("/AddParent")
public class AddParent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddParent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type=request.getParameter("type");//1. ��Ů��ĸ�� 2.��ĸ����Ů��
		String child_account=request.getParameter("child");
		String parent_account=request.getParameter("parent");
		String resCode="";
		String resMsg="";
		if(type.equals("1")){
		try {
			Connection connect = JdbcTest.getConnect();
			Statement statement = connect.createStatement();
			String sql="select * from "+JdbcTest.TABLE_PASSWORD+" where userAccount = "+
					"'"+parent_account+"'";
			ResultSet result=statement.executeQuery(sql);
			if(result.next())
			{
				Message message=new Message();
				message.setTitle("add");
		        message.setContent(child_account);
		        message.setType(Message.TYPE_MESSAGE);
		        JSONObject Lnb_result=LaoNianBaoPush.pushMessage(parent_account, message);
		        int c=Lnb_result.getInt("ret_code");
		        if(c == 0){
		        	resCode="100";
		        	resMsg="�ɹ�";
		        }else{
		        	resCode=c+"";
		        	resMsg=Lnb_result.getString("err_msg");
		        }
			}
			else{
				resCode="200";
				resMsg="�˺Ų�����";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			String parent_res=request.getParameter("res");
			Message message=new Message();
			message.setTitle(parent_account);
	        message.setContent(parent_res);
	        message.setType(Message.TYPE_MESSAGE);
	        JSONObject xg_result=XgPush.pushMessage(child_account, message);
	        int c=xg_result.getInt("ret_code");
	        if(c==0){
	        	resCode="100";
	        	resMsg="�ɹ�";
	        }else{
	        	resCode=c+"";
	        	resMsg=xg_result.getString("err_msg");
	        }
		}
		response.setContentType("text/html;charset=utf-8"); // ������Ӧ���ĵı����ʽ  
        PrintWriter pw = response.getWriter(); // ��ȡ response �������  
        pw.print(resCode); // ͨ���������ҵ���߼��Ľ�����  
        pw.flush();  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
