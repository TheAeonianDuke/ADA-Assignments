import java.io.*;
import java.util.*;

class NodeR implements Comparable<NodeR>
{
    //    static int count;
    int frequency;
    char character;
    String s="";

    @Override
    public int compareTo(NodeR o) {
        return frequency-o.frequency;
    }
}
public class fano_shannon {

    private static HashMap<Character,String> mapper=new HashMap<>();

    private static void halfsum(ArrayList<NodeR> pqueue)
    {
        if(pqueue.size()==1)
        {
            return;
        }
        else
            {
                int total_sum = sumer(pqueue,pqueue.size()-1);
//                System.out.println(pqueue);
//                System.out.println(total_sum);
                int i=1;

                while(sumer(pqueue,i)<total_sum/2)
                {
                    if(sumer(pqueue,i+1)>=total_sum/2){
                        break;
                    }
                    i+=1;
                }
                ArrayList<NodeR> left=new ArrayList<>();
                ArrayList<NodeR> right=new ArrayList<>();


                for (int x=0;x<i;x++)
                {
//                    left.add(lst.get(x));
                    left.add(pqueue.get(x));
//                    System.out.println(left.get(x).character +" "+left.get(x).frequency);
                    if(mapper.containsKey(pqueue.get(x).character))
                    {
                        pqueue.get(x).s+="0";
                        mapper.put(pqueue.get(x).character,pqueue.get(x).s);
                    }
                    else
                        {
                            pqueue.get(x).s=("0");
                            mapper.put(pqueue.get(x).character,"0");

                        }
                    System.out.print(pqueue.get(x).character+",");
                }
                System.out.println();

                for (int x=i;x<pqueue.size();x++)
                {
                    right.add(pqueue.get(x));
//                    System.out.println(right.get(x).character +" "+right.get(x).frequency);
                    if(mapper.containsKey(pqueue.get(x).character))
                        {
                            pqueue.get(x).s+="1";
                            mapper.put(pqueue.get(x).character,pqueue.get(x).s);
                        }
                    else
                        {
                            pqueue.get(x).s=("1");
                            mapper.put(pqueue.get(x).character,"1");
                        }
                    System.out.print(pqueue.get(x).character+",");
                }

                System.out.println();
                halfsum(left);
                halfsum(right);
            }
    }

    public static int sumer(ArrayList<NodeR> parr, int x)
    {
        int sum=0;
        for(int i = 0; i < x; i++)
        {
            sum += parr.get(i).frequency;
        }
        return sum;
    }

    public static void main(String args[]) throws IOException {

        HashMap<Integer,Integer> map=new HashMap<>();  //Hashmap of frequency
        PriorityQueue<NodeR> pqueue=new PriorityQueue<>();

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
                int value=map.getOrDefault((int) c,0);
                map.put((int) c,value+1);
            }
        }
        in.close();

        Collection<Integer> values = map.values();
        ArrayList<Integer> frequency=new ArrayList<>(values);
//        System.out.println(map);

        for (int key: map.keySet())
        {
//            System.out.println((char) key+ ": " + map.get(key));
            NodeR node= new NodeR();
            node.character=(char)key;
            node.frequency=map.get(key);
            pqueue.offer(node);
        }

        ArrayList<NodeR> arr= new ArrayList<>();
        while (!pqueue.isEmpty()) {
            arr.add(pqueue.poll());
        }

//        for (int  i= 0; i < arr.size(); i++) {
//            System.out.println(arr.get(i).character +" "+arr.get(i).frequency);
//        }
        Collections.reverse(arr);
        System.out.println(arr);


        halfsum(arr);

        System.out.println(mapper);


    }
}
