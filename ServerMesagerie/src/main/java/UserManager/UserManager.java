package UserManager;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class UserManager implements UserManagerInterface {

    ClassLoader classLoader=this.getClass().getClassLoader();

    @Override
    public boolean addUser(String user_) throws IOException, ParseException {
        List<String> nameList = parseJson();

        if(!nameList.contains(user_))
        {
            nameList.add(user_);
            System.out.println(nameList);
            writeJson(nameList);
            return true;
        }

        System.out.println(nameList);
        return false;
    }

    private List<String> parseJson() throws IOException, ParseException {
        InputStream inputStream= Files.newInputStream(Paths.get("classes", "userList.json"));
        System.out.println(Paths.get("classes", "userList.json").toAbsolutePath());
        JSONParser jsonParser = new JSONParser();
        Reader reader = new InputStreamReader(inputStream);
        Object obj = jsonParser.parse(reader);
        JSONArray userList = (JSONArray) obj;
        List<String> nameList = new ArrayList<String>();

        for (Object o : userList) {
            JSONObject user = (JSONObject) o;
            String name = (String) user.get("user");
            nameList.add(name);
        }
        reader.close();
        return nameList;
    }

    private void writeJson(List<String> users) throws IOException {
       OutputStream outputStream= Files.newOutputStream(Paths.get("classes","userList.json"));
       OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
       JSONArray jsonArray=new JSONArray();
       for(String user:users)
       {
           JSONObject o=new JSONObject();
           o.put("user",user);
           jsonArray.add(o);
       }
       outputStreamWriter.write(jsonArray.toJSONString());
       outputStreamWriter.flush();
    }

    @Override
    public boolean removeUser(String user_) throws IOException, ParseException {
        List<String> nameList = parseJson();

        if(nameList.contains(user_))
        {
            nameList.remove(user_);
            System.out.println(nameList);
            writeJson(nameList);
            return true;
        }

        System.out.println(nameList);
        return false;
    }
}