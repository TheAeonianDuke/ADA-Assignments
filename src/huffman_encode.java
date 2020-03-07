import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;


class Node implements Comparable<Node>
{
//    static int count;
    int frequency;
    char character;
    Node left, right;

    @Override
    public int compareTo(Node o) {
        return frequency-o.frequency;
    }
}

public class huffman_encode
{
    private static Node rootNode;

    // Hashing //
    public static void hashing(Node root, String s, HashMap<Character, String> hash)
    {
        if (root.left==null && root.right==null)
        {
            hash.put(root.character, s);
            return;
        }

        hashing(root.left, s+ "0",hash);
        hashing(root.right,s+ "1",hash);

    }

    public static void main(String[] args) throws IOException {
        HashMap<Integer,Integer> frequency=new HashMap<>();  //Hash<ap of frequency
        HashMap<Character,String> hash=new HashMap<>(); // HashMap of hashes
        PriorityQueue<Node> pqueue=new PriorityQueue<>();

        // Input File //
        File inputtxt=new File("src/input.txt");
        BufferedReader in = new BufferedReader(new FileReader(inputtxt));

        // Frequency of Chars //
        while (true)
        {
            String line=in.readLine();
            if (line==null){
                break;
            }
            for (int i=0; i<line.length();i++)
            {
               char c=line.charAt(i);
               int value=frequency.getOrDefault((int) c,0);
               frequency.put((int) c,value+1);

            }
        }
        in.close();


        for (int key: frequency.keySet())
        {
//            System.out.println((char) key+ ": " + hash.get(key));
            Node node= new Node();
            node.character=(char)key;
            node.frequency=frequency.get(key);
            node.left=null;
            node.right=null;
            pqueue.offer(node);
        }

        while(pqueue.size()>1)
        {
            Node x=pqueue.peek();
//            System.out.println(""+x.character);
            pqueue.poll();

            Node y=pqueue.peek();
//            System.out.println("y: "+y.character);
            pqueue.poll();

            Node sum= new Node();
            sum.frequency=x.frequency+y.frequency;
            sum.character='-';
            sum.left=x;
            sum.right=y;
            rootNode=sum;
            pqueue.offer(sum);
        }

        hashing(pqueue.peek(),"",hash);
        System.out.println(hash);

        BufferedReader in2 = new BufferedReader(new FileReader(inputtxt));

        int c=0;
        File newfile=new File("src/encoded.txt");
        FileWriter fileWriter= new FileWriter(newfile);

        while ((c=in2.read())!=-1)
        {
            char character =(char) c;
            if (hash.containsKey(character))
            {
                String val=hash.get(character);
                System.out.print(val);
                fileWriter.write(val);
            }
        }
        fileWriter.flush();
        fileWriter.close();
        in2.close();

        try {
            FileOutputStream os= new FileOutputStream(("src/mapping.txt"));
            ObjectOutputStream oos= new ObjectOutputStream(os);
            oos.writeObject(hash);
            oos.close();
            os.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
