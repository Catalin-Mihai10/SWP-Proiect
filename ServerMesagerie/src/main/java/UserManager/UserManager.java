package UserManager;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import Utilitati.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class UserManager implements UserManagerInterface {

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
        InputStream inputStream= Files.newInputStream(Constants.DESTINATION_PATH);
        JSONParser jsonParser = new JSONParser();
        Reader reader = new InputStreamReader(inputStream);
        Object obj = jsonParser.parse(reader);
        JSONArray userList = (JSONArray) obj;
        List<String> nameList = new ArrayList<>();

        for (Object o : userList) {
            JSONObject user = (JSONObject) o;
            String name = (String) user.get(Constants.USER);
            nameList.add(name);
        }
        reader.close();
        return nameList;
    }

    private void writeJson(List<String> users) throws IOException {
       OutputStream outputStream= Files.newOutputStream(Constants.DESTINATION_PATH);
       OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
       JSONArray jsonArray=new JSONArray();
       for(String user:users)
       {
           JSONObject o=new JSONObject();
           o.put(Constants.USER,user);
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

    public List<String> getUsers() throws IOException, ParseException {
        List<String> nameList = parseJson();
        return nameList;
    }
}