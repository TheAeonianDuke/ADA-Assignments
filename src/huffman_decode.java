import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

public class huffman_decode
{


    public static void decode(String s, HashMap map)
    {
        final String[] decodedStr = {""};
        final String[] temp = {""};

        for (int i=0; i<s.length();i++)
        {
            temp[0] +=s.charAt(i);
            String finalTemp = temp[0];

            map.forEach((key, value) -> {
                if (value.equals(finalTemp)) {
                    decodedStr[0] +=key;
                    temp[0] ="";
                }
            });
        }
        System.out.print(decodedStr[0]);
    }

    public static void main (String args[]) throws IOException {
        HashMap<Character, Integer> hash = null;

        try {
            FileInputStream in = new FileInputStream("src/mapping.txt");
            ObjectInputStream ois= new ObjectInputStream(in);
            hash=(HashMap) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println(hash);

        File inputtxt=new File("src/encoded.txt");
        BufferedReader in = new BufferedReader(new FileReader(inputtxt));
        String line=in.readLine();

        decode(line,hash);


    }
}
