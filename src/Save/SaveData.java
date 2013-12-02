package Save;
// @author Ivan Delgado Huerta
import Graficos.Muro;
import Main.Mundo;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SaveData 
{    
    public static void saveMap()
    {
        Kryo kryo = new Kryo();      
        
        try 
        {
            Output output = new Output(new FileOutputStream("map.bin"));
            kryo.writeObject(output, Mundo.mapa);
            output.close();
        } catch (FileNotFoundException ex) { System.out.println("Fichero de Mapa no encontrado"); }
        
    }
    
    public static void loadMap()
    {
        Kryo kryo = new Kryo();
        
        try 
        {
            Input input = new Input(new FileInputStream("map.bin"));
            Mundo.mapa = kryo.readObject(input, Mundo.mapa.getClass());
            input.close();
        } catch (FileNotFoundException ex) { System.out.println("Fichero de Mapa no encontrado"); }
    }
    
}
