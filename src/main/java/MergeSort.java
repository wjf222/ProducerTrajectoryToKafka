import objects.TracingPoint;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MergeSort {
    public static ArrayList<File> getFlies(String dire) {
        ArrayList<File> files = new ArrayList<>();
        File file = new File(dire);
        File[] tempList = file.listFiles();
        for(File f:tempList) {
            if(f.isFile()) {
                files.add(f);
            }else if(f.isDirectory()) {
                files.addAll(getFlies(f.getAbsolutePath()));
            }
        }
        return files;
    }

    public static void merge(ArrayList<File> list) {
        int fileSize = list.size();
        if (fileSize == 1) {
            return;
        }
        //这里的输出文件命名规则，大家按需更改
        //输出文件名
        String outFilePath = "data-sorted.txt";
        File outFile = new File(outFilePath);
        //输出文件
        BufferedWriter out = null;

        //进行多路归并的叶子节点数组
        ArrayList<TracingPoint> leaves = new ArrayList<>(fileSize);
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            //每个归并段生成一个输入流
            ArrayList<BufferedReader> inputList = new ArrayList<>();
            for (int i = 0; i < fileSize; i++) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(list.get(i))));
                inputList.add(i, reader);
            }
            //初始化叶子数组
            String data = "";
            for (int i = 0; i < inputList.size(); i++) {
                data = inputList.get(i).readLine();
                try {
                    if(data != null)
                        leaves.add(TracingPoint.fromString(data));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //初始化败者树
            FailedTree<TracingPoint> failedTree = new FailedTree(leaves);
            //输出胜者
            Integer s = failedTree.getWinner();
            out.write(failedTree.getLeaf(s) + "");
            out.newLine();
            out.flush();
            while (inputList.size() > 0) {
                //新增叶子节点
                String newLeaf = inputList.get(s).readLine();
                //如果文件读取完成，关闭读取流，删除叶子几点
                if (newLeaf == null || newLeaf.equals("")) {
                    //当一个文件读取完成之后，关闭文件输入流、移除出List集合。
                    inputList.get(s).close();
                    int remove = s;
                    inputList.remove(remove);
                    failedTree.del(s);
                } else {
                    failedTree.add(TracingPoint.fromString(newLeaf), s);
                }

                s = failedTree.getWinner();
                if (s == null) {
                    break;
                }

                //输出胜利者
                out.write(failedTree.getLeaf(s) + "");
                out.newLine();
                out.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输出流
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
