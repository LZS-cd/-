package servlet;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import spider.*;
/**
 * Servlet implementation class HealthyTips
 */
@WebServlet("/HealthyTips")
public class HealthyTips extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HealthyTips() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 Rule rule = new Rule("http://health.china.com/",  
		            new String[] { "name" }, new String[] { "" },  
		            null, -1, Rule.GET);  
		    List<LinkTypeData> extracts = ExtractService.extract(rule);  
		
		    JSONObject jsonObject=new JSONObject();
		    int length=1;
		    for (LinkTypeData data : extracts)
			{
				if((data.getLinkText().indexOf("����")!=-1||data.getLinkText().indexOf("����")!=-1||data.getLinkText().indexOf("��θ")!=-1
					||data.getLinkText().indexOf("ʳ��")!=-1||data.getLinkText().indexOf("����")!=-1||data.getLinkText().indexOf("˯ǰ")!=-1
					||data.getLinkText().indexOf("ô")!=-1||data.getLinkText().indexOf("��")!=-1||data.getLinkText().indexOf("����")!=-1
					||data.getLinkText().indexOf("ҩ")!=-1||data.getLinkText().indexOf("ҽ��")!=-1
					)&&data.getLinkText().indexOf("��")==-1&&data.getLinkText().indexOf("��")==-1&&data.getLinkHref().indexOf(".html")!=-1)
				{
			//	System.out.println(data.getLinkText());
			//	System.out.println("http://health.china.com" + data.getLinkHref());
			//	System.out.println("-----------------------------");
					try {
						JSONObject jsonObject1=new JSONObject();
						jsonObject1.put("����", data.getLinkText());
						jsonObject1.put("Href", "http://health.china.com"+data.getLinkHref());
						 JSONArray jsonArr=new JSONArray();
						 jsonArr.put(jsonObject1);
						 jsonObject.put(length+"",jsonArr);
						 length++;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		    response.setContentType("text/html;charset=utf-8");
		    PrintWriter print=response.getWriter();
		    print.print(jsonObject.toString());
		    print.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
