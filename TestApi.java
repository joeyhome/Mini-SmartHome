import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat; 
import java.lang.reflect.Array;

import org.apache.http.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;
import org.apache.http.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//需要两个第三方库:
//Apache httpclient
//Google gson
//请自行引入

public class LinpcloudApi{
	private String protocol = "http://";
	
	//本地服务器
	private String host = "192.168.207.1:80";

	//远程服务器
	//private String host = "218.244.138.146:8000";

	//远程服务器2
	//private String host = "";
	
	//通过API的login方法登录后，自动设置APIKEY
	private String apikey = null;

	public static void main(String[] args) throws IOException {
		//建议单独对某个部分的功能进行测试，注释掉其他的测试部分

		//创建对象
		LinpcloudApi api = new LinpcloudApi();

		//基本的HTTP请求测试
		System.out.println("<--------HTTP请求测试-------->");
		List <BasicNameValuePair> params = new ArrayList <BasicNameValuePair> ();
		params.add(new BasicNameValuePair("param1", "value for param1"));
		params.add(new BasicNameValuePair("param2", "value for param2"));

		ResponseInfo result1 = api.http_put("/helper/put", params);
		result1.show();

		ResponseInfo result2 = api.http_post("/helper/post", params);
		result2.show();

		ResponseInfo result3 = api.http_get("/helper/get" + "?info=test" + "&method=get");
		result3.show();
		
		ResponseInfo result4 = api.http_delete("/helper/delete" + "?info=test" + "&method=delete");
		result4.show();
		

		//登录测试
		System.out.println("<--------登录（login）方法测试-------->");
		User user = api.login_post("linpcloud", "1234");//登录，用户名和密码
		System.out.println(user.toString());
		user = api.user_get(user.id);//通过ID获取完整用户信息
		System.out.println(user.toString());

/*
		//设备接口测试
		System.out.println("<--------设备（device）接口测试-------->");
		Device [] devices = api.devices_get();//获取设备列表，该用户下的所有设备，存于数组中
		System.out.println(Arrays.toString(devices));//打印设备信息

		//获取某个设备的详细信息
		Device device = api.device_get(10010);
		System.out.println(device.toString());

		//添加一个新设备，需要指定设备名称name
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formater.format(new   java.util.Date());
		api.devices_post("java-device@" + date, "tags", "device created by java api, at " + date, "locate");

		//删除一个设备
		//api.device_delete(10010);
*/

/*
		//传感器接口测试
		System.out.println("<--------传感器（sensor）接口测试-------->");
		Sensor[] sensors = api.sensors_get(10012);//通过设备ID获取某个具体设备下所有的传感器
		System.out.println(Arrays.toString(sensors));//打印传感器信息

		//获取某个传感器的详细信息
		Sensor sensor = api.sensor_get(100009);
		System.out.println(sensor.toString());

		//添加一个新的传感器，需要指定所属设备id，传感器的名称name，传感器类型type
		api.sensors_post(10012, "java-sensor@" + date, 1, "tags", "sensor created by java api, at " + date);

		//通过ID删除指定的传感器
		api.sensor_delete(100009);

		//更新某个传感器的信息
		api.sensor_put(100009, "zhangxing", 1, "test", "test");
*/
/*
		//数据点接口测试
		System.out.println("<--------数据点（datapoint）接口测试-------->");

		//向某个传感器添加一个数据点
		api.datapoint_post(100009, "hello world");

		//获取某个具体传感器的最新的数据
		Datapoint dp = api.datapoint_get(100009);
		System.out.println(dp.toString());//输出数据点的详细信息

		//获取某个数据点的历史数据
		Datapoint[] dps = api.datapoints_get(100007, 1000, 0, 0);
		System.out.println(Arrays.toString(dps));//输出数据点信息
*/
	}

	//HTTP POST请求
	//其它HTTP请求的注释略，参考POST即可
	private ResponseInfo http_post(String uri, List <BasicNameValuePair> params)
	{
		StringBuilder content = new StringBuilder();//用于保存响应正文
		StringBuilder header = new StringBuilder();//用于保存相应头
		String statusLine = "";//用于相应头的第一行，或称状态行
		int statusCode = 404;//响应状态码

		//创建HTTP CLIENT对象
		CloseableHttpClient client = HttpClients.createDefault();

		try {
			String url = protocol + host + uri;//组织URL
			HttpPost request = new HttpPost(url);//创建HTTP请求对象：POST
			request.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));//添加表单数据
			request.addHeader("Apikey", apikey);//添加请求头:apikey
			request.addHeader("Accept", "applicaton/json");//添加请求头:设置接受数据类型为JSON

			CloseableHttpResponse response = client.execute(request);//发送HTTP请求

			HttpEntity entity = response.getEntity();//获取响应实体
			statusLine = response.getStatusLine().toString();//保存响应的状态行
			statusCode = response.getStatusLine().getStatusCode();//保存响应的状态码

			//组织响应报头
			HeaderIterator it = response.headerIterator();
			while (it.hasNext()) {
				header.append( it.nextHeader().toString() + "\n");
			}

			//组织响应正文
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String line;
			while ((line = reader.readLine()) != null)
			{
				content.append(line+"\n");
			}

			//用于测试的输出语句，用于输出HTTP响应报文，可注释掉
			//可以使用ResponseInfo#toString()来输出
			/*System.out.println("--------HTTP响应报文--------");
			System.out.println(statusLine);
			System.out.println(header.toString());
			System.out.println(content.toString());*/

			reader.close();
			response.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回对象ResponseInfo，该对象用于存储响应报文的主要信息
		return new ResponseInfo(statusCode, statusLine, header.toString(), content.toString());
	}

	private ResponseInfo http_put(String uri, List <BasicNameValuePair> params)
	{
		StringBuilder content = new StringBuilder();
		StringBuilder header = new StringBuilder();
		String statusLine = "";
		int statusCode = 404;
		CloseableHttpClient client = HttpClients.createDefault();

		try {
			String url = protocol + host + uri;
			HttpPut request = new HttpPut(url);
			request.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
			request.addHeader("Apikey", apikey);
			request.addHeader("Accept", "applicaton/json");

			CloseableHttpResponse response = client.execute(request);

			HttpEntity entity = response.getEntity();
			statusLine = response.getStatusLine().toString();
			statusCode = response.getStatusLine().getStatusCode();

			HeaderIterator it = response.headerIterator();
			while (it.hasNext()) {
				header.append( it.nextHeader().toString() + "\n");
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String line;
			while ((line = reader.readLine()) != null)
			{
				content.append(line+"\n");
			}

			reader.close();
			response.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseInfo(statusCode, statusLine, header.toString(), content.toString());
	}

	private ResponseInfo http_get(String uri)
	{
		StringBuilder content = new StringBuilder();
		StringBuilder header = new StringBuilder();
		String statusLine = "";
		int statusCode = 404;
		CloseableHttpClient client = HttpClients.createDefault();

		try {
			String url = protocol + host + uri;
			HttpGet request = new HttpGet(url);
			request.addHeader("Apikey", apikey);
			request.addHeader("Accept", "applicaton/json");

			CloseableHttpResponse response = client.execute(request);

			HttpEntity entity = response.getEntity();
			statusLine = response.getStatusLine().toString();
			statusCode = response.getStatusLine().getStatusCode();

			HeaderIterator it = response.headerIterator();
			while (it.hasNext()) {
				header.append( it.nextHeader().toString() + "\n");
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String line;
			while ((line = reader.readLine()) != null)
			{
				content.append(line+"\n");
			}

			reader.close();
			response.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseInfo(statusCode, statusLine, header.toString(), content.toString());
	}

	private ResponseInfo http_delete(String uri)
	{
		StringBuilder content = new StringBuilder();
		StringBuilder header = new StringBuilder();
		String statusLine = "";
		int statusCode = 404;
		CloseableHttpClient client = HttpClients.createDefault();

		try {
			String url = protocol + host + uri;
			HttpDelete request = new HttpDelete(url);
			request.addHeader("Apikey", apikey);
			request.addHeader("Accept", "applicaton/json");

			CloseableHttpResponse response = client.execute(request);

			HttpEntity entity = response.getEntity();
			statusLine = response.getStatusLine().toString();
			statusCode = response.getStatusLine().getStatusCode();

			HeaderIterator it = response.headerIterator();
			while (it.hasNext()) {
				header.append( it.nextHeader().toString() + "\n");
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String line;
			while ((line = reader.readLine()) != null)
			{
				content.append(line+"\n");
			}

			reader.close();
			response.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseInfo(statusCode, statusLine, header.toString(), content.toString());
	}

	//通过用户名和密码进行登录
	//验证成功将设置Apikey的值
	//返回保存有信息的USER对象
	private User login_post(String username, String password)
	{
		List <BasicNameValuePair> params = new ArrayList <BasicNameValuePair> ();
		params.add(new BasicNameValuePair("username", username));//required
		params.add(new BasicNameValuePair("password", password));//required

		
		ResponseInfo result = this.http_post("/linpcloud/login", params);
		//System.out.println(result.content);//需要显示HTTP报文则取消改注释

		Gson gson = new Gson();
		if(result.code == 200)//请求成功
		{
			//@google gson
			User user = gson.fromJson(result.content, User.class);
			user.code = 200;
			apikey = user.apikey;//设置APIKEY
			return user;
		}
		else if(result.code == 400)//设计内错误
		{
			//@google gson
			User user = gson.fromJson(result.content, User.class);
			user.code = 400;
			//do something to tell me what is wrong
			return user;
		}
		else//预料外错误
		{
			//do something to tell me what is wrong
			User user = new User();
			user.code = result.code;
			return user;

			//or return this
			//return null;
		}
	}

	//通过用户ID获取完整的用户信息
	//返回USER对象
	private User user_get(int user_id)
	{
		ResponseInfo result = this.http_get("/linpcloud/user/" + user_id);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			//@google gson
			User user = gson.fromJson(result.content, User.class);
			user.code = 200;
			return user;
		}
		else if(result.code == 400)
		{
			//@google gson
			User user = gson.fromJson(result.content, User.class);
			user.code = 400;
			//do something to tell me what is wrong
			return user;
		}
		else
		{
			//do something to tell me what is wrong
			User user = new User();
			user.code = result.code;
			return user;

			//or return this
			//return null;
		}
	}

	//获取该用户所有的设备
	//返回对象数组
	private Device[] devices_get()
	{
		ResponseInfo result = this.http_get("/linpcloud/devices");
		System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			//@google gson
			Device[] devices = gson.fromJson(result.content, new TypeToken<Device[]>(){}.getType());
			return devices;
		}
		else if(result.code == 400)
		{
			//@google gson
			//Device device = gson.fromJson(result.content, Device.class);
			//do something to tell me what is wrong
			//return device;

			return null;
		}
		else
		{
			//do something to tell me what is wrong
			//Device device = new Device();
			//device.code = result.code;
			//return device;

			//or return this
			return null;
		}
	}

	//通过表单创建一个新设备
	//云平台有信息返回，但现在没有进行处理
	private boolean devices_post(String name, String tags, String about, String locate)
	{
		List <BasicNameValuePair> params = new ArrayList <BasicNameValuePair> ();
		params.add(new BasicNameValuePair("name", name));//required
		params.add(new BasicNameValuePair("tags", tags));//optional
		params.add(new BasicNameValuePair("about", about));//optional
		params.add(new BasicNameValuePair("locate", locate));//optional

		ResponseInfo result = this.http_post("/linpcloud/devices/", params);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			return true;
		}
		else if(result.code == 400)
		{
			return false;
		}
		else
		{
			return false;
		}
	}

	//通过ID获取某个设备的详细信息
	//返回DEVICE对象
	private Device device_get(int device_id)
	{
		ResponseInfo result = this.http_get("/linpcloud/device/" + device_id);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			//@google gson
			Device device = gson.fromJson(result.content, Device.class);
			device.code = 200;
			return device;
		}
		else if(result.code == 400)
		{
			//@google gson
			Device device = gson.fromJson(result.content, Device.class);
			device.code = 400;
			//do something to tell me what is wrong
			return device;
		}
		else
		{
			//do something to tell me what is wrong
			Device device = new Device();
			device.code = result.code;
			return device;

			//or return this
			//return null;
		}
	}

	//通过ID删除某个设备
	//有数据返回，但暂时没有处理
	private boolean device_delete(int device_id)
	{
		ResponseInfo result = this.http_delete("/linpcloud/device/" + device_id);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			return true;
		}
		else if(result.code == 400)
		{
			return false;
		}
		else
		{
			return false;
		}
	}

	//更新某个设备信息
	private boolean device_put(int device_id, String name, String tags, String about, String locate)
	{
		List <BasicNameValuePair> params = new ArrayList <BasicNameValuePair> ();
		params.add(new BasicNameValuePair("name", name));//required
		params.add(new BasicNameValuePair("tags", tags));//optional
		params.add(new BasicNameValuePair("about", about));//optional
		params.add(new BasicNameValuePair("locate", locate));//optional

		ResponseInfo result = this.http_put("/linpcloud/device/" + device_id, params);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			return true;
		}
		else if(result.code == 400)
		{
			return false;
		}
		else
		{
			return false;
		}
	}

	//通过设备ID获取该设备下所有的传感器
	//返回对象数组
	private Sensor[] sensors_get(int device_id)
	{
		ResponseInfo result = this.http_get("/linpcloud/sensors/" + device_id);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			//@google gson
			Sensor[] sensors = gson.fromJson(result.content, new TypeToken<Sensor[]>(){}.getType());
			return sensors;
		}
		else if(result.code == 400)
		{
			//@google gson
			//Device device = gson.fromJson(result.content, Device.class);
			//do something to tell me what is wrong
			//return device;

			return null;
		}
		else
		{
			//do something to tell me what is wrong
			//Device device = new Device();
			//device.code = result.code;
			//return device;

			//or return this
			return null;
		}
	}

	//在具体某个设备下创建一个新的传感器
	private boolean sensors_post(int device_id, String name, int type, String tags, String about)
	{
		List <BasicNameValuePair> params = new ArrayList <BasicNameValuePair> ();
		params.add(new BasicNameValuePair("device_id", Integer.toString(device_id)));//required
		params.add(new BasicNameValuePair("type", Integer.toString(type)));//required
		params.add(new BasicNameValuePair("name", name));//required

		params.add(new BasicNameValuePair("tags", tags));//optional
		params.add(new BasicNameValuePair("about", about));//optional

		ResponseInfo result = this.http_post("/linpcloud/sensors/", params);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			return true;
		}
		else if(result.code == 400)
		{
			return false;
		}
		else
		{
			return false;
		}
	}

	//通过ID获取某个传感器的详细信息
	private Sensor sensor_get(int sensor_id)
	{
		ResponseInfo result = this.http_get("/linpcloud/sensor/" + sensor_id);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			//@google gson
			Sensor sensor = gson.fromJson(result.content, Sensor.class);
			sensor.code = 200;
			return sensor;
		}
		else if(result.code == 400)
		{
			//@google gson
			Sensor sensor = gson.fromJson(result.content, Sensor.class);
			sensor.code = 400;
			//do something to tell me what is wrong
			return sensor;
		}
		else
		{
			//do something to tell me what is wrong
			Sensor sensor = new Sensor();
			sensor.code = result.code;
			return sensor;

			//or return this
			//return null;
		}
	}

	//通过ID删除某个具体传感器
	private boolean sensor_delete(int sensor_id)
	{
		ResponseInfo result = this.http_delete("/linpcloud/sensor/" + sensor_id);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			return true;
		}
		else if(result.code == 400)
		{
			return false;
		}
		else
		{
			return false;
		}
	}

	//更新某个设备的信息
	private boolean sensor_put(int sensor_id, String name, int type, String tags, String about)
	{
		List <BasicNameValuePair> params = new ArrayList <BasicNameValuePair> ();
		params.add(new BasicNameValuePair("name", name));//required
		params.add(new BasicNameValuePair("type", Integer.toString(type)));//required

		params.add(new BasicNameValuePair("tags", tags));//optional
		params.add(new BasicNameValuePair("about", about));//optional

		ResponseInfo result = this.http_put("/linpcloud/sensor/" + sensor_id, params);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			return true;
		}
		else if(result.code == 400)
		{
			return false;
		}
		else
		{
			return false;
		}
	}

	//通过ID给传感器创建一个数据点
	//也可以视为发出控制命令
	private boolean datapoint_post(int sensor_id, String value)
	{
		List <BasicNameValuePair> params = new ArrayList <BasicNameValuePair> ();
		params.add(new BasicNameValuePair("sensor_id", Integer.toString(sensor_id)));//required
		params.add(new BasicNameValuePair("value", value));//required

		ResponseInfo result = this.http_post("/linpcloud/datapoint", params);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			return true;
		}
		else if(result.code == 400)
		{
			return false;
		}
		else
		{
			return false;
		}
	}

	//获取某个传感器的最新数据点
	private Datapoint datapoint_get(int sensor_id)
	{
		ResponseInfo result = this.http_get("/linpcloud/datapoint/" + sensor_id);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			//@google gson
			Datapoint datapoint = gson.fromJson(result.content, Datapoint.class);
			datapoint.code = 200;
			return datapoint;
		}
		else if(result.code == 400)
		{
			//@google gson
			Datapoint datapoint = gson.fromJson(result.content, Datapoint.class);
			datapoint.code = 400;
			//do something to tell me what is wrong
			return datapoint;
		}
		else
		{
			//do something to tell me what is wrong
			Datapoint datapoint = new Datapoint();
			datapoint.code = result.code;
			return datapoint;

			//or return this
			//return null;
		}
	}

	//获取某个传感器的历史数据
	//start-开始的时间戳，设置为0即默认设为一小时前
	//end-结束的时间戳，设置为0即默认是现在
	//interval-时间间隔，设置为0即默认值60秒
	//三个值均为非负整数
	//前两个为UNIX时间戳，即XX年XX月XX日至今的秒数，而非毫秒
	//返回值为对象数组
	private Datapoint[] datapoints_get(int sensor_id, int start, int end, int interval)
	{
		String url = "/linpcloud/datapoints/sensor/" + sensor_id;
		if(start != 0)
		{
			url += "?start=" + start;

			if(end != 0)
			{
				url += "&end=" + end;

				if(interval != 0)
				{
					url += "&interval=" + interval;
				}
			}
		}
		System.out.println(url);

		ResponseInfo result = this.http_get(url);
		//System.out.println(result.content);

		Gson gson = new Gson();
		if(result.code == 200)
		{
			//parse the json string to array
			Datapoint[] datapoints = gson.fromJson(result.content, new TypeToken<Datapoint[]>(){}.getType());
			return datapoints;
		}
		else if(result.code == 400)
		{
			//@google gson
			//Device device = gson.fromJson(result.content, Device.class);
			//do something to tell me what is wrong
			//return device;

			return null;
		}
		else
		{
			//do something to tell me what is wrong
			//Device device = new Device();
			//device.code = result.code;
			//return device;

			//or return this
			return null;
		}
	}

}

//用于存储响应报文信息
class ResponseInfo
{
	//http response status code
	//for 200, request is success and platform work properly
	//for 400, request is success but platform encounter an error and return error msg
	//for other code, request is treated to be fail
	protected int code;

	protected String statusLine;
	protected String header;

	//response body, or response content
	//for 200 and 400, this should be a json string contained infomation
	//for any other, this is treated as invalid
	protected String content;

	ResponseInfo(int statusCode, String statusLine, String header, String content)
	{
		this.code = statusCode;
		this.statusLine = statusLine;
		this.header = header;
		this.content = content;
	}

	public void show()
	{
		System.out.println("--------HTTP响应报文--------");
		System.out.println(statusLine);
		System.out.println(header);
		System.out.println(content);
	}
}

//用于保存用户信息
class User
{
	//these tow params you may not need,
	//but if you want to deal with response code after json is parsing,
	//it could be helpful.
	//used to store http response code, set it when deal with http response
	protected int code;
	//used to store message in json string, get from json string
	protected String info;

	protected int id;			//important
	protected String username;	//important
	protected String password;
	protected String email;		//important
	protected int regtime;
	protected String apikey;	//important
	protected String about;
	protected int status;

	//this function is used for test, to show all params
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("\n--------SHOW PARAMS FOR USER--------\n");
		builder.append("info = " + info + "\n");

		builder.append("id = " + id + "\n");
		builder.append("username = " + username + "\n");
		builder.append("password = " + password + "\n");
		builder.append("email = " + email + "\n");
		builder.append("regtime = " + regtime + "\n");
		builder.append("apikey = " + apikey + "\n");
		builder.append("status = " + status + "\n");
		builder.append("about = " + about + "\n");

		return builder.toString();
	}
}

//用于保存设备信息
class Device
{
	protected int code;
	protected String info;

	protected int id;
	protected String name;
	protected String tags;
	protected String about;
	protected String locate;
	protected int user_id;
	protected int create_time;
	protected int last_active;
	protected int status;

	//this function is used for test, to show all params
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("\n--------SHOW PARAMS FOR DEVICE--------" + "\n");
		builder.append("info = " + info + "\n");

		builder.append("id = " + id + "\n");
		builder.append("name = " + name + "\n");
		builder.append("tags = " + tags + "\n");
		builder.append("about = " + about + "\n");
		builder.append("locate = " + locate + "\n");
		builder.append("user_id = " + user_id + "\n");
		builder.append("create_time = " + create_time + "\n");
		builder.append("last_active = " + last_active + "\n");
		builder.append("status = " + status + "\n");

		return builder.toString();
	}
}

//用于保存传感器信息
class Sensor
{
	protected int code;
	protected String info;

	private int id;
	private String name;
	private String tags;
	private String about;
	private int type;
	private int device_id;
	private int last_update;
	private String last_data;
	private int status;

	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("\n--------SHOW PARAMS FOR SENSOR--------" + "\n");
		builder.append("info = " + info + "\n");

		builder.append("id = " + id + "\n");
		builder.append("name = " + name + "\n");
		builder.append("tags = " + tags + "\n");
		builder.append("about = " + about + "\n");
		builder.append("type = " + type + "\n");
		builder.append("device_id = " + device_id + "\n");
		builder.append("last_update = " + last_update + "\n");
		builder.append("last_data = " + last_data + "\n");
		builder.append("status = " + status + "\n");

		return builder.toString();
	}
}

//保存数据点信息
class Datapoint
{
	protected int code;
	protected String info;

	private int id;
	private int sensor_id;
	private int timestamp;
	private String value;

	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("\n--------SHOW PARAMS FOR SENSOR--------" + "\n");
		builder.append("info = " + info + "\n");

		builder.append("id = " + id + "\n");
		builder.append("sensor_id = " + sensor_id + "\n");
		builder.append("timestamp = " + timestamp + "\n");
		builder.append("value = " + value + "\n");

		return builder.toString();
	}
}

//暂时没用
class ErrorInfo
{
	protected String info;
}