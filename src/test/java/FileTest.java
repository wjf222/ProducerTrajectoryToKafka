import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTest {
    @Test
    public void testGetFiles(){
        List<File> flies = MergeSort.getFlies("D:\\wjf\\graduatestudent\\613lab\\数据相关\\轨迹数据\\T-Drive trajectory");

        System.out.println(1);
    }

    @Test
    public void testMerge(){
        ArrayList<File> files = MergeSort.getFlies("D:\\wjf\\graduatestudent\\613lab\\数据相关\\轨迹数据\\T-Drive trajectory");
        MergeSort.merge(files);

        System.out.println(1);
    }
}
