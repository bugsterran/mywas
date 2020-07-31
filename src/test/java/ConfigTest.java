import com.google.gson.Gson;
import com.was.app.http.HttpServer;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class ConfigTest {

    @Test
    public void getConfigFileTest(){

        InputStream inputStream = HttpServer.class.getResourceAsStream("/config.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String strConfig = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        Map<String, Object> config = gson.fromJson(strConfig, Map.class);

        assertEquals( 80, (int)(double)config.get("port"));
        assertEquals( false, isEmpty(config.get("VirtualHosts")));

        assertEquals( true, config.get("VirtualHosts") instanceof List);
        List list = (List)config.get("VirtualHosts");

        assertEquals( true, (Map<String, Object>)list.get(0) instanceof Map);
        Map<String, Object> map = (Map<String, Object>)list.get(0);

        assertEquals( false, isEmpty(map.get("SeverName")));

    }

    private boolean isEmpty(Object o){
        if(o == null) return true;
        if(o instanceof String){
            return ((String) o).length() == 0;
        }else if(o instanceof Map){
            return ((Map) o).isEmpty();
        }else if(o instanceof List){
            return ((List) o).isEmpty();
        }
        return false;
    }
}
